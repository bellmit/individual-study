package cn.gyw.gbatis.config;

import cn.gyw.gbatis.binding.MapperRegistry;
import cn.gyw.gbatis.session.SqlSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 全局配置类
 */
public class Configuration {

    // 数据源
    private String driverClass;
    private String driverUrl;
    private String username;
    private String password;

    protected final MapperRegistry mapperRegistry;

    // SQL 映射
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public Configuration() {
        // 创建MapperProxy 的注册器
        this.mapperRegistry = new MapperRegistry(this);
    }

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        mappedStatementMap.put(statementId, mappedStatement);
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public MappedStatement getMappedStatement(String statementId) {
        return mappedStatementMap.get(statementId);
    }

    /**
     * 获取代理对象
     * @param type
     * @param sqlSession
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return this.mapperRegistry.getMapper(type, sqlSession);
    }

    public <T> void registerMapper(Class<T> type) {
        this.mapperRegistry.addMapper(type);
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverUrl() {
        return driverUrl;
    }

    public void setDriverUrl(String driverUrl) {
        this.driverUrl = driverUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
