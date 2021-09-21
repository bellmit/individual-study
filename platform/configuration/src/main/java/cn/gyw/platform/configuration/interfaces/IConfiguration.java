package cn.gyw.platform.configuration.interfaces;

import java.util.Map;

import cn.gyw.platform.configuration.exception.ConfigurationException;

/**
 * Configuration 配置接口
 */
public interface IConfiguration {

    /**
     *
     * @param section the configuration section
     * @param parameterKey the configuration parameterKey
     * @return
     */
    String getValue(String section, String parameterKey);

    String getValue(String section,String parameterKey, String defaultValue);

    void setValue(String section, String parameterKey, String parameterValue) throws ConfigurationException;

    void removeEntry(String section, String parameterKey) throws ConfigurationException;

    boolean containsEntry(String section, String parameterKey);

    /**
     * key-value pair on same section
     * @param section
     * @return
     */
    Map<String, String> getSection(String section);

    /**
     * add key-value pair on same section
     * @param section
     * @param map
     * @throws ConfigurationException
     */
    void setSection(String section, Map<String, String> map) throws ConfigurationException;

    /**
     * remove all key-value pairs on section
     * @param section
     * @throws ConfigurationException
     */
    void removeSection(String section) throws ConfigurationException;

    boolean containsSection(String section);
}
