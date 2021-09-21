package cn.gyw.platform.crawler.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 屏幕截图
 */
public class ScreenShotUtil {

    private final static String DEFAULT_DIR = "D:\\Temp";

    public static String fetchVerifyCode(String scrPath) throws IOException {
        BufferedImage full = ImageIO.read(new File(scrPath));

        return "";
    }

    public static String takeScreenShot(WebDriver driver) {
        String screenName = System.currentTimeMillis() + ".jpg";
        File dir = new File(DEFAULT_DIR + "/snapshot");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String scrPath = dir.getAbsolutePath() + "/" + screenName;
        takeScreenShot(scrPath, driver);
        return scrPath;
    }

    public static void takeScreenShot(String screenPath, WebDriver driver) {
        try {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ScreenShotUtil() {}

}
