package cn.gyw.spring.web.bigfile.service;

import java.io.File;

import cn.gyw.spring.web.bigfile.model.FileUploadDTO;
import cn.gyw.spring.web.bigfile.model.FileUploadRequestDTO;

/**
 * 分片上传模板
 */
public abstract class SliceUploadTemplate implements SliceUploadStrategy {

	private String uploadDirPath = "D:\\Temp\\files";
	
	@Override
	public FileUploadDTO sliceUpload(FileUploadRequestDTO param) {
		boolean isOk = this.upload(param);
		if (isOk) {
			File tmpFile = this.createTmpFile(param);
//			FileUploadDTO fileUploadDTO = this.saveAndFileUploadDTO(param.getFile().getOriginalFilename(), tmpFile);
//			return fileUploadDTO;
		}
//		String md5 = FileMD5Util.getFileMD5(param.getFile());
//
//		Map<Integer, String> map = new HashMap<>();
//		map.put(param.getChunk(), md5);
//		return FileUploadDTO.builder().chunkMd5Info(map).build();
		return null;
	}

	protected File createTmpFile(FileUploadRequestDTO param) {
//		param.setPath(FileUtil.withoutHeadAndTailDiagonal(param.getPath()));
		String fileName = param.getFile().getOriginalFilename();
		String tempFileName = fileName + "_tmp";
		
		File tmpDir = new File(uploadDirPath);
		File tmpFile = new File(uploadDirPath, tempFileName);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		return tmpFile;
	}

	protected abstract boolean upload(FileUploadRequestDTO param);

}
