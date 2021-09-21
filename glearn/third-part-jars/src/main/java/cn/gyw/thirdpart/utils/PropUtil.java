package cn.gyw.thirdpart.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 */
public final class PropUtil {

    private static Properties properties;

    /**
     * 获取属性值
     * @param propertyName
     * @return
     */
    public static String getPropertyValue(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public PropUtil(String resourcePath) {
        InputStream inputStream = this.getClass().getResourceAsStream(resourcePath);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
