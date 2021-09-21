package cn.gyw.gbatis.session;

import cn.gyw.gbatis.annotations.Mapper;
import cn.gyw.gbatis.config.Configuration;
import cn.gyw.gbatis.config.MappedStatement;
import cn.gyw.gbatis.mapper.PersonMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * SqlSessionFactory 构造器
 */
public class SqlSessionFactoryBuilder {

    // final 全局唯一
    private final Configuration configuration = new Configuration();

    public SqlSessionFactoryBuilder() {
    }

    public SqlSessionFactory build(String configPath) {
        try {
            // 加载配置文件
            InputStream inputStream = this.getClass().getClassLoader().
                    getResourceAsStream(configPath);
            Properties properties = new Properties();
            properties.load(inputStream);

            // 1.加载数据库配置信息
            loadDatabaseProperties(properties);
            // 2.初始化 Mapper.xml
            loadMapperLocations(properties);
            // 初始化Mapper interface 注解
            loadMapperAnnotations(properties);

            // 3.初始化 Mapper 接口
            loadMapperInterface(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return build(this.configuration);
    }

    // 加载数据库配置信息
    private void loadDatabaseProperties(Properties properties) throws IOException {
        // 将数据库配置信息写入到Configuration 对象
        configuration.setDriverClass(properties.getProperty("driverClass"));
        configuration.setDriverUrl(properties.getProperty("driverUrl"));
        configuration.setUsername(properties.getProperty("username"));
        configuration.setPassword(properties.getProperty("password"));
    }

    // 加载指定文件夹下的所有 Mapper XML 文件
    private void loadMapperLocations(Properties properties) throws DocumentException {
        URL mappersURL = this.getClass().getClassLoader()
                .getResource(properties.getProperty("mapper.xml.location"));
        // 获取指定文件夹信息
        File mappers = new File(mappersURL.getFile());
        if (mappers.isDirectory()) {
            File[] files = mappers.listFiles();
            // 遍历所有的mapper.xml ，解析信息后，注册到Configuration 对象种
            for (File file : files) {
                loadMapperInfo(file);
            }
        }
    }

    // 加载指定的Mapper XMl 信息，注册到全局配置
    private void loadMapperInfo(File file) throws DocumentException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                loadMapperInfo(file);
            }
        } else {
            String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (!"xml".equalsIgnoreCase(suffix)) {
                return;
            }
            // 解析 XML 注册 Configuration
            resolveMapper(file);
        }
    }

    private void resolveMapper(File mapperFile) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(mapperFile);

        Element root = document.getRootElement();
        String namespace = root.attributeValue("namespace");

        List<Element> curdEles = root.elements("select");
        for (int i = 0; i < curdEles.size(); i++) {
            Element curdEle = curdEles.get(i);
            MappedStatement mappedStatement = new MappedStatement(namespace);
            mappedStatement.setId(curdEle.attributeValue("id"));
            mappedStatement.setResultType(curdEle.attributeValue("resultType"));
            mappedStatement.setSql(curdEle.getText());
            configuration.addMappedStatement(namespace + "." + mappedStatement.getId(), mappedStatement);
        }
    }

    private void loadMapperAnnotations(Properties properties) {
    }

    private void loadMapperInterface(Properties properties) throws ClassNotFoundException {
        String basePackage = properties.getProperty("mapper.base.package");
        URL url = this.getClass().getClassLoader()
                .getResource(basePackage.replaceAll("\\.", "/"));
        File mapperDir = new File(url.getFile());
        if (mapperDir.isDirectory()) {
            File[] files = mapperDir.listFiles();
            for (File file : files) {
                loadMapper(file, basePackage);
            }
        }
    }

    private void loadMapper(File file, String scanPackage) throws ClassNotFoundException {
        if (file.isDirectory()) {
            for (File subFile : Objects.requireNonNull(file.listFiles())) {
                loadMapper(subFile, scanPackage + file.getName());
            }
        } else {
            // 解析@Mapper 修饰的接口，注册到Configuration
            String fileName = file.getName();
            if (!fileName.endsWith(".class")) { // 非class文件直接返回
                return;
            }
            String className = scanPackage + "." + fileName.replace(".class", "");
            Class<?> clazz = Class.forName(className);
            if (!clazz.isAnnotationPresent(Mapper.class)) {
                return;
            }
            configuration.registerMapper(clazz);
        }
    }

    public SqlSessionFactory build(Configuration configuration) {
        return new SqlSessionFactory(configuration);
    }
}
