package cn.gyw.platform.tools.logging.service;

import cn.gyw.platform.tools.logging.model.RemoteConfig;

/**
 * 远程操作服务接口
 */
public interface IRemoteService {

	/**
	 * 登录
	 * @param config
	 * @return
	 */
	boolean login(RemoteConfig config);
	
	/**
	 * 执行命令
	 * @param cmd
	 * @return
	 */
	String execute(String cmd);
	
	/**
	 * 上传文件
	 * @param dir
	 * @return
	 */
	boolean upload(String sourceFile, String remoteDir);
	
	/**
	 * 
	 * @param sourceFile
	 * @param remoteDir
	 * @param mode 0777 : -rwxrwxrwx
	 * @return
	 */
	boolean upload(String sourceFile, String remoteDir, String mode);
	
	/**
	 * 下载文件
	 * @param dir
	 * @return
	 */
	boolean download(String remoteFile, String localDir);
	
	boolean download(String[] remoteFiles, String localDir);
}
