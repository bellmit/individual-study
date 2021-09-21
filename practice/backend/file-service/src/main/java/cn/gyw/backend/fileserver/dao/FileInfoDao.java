package cn.gyw.backend.fileserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cn.gyw.backend.fileserver.entity.FileInfo;

@Repository
public interface FileInfoDao extends JpaRepository<FileInfo, Long> {

    FileInfo findByFileId(String fileId);
    
    FileInfo findByUploader(String uploader);
}
