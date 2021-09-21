package cn.gyw.middleware.redis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性加载文件
 */
public class PropertiesUtil {

    /**
     * Redis 配置文件
     */
    private static final String REDIS_CONIF_FILE = "redis.properties";

    //加载property文件到io流里面
    public static Properties loadProperties(String propertyFile) {
        if (propertyFile == null || "".equals(propertyFile.trim()) || propertyFile.isEmpty()) {
            propertyFile = REDIS_CONIF_FILE;
        }
        Properties properties = new Properties();
        try {
            InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertyFile);
            if(is == null){
                is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertyFile);
            }
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 根据key值取得对应的value值
     *
     * @param key
     * @return
     */
    public static String getValue(String propertyFile, String key) {
        Properties properties = loadProperties(propertyFile);
        return properties.getProperty(key);
    }

}
