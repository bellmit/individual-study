package cn.gyw.backend.fileserver.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import cn.gyw.backend.fileserver.dao.FileInfoDao;
import cn.gyw.backend.fileserver.entity.FileInfo;
import cn.gyw.backend.fileserver.model.ServiceProperties;

@Service
public class FileUploadService {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    private ServiceProperties serviceProperties;

    private static final String FILE_INDO_DIR = "file-info/";

    private static final String AVATAR_DIR = "avatar/";

    private static final String STATUS_UPLOAD = "upload";
    private static final String STATUS_DELETED = "delete";

    private String fileServerDir;

    @Autowired
    private FileInfoDao userAvatarDao;

    public String uploadSingleFile(FileInfo fileInfo, MultipartFile file) {
        LOG.debug("upload file info :{}", fileInfo);
        Path path = Paths.get(fileServerDir + AVATAR_DIR + fileInfo.getFileId());
        File newFile = path.toFile();
        try {
            file.transferTo(newFile);
            userAvatarDao.save(fileInfo);
            return fileInfo.getFileId();
        } catch (IllegalStateException e) {
            LOG.error("file upload state error :{}", e);
        } catch (IOException e) {
            LOG.error("file upload IO error :{}", e);
        }
        return "";
    }

    public boolean download(String fileId, HttpServletRequest request, HttpServletResponse response) {
        FileInfo fileInfo = userAvatarDao.findByFileId(fileId);
        Path path = Paths.get(fileServerDir + AVATAR_DIR + fileId);
        File serverFile = path.toFile();
        
        if (serverFile.exists()) {
            response.setContentType("application/force-download;charset=UTF-8"); // 设置下载完毕不打开文件
            // 设置文件名
            try {
                response.setHeader("Content-Disposition",
                        "attachment;fileName*=utf-8'zh_cn'" + URLEncoder.encode(fileInfo.getFileName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOG.error("enode file name error");
            }
            try (OutputStream out = response.getOutputStream()) {
                out.write(FileUtils.readFileToByteArray(serverFile));
                out.flush();
                
                return true;
            } catch (IOException e) {
                LOG.error("response write file error.");
            }
        }
        return false;
    }

    public FileInfo buildFileInfo(String fileName, String userId, String expire) {
        String fileId = UUID.randomUUID().toString();

        FileInfo dbInfo = userAvatarDao.findByUploader(userId);
        if (Objects.nonNull(dbInfo)) {
            dbInfo.setFileId(fileId);
            dbInfo.setFileName(fileName);
            return dbInfo;
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setFileName(fileName);
        if (StringUtils.isNotBlank(expire)) {
            fileInfo.setExpire(Long.parseLong(expire));
        } else {
            fileInfo.setExpire(Long.parseLong("-1"));
        }
        fileInfo.setFileDir(fileServerDir);
        fileInfo.setInfoDir(FILE_INDO_DIR);
        fileInfo.setUploader(userId);
        fileInfo.setCreatedTime(LocalDateTime.now());
        fileInfo.setStatus(STATUS_UPLOAD);
        return fileInfo;
    }

    public void writeFileInfo(FileInfo fileInfo) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(fileInfo);
        try {
            FileUtils.writeByteArrayToFile(new File(fileInfo.getInfoDir() + fileInfo.getFileId()),
                    jsonStr.getBytes("UTF-8"));
        } catch (IOException e) {
            LOG.error("write info file error, file id = {}", fileInfo.getFileId());
        }
    }

    @PostConstruct
    public void init() {
        boolean flag = false;
        fileServerDir = serviceProperties.getStorageDir();

        Path dirPath = Paths.get(fileServerDir, AVATAR_DIR);
        File dir = dirPath.toFile();
        if (!dir.exists()) {
            flag = dir.mkdirs();
            LOG.debug("{} create result:{}", dir.getAbsolutePath(), flag);
        }

        Path infoPath = Paths.get(fileServerDir, FILE_INDO_DIR);
        File info = infoPath.toFile();
        flag = false;
        if (!info.exists()) {
            info.mkdirs();
            LOG.debug("{} create result:{}", info.getAbsolutePath(), flag);
        }
    }
}
