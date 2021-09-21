package cn.gyw.spring.web.bigfile.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequestDTO {

	private MultipartFile file;
	
	public MultipartFile getFile() {
		return this.file;
	}

}
