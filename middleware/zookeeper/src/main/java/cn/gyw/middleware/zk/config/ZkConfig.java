package cn.gyw.middleware.zk.config;

import java.util.Properties;

import cn.gyw.platform.common.util.PropertiesUtil;

public enum ZkConfig {

	INSTANCE;
	
	private static final String CONFIG_FILE = "config.properties";
	
	private Properties properties = PropertiesUtil.readConfig(CONFIG_FILE);
	
	public String getZkServer() {
		return properties.getProperty("zk.connect.addr");
	}
}
