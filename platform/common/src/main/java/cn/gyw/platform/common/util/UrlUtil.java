package cn.gyw.platform.common.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;

public class UrlUtil {

    public static URL url(String url) {
        return url(url, null);
    }

    public static URL url(String url, URLStreamHandler handler) {
        try {
            return new URL(null, url, handler);
        } catch (MalformedURLException e) {
            // 尝试文件路径
            try {
                return new File(url).toURI().toURL();
            } catch (MalformedURLException ex2) {
                throw new RuntimeException(e);
            }
        }
    }
}
