package cn.gyw.platform.tools.logging.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;

import cn.gyw.platform.tools.logging.constants.Common;
import cn.gyw.platform.tools.logging.model.RemoteConfig;

/**
 * 配置加载器
 */
public enum ConfigLoader {
	
	INSTANCE;

	private static final String PROPERTIES_NAME = "application.properties";
	
	private Properties properties;
	
	private RemoteConfig remoteConfig;
	
	ConfigLoader() {
		Path path = Paths.get(PROPERTIES_NAME);
		File file = path.toFile();
		if (!file.exists()) {
			URL url = Thread.currentThread().getContextClassLoader().getResource(PROPERTIES_NAME);
			try {
				path = Paths.get(url.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			file = path.toFile();
		}
		try (InputStream in = new FileInputStream(file)) {
			properties = new Properties();
			properties.load(new FileInputStream(file));			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public RemoteConfig getRemoteConfig() {
		String filePath = properties.getProperty(Common.PropertyKey.SSH_CFG_FILE.key());
		File file = Paths.get(filePath).toFile();
		try {
			String cfgStr = FileUtils.readLines(file, "UTF-8").stream().collect(Collectors.joining());
			if (StringUtils.isBlank(cfgStr)) {
				throw new RuntimeException("SSH config is empty, check.");
			}
			JSONObject cfgJson = new JSONObject(cfgStr);
			remoteConfig = new RemoteConfig();
			remoteConfig.setIp(cfgJson.getString("ip"));
			remoteConfig.setUsername(cfgJson.getString("user"));
			remoteConfig.setPassword(cfgJson.getString("password"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return remoteConfig;
	}
	
	public JSONObject toJson() {
		JSONWriter jsonWriter = new JSONStringer().object();
		properties.entrySet().forEach( entry -> {
			jsonWriter.key(entry.getKey().toString()).value(entry.getValue());
		});
		jsonWriter.endObject();
		return new JSONObject(jsonWriter);
	}
	
	public String get(Common.PropertyKey propertyKey) {
		return properties.getProperty(propertyKey.key(), "");
	}
	
	public String getFromArray(Common.PropertyKey propertyKey, int index) {
		return properties.getProperty(propertyKey.key() + "[" + index + "]", "");
	}
	
}
