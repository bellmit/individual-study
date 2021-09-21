package cn.gyw.handwritten.gspring.beans.factory.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import cn.gyw.handwritten.gspring.beans.factory.config.GBeanDefinition;

/**
 * 定位配置文件的顶层类
 */
public class GBeanDefinitionReader {

    private static final String SCAN_PACKAGE_KEY = "scanPackage";

    private Properties config = new Properties();

    private List<String> classNameList = new ArrayList<>(256);

    public GBeanDefinitionReader() {

    }

    /**
     * 返回用于注册bean定义的bean工厂
     */
    public GBeanDefinitionRegistry getRegistry() {

        return null;
    }

    public List<GBeanDefinition> loadBeanDefinitions(String... locations) {
        // 通过URL 路径读取需要的文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));
        try {
            this.config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanPackage(this.config.getProperty(SCAN_PACKAGE_KEY));

        List<GBeanDefinition> beanDefinitions = new ArrayList<>(256);
        String className;
        Class<?> beanClass;
        try {
            for (int i = 0, len = classNameList.size(); i < len; i++) {
                className = classNameList.get(i);
                beanClass = Class.forName(className);
                if (beanClass.isInterface()) {
                    continue;
                }
                // 首字母小写
                beanDefinitions.add(doCreateBeanDefinition(firstLetterLowerCase(beanClass.getSimpleName()), className));
                // 全类名
                beanDefinitions.add(doCreateBeanDefinition(className, className));
                for (Class<?> oneInterface : beanClass.getInterfaces()) {
                    // 接口
                    beanDefinitions.add(doCreateBeanDefinition(oneInterface.getName(), className));
                }
            }
            return beanDefinitions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doScanPackage(String scanPackage) {
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource(scanPackage.replace(".", "/"));
		assert url != null;
		File rootFile = new File(url.getFile());
        for (File file : Objects.requireNonNull(rootFile.listFiles())) {
            // 手动跳过 annotation
            if (file.isDirectory()) {
                // 递归查找.class 文件
                doScanPackage(scanPackage + "." + file.getName());
            } else {
                // 非class 文件跳过
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                // .class 文件结尾的文件
                classNameList.add(scanPackage + "." + file.getName().replace(".class", ""));
            }
        }
    }

    // 每一个配置信息封装为BeanDefinition
    private GBeanDefinition doCreateBeanDefinition(String beanName, String className) {
        GBeanDefinition beanDefinition = new GBeanDefinition();
        beanDefinition.setBeanClassName(className);
        beanDefinition.setFactoryBeanName(firstLetterLowerCase(beanName));
        return beanDefinition;
    }

    // 首字母小写
    private String firstLetterLowerCase(String className) {
        char[] chars = className.toCharArray();
        // 首位是字母
        if (chars[0] > 'A' && chars[0] < 'Z') {
            // 32 = 'a' - 'A'
            chars[0] += 32;
        } // 其他的不处理
        return new String(chars);
    }
    
    public Properties getConfig() {
    	return this.config;
    }
}
