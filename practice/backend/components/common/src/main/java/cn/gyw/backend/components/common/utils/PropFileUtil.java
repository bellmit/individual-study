package cn.gyw.backend.components.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

/**
 * @desc 本地配置文件读取
 * @createdTime 2022/1/10 23:06
 */
public final class PropFileUtil {

    private static Properties properties;

    public PropFileUtil(String filePath) {
        loadProperties(filePath);
    }

    public String get(String key, String defaultVal) {
        if (Objects.isNull(properties)) {
            return defaultVal;
        }
        return properties.getProperty(key, defaultVal);
    }

    private void loadProperties(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.isReadable(path)) {
            throw new RuntimeException("[" + filePath + "] 配置文件不存在");
        }
        properties = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            properties.load(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
