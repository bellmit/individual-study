package cn.gyw.components.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * 读取本地配置文件
 */
@Configuration
@Profile(value = {"local"})
public class LocalConfig {

    private static final Logger log = LoggerFactory.getLogger(LocalConfig.class);

    private static final String FILE_PATH = "/config-center/local.properties";

    @Autowired
    private StandardEnvironment environment;

    /**
     * 读取配置
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer loadProperties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        try {
            log.debug("Load remote config file name :{}", Paths.get(Paths.get(FILE_PATH).toUri()));
            Resource resource = new FileSystemResource(Paths.get(Paths.get(FILE_PATH).toUri()).toString());
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            configurer.setProperties(properties);
        } catch (IOException e) {
            log.error("Load properties error :", e);
        }
        // YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        // File路径引入
        //yaml.setResources(new FileSystemResource("classpath:" + FILE_PATH));
        // class路径引入
        // yaml.setResources(new ClassPathResource(FILE_PATH));
        return configurer;
    }
}
