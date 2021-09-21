package cn.gyw.platform.crawler.util;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * WebDriver 获取工具类
 */
public class SeleniumUtil {

    private final static String DEFAULT_PROPERTY = "webdriver.chrome.driver";
    private final static String DEFAULT_DRIVER = "chrome.driver.path";
    private final static Properties PROPERTIES = PropertiesUtil.resolveResource("selenium.properties");

    public static WebDriver driver() {
        System.setProperty(DEFAULT_PROPERTY, PROPERTIES.getProperty(DEFAULT_DRIVER));
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
        // options.setExperimentalOption("debuggerAddress", "localhost:9222");
        return new ChromeDriver(options);
    }
    
    public static boolean captchVerifyCode(WebElement element) {
    	Point location = element.getLocation();
        int left = location.getX();
        int top = location.getY();
        int right = left + element.getSize().width;
        int bottom = top + element.getSize().height;

        File code = element.getScreenshotAs(OutputType.FILE);
        String path = "D:\\Temp\\" + DateTimeFormatter.ofPattern("yyHHmmssSSS").format(LocalDateTime.now())
    			+ ".jpg";
        try {
            FileUtils.copyFile(code, new File(path));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private SeleniumUtil() {}
}
