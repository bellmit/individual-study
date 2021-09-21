package cn.gyw.platform.common.util;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import cn.gyw.platform.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public final class PropertiesUtil {

	private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	/**
	 * 读取类路径下配置文件
	 * @param configName
	 * @return
	 */
	public static Properties readConfig(String configName) {
		ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
		Properties properties = new Properties();
		try (InputStream in = classLoader.getResourceAsStream(configName)) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 读取指定文件 properties
	 * @param targetPath 属性文件路径
	 * @return
	 */
	public static Properties read(String targetPath) {
		File f = Paths.get(Paths.get(targetPath).toUri()).toFile();
		log.debug("file path :{}", f.getAbsolutePath());
		if (!f.exists()) {
			return null;
		}
		Properties properties = new Properties();
		try (InputStream in = new FileInputStream(f)) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
