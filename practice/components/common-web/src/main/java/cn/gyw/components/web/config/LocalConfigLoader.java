package cn.gyw.components.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

/**
 * 读取本地配置文件
 */
@Configuration
@Profile(value = {"local"})
@Order
public class LocalConfigLoader implements EnvironmentPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(LocalConfigLoader.class);

    public static final String KEY_FILE = "local.file";
    /**
     * TODO 无效
     * @param environment
     * @param application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String localFile = environment.getProperty(KEY_FILE);
        Objects.requireNonNull(localFile);
        String configFile = Paths.get(Paths.get(localFile).toUri()).toString();
        log.info("Load local config from :{}", configFile);
        Resource resource = new FileSystemResourceLoader().getResource(configFile);
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            System.out.println(properties);
            PropertiesPropertySource propertySource = new PropertiesPropertySource("customerApplicationConfig", properties);
            environment.getPropertySources().addLast(propertySource);
        } catch (IOException e) {
            log.error("Local config load error :", e);
        }
    }
}
