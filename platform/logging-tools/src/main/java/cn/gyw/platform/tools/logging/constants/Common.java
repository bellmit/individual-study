package cn.gyw.platform.tools.logging.constants;

/**
 * 通用常量
 */
public interface Common {

	enum PropertyKey {
		SSH_CFG_FILE("ssh.cfg.path"),
		
		ARRAY_COUNT("array.count"),
		
		UPLOAD_LOCAL_FILES("upload.local.files"),
		UPLOAD_REMOTE_DIR("upload.remote.dir"),
		
		DOWNLOAD_LOCAL_DIR("download.local.dir"),
		DOWNLOAD_REMOTE_FILES("download.remote.files"),
		;
		
		private String key;
		PropertyKey(String key) {
			this.key = key;
		}
		public String key() {
			return key;
		}
	}
}
