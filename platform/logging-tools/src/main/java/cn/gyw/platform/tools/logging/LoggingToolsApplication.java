package cn.gyw.platform.tools.logging;

import cn.gyw.platform.tools.logging.constants.Common;
import cn.gyw.platform.tools.logging.service.GanymedRemoteService;
import cn.gyw.platform.tools.logging.service.IRemoteService;
import cn.gyw.platform.tools.logging.support.ConfigLoader;

public class LoggingToolsApplication {

	public static void main(String[] args) {
		IRemoteService remoteService = new GanymedRemoteService();
		remoteService.login(ConfigLoader.INSTANCE.getRemoteConfig());
		
		int count = Integer.parseInt(ConfigLoader.INSTANCE.get(Common.PropertyKey.ARRAY_COUNT));
		String remoteFiles;
		String localDir;
		for (int i = 0 ; i < count ; i ++) {
			remoteFiles = ConfigLoader.INSTANCE.getFromArray(Common.PropertyKey.DOWNLOAD_REMOTE_FILES, i);
			localDir = ConfigLoader.INSTANCE.getFromArray(Common.PropertyKey.DOWNLOAD_LOCAL_DIR, i);
			remoteService.download(remoteFiles.split(","), localDir);
		}
	}
}
