package cn.gyw.backend.fileserver.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.gyw.platform.common.model.ToStringObject;

@Entity
@Table(name = "file_info")
public class FileInfo extends ToStringObject implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * strategy: auto 主键由程序控制；
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "cn.gyw.backend.fileserver.strategy.SnowFlakeIdentityGenerator")
    private Long uid;
    
    private String fileId;// 文件ID uuid
    private String fileName;// 文件名
    private long expire;// 有效期
    private String fileDir;// 文件目录
    private String infoDir;// 文件信息目录
    private String uploader;// 上传者

    /**
     * 文件上传时间
     */
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
    
    private String status;// 文件状态 upload:上传, delete:删除

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getInfoDir() {
        return infoDir;
    }

    public void setInfoDir(String infoDir) {
        this.infoDir = infoDir;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
