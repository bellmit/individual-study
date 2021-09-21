package cn.gyw.backend.system.dto;

import java.util.Date;

public class FileInfoDto {

	private Long uid;

	/**
	 * 文件UUID
	 * 
	 * 表字段: file_info.file_id
	 */
	private String fileId;

	/**
	 * 文件名称
	 * 
	 * 表字段: file_info.file_name
	 */
	private String fileName;

	/**
	 * 过期时长（min）
	 * 
	 * 表字段: file_info.expire
	 */
	private Long expire;

	/**
	 * 文件类型
	 * 
	 * 表字段: file_info.file_type
	 */
	private String fileType;

	/**
	 * 文件存放目录
	 * 
	 * 表字段: file_info.file_dir
	 */
	private String fileDir;

	/**
	 * 信息文件存放目录
	 * 
	 * 表字段: file_info.info_dir
	 */
	private String infoDir;

	/**
	 * 文件上传者ID
	 * 
	 * 表字段: file_info.uploader
	 */
	private Integer uploader;

	/**
	 * 文件状态 upload:上传, delete:删除
	 * 
	 * 表字段: file_info.status
	 */
	private String status;

	/**
	 * 创建时间
	 * 
	 * 表字段: file_info.created_time
	 */
	private Date createdTime;

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

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	public Integer getUploader() {
		return uploader;
	}

	public void setUploader(Integer uploader) {
		this.uploader = uploader;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
