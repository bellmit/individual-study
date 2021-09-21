package cn.gyw.spring.web.bigfile.service;

import cn.gyw.spring.web.bigfile.model.FileUploadDTO;
import cn.gyw.spring.web.bigfile.model.FileUploadRequestDTO;

public interface SliceUploadStrategy {

	FileUploadDTO sliceUpload(FileUploadRequestDTO param);
	
}
