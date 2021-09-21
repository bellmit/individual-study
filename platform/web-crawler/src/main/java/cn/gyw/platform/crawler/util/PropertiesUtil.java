package cn.gyw.platform.crawler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

	public static Properties resolveResource(String fileName) {
		Properties properties = new Properties();
		try (InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	private PropertiesUtil() {}
}
