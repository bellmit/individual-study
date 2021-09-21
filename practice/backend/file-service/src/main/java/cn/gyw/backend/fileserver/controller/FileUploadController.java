package cn.gyw.backend.fileserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.gyw.backend.fileserver.entity.FileInfo;
import cn.gyw.backend.fileserver.enums.FileServerAssert;
import cn.gyw.backend.fileserver.service.FileUploadService;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 文件上传
	 * @param uploadFile
	 * @param userId
	 * @param expire
	 * @return
	 */
	@PostMapping
	public String uploadSingleFile(@RequestParam("file") MultipartFile uploadFile,
			@RequestParam(name = "userId", required = false, defaultValue = "-1") String userId,
			@RequestParam(name = "expire", required = false) String expire) {
		FileServerAssert.FILE_NOT_EMPTY.assertNotEmpty(uploadFile);
		FileInfo fileInfo = fileUploadService.buildFileInfo(uploadFile.getOriginalFilename(), userId, expire);
		String fileId = fileUploadService.uploadSingleFile(fileInfo, uploadFile);
		FileServerAssert.UPLOAD_SUCCESS.assertNotEmpty(fileId);
		return fileId;
	}

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param id
	 */
	@GetMapping("/{id}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		FileServerAssert.FILE_ID_NOT_EMPTY.assertNotEmpty(id);
		fileUploadService.download(id, request, response);
	}
}
