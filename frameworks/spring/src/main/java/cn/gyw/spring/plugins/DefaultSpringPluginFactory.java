package cn.gyw.spring.plugins;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.aopalliance.aop.Advice;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.gson.Gson;

public class DefaultSpringPluginFactory implements ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(DefaultSpringPluginFactory.class);

    private ApplicationContext applicationContext;

    private Plugins plugins;

    // config cache
    private Map<String, PluginConfig> configs = new HashMap<>();
    // advice cache
    private Map<String, Advice> adviceCache = new HashMap<>();

    private String configPath = "D:\\Temp\\plugin\\spring-plugins.json";

    /**
     * 激活插件
     *
     * @param pluginId
     * @throws Exception
     */
    public void activePlugin(String pluginId) {
        if (!configs.containsKey(pluginId)) {
            throw new RuntimeException("this plugin id not exists :" + pluginId);
        }
        PluginConfig config = configs.get(pluginId);
        config.setActive(true);

        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);
            if (bean == this) { // 当前bean
                continue;
            }
            if (!(bean instanceof Advised)) { // bean 不是增强bean
                log.debug("bean [{}] is not advised", beanName);
                continue;
            }
            if (findAdvice((Advised) bean, config.getClassName())) { // bean 里是否有该通知
                continue;
            }
            Advice advice = null;
            try {
                advice = buildAdvice(config);
                // do advice
                ((Advised) bean).addAdvice(advice);
            } catch (Exception e) {
                throw new RuntimeException("插件安装失败", e);
            }
        }
    }

    /**
     * 删除插件
     *
     * @param pluginId
     * @throws Exception
     */
    public void removePlugin(String pluginId) throws Exception {
        if (!configs.containsKey(pluginId)) {
            throw new RuntimeException("this plugin id not exists :" + pluginId);
        }
        PluginConfig config = configs.get(pluginId);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);
            if (bean == this) { // 当前bean
                continue;
            }
            if (!(bean instanceof Advised)) { // bean 不是增强bean
                log.debug("bean [{}] is not advised", beanName);
                continue;
            }

            log.debug("bean need advice {}, bean :{}", beanName, bean);
            URL remoteJar = new URL(config.getJarRemoteUrl());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{remoteJar});
            Class<?> clazz = classLoader.loadClass(config.getClassName());
            Advice advice = (Advice) clazz.newInstance();
            // do advice
            ((Advised) bean).removeAdvice(advice);
        }
    }

    /**
     * 刷新插件库
     *
     * @return
     * @throws IOException
     */
    public Collection<PluginConfig> flushPlugins() throws IOException {
        Path jsonPath = Paths.get(configPath);
        File configFile = jsonPath.toFile();
        String content = FileUtils.readFileToString(configFile, "UTF-8");
        Gson gson = new Gson();
        this.plugins = gson.fromJson(content, Plugins.class);
        this.plugins.getConfig().forEach(cfg -> {
            if (!configs.containsKey(cfg.getId())) {
                configs.put(cfg.getId(), cfg);
            }
        });
        return configs.values();
    }

    /**
     * 目标是否已经存在插件通知
     *
     * @param advised
     * @param className 插件类名
     * @return
     */
    private boolean findAdvice(Advised advised, String className) {
        for (Advisor advisor : advised.getAdvisors()) {
            if (advisor.getAdvice().getClass().getName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建通知
     *
     * @param config
     * @return
     */
    private Advice buildAdvice(PluginConfig config) throws Exception {
        if (adviceCache.containsKey(config.getClassName())) {
            return adviceCache.get(config.getClassName());
        }
        // 获取本地待加载的jar插件包路径
        URL targetUrl = new URL(config.getJarRemoteUrl());
        // 获取当前正在运行的项目加载了哪些jar
        URLClassLoader loader = (URLClassLoader) this.getClass().getClassLoader();
        boolean isLoaded = false;
        for (URL url : loader.getURLs()) {
            if (url.equals(targetUrl)) {
                isLoaded = true;
                break;
            }
        }
        if (!isLoaded) {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            add.setAccessible(true);
            add.invoke(loader, targetUrl);
        }

        Class<?> adviceClass = loader.loadClass(config.getClassName());
        Advice advice = (Advice) adviceClass.newInstance();
        // cache
		adviceCache.put(adviceClass.getName(), advice);
        return advice;
    }

    @PostConstruct
    public void init() {
        // 读取json config 文件到内存中
        try {
            flushPlugins();
            activePlugin("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
