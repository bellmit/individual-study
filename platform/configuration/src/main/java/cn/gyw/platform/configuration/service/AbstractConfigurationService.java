package cn.gyw.platform.configuration.service;

import cn.gyw.platform.configuration.exception.ConfigurationException;
import cn.gyw.platform.configuration.interfaces.IConfiguration;
import cn.gyw.platform.configuration.interfaces.IConfigurationEntryListener;
import cn.gyw.platform.configuration.interfaces.IConfigurationSectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class AbstractConfigurationService implements IConfiguration, Serializable {

    private static final long serialVersionUID = 6425609554696610648L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfigurationService.class);

    // methods about listener
    public static Method METHOD_VALUE_CHANGED;
    public static Method METHOD_ENTRY_REMOVED;
    public static Method METHOD_SECTION_CHANGED;
    public static Method METHOD_SECTION_REMOVED;

    static {
        try {
            METHOD_VALUE_CHANGED = IConfigurationEntryListener.class.getMethod("valueChanged", String.class, String.class);
            METHOD_ENTRY_REMOVED = IConfigurationEntryListener.class.getMethod("entryRemoved", String.class, String.class);
            METHOD_SECTION_CHANGED = IConfigurationSectionListener.class.getMethod("sectionChanged", String.class);
            METHOD_SECTION_REMOVED = IConfigurationSectionListener.class.getMethod("sectionRemoved", String.class);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter writer = new PrintWriter(sw);
            e.printStackTrace(writer);
            LOGGER.error(sw.toString());
        }
    }

    @Override
    public String getValue(String section, String parameterKey) {
        return doGetValue(section, parameterKey);
    }

    @Override
    public String getValue(String section, String parameterKey, String defaultValue) {
        return doGetValue(section, parameterKey, defaultValue);
    }

    @Override
    public void setValue(String section, String parameterKey, String parameterValue) throws ConfigurationException {
        LOGGER.info("set configuration section [{}], key [{}], value [{}]", section, parameterKey, parameterValue);
        try {
            doSetValue(section, parameterKey, parameterValue);
            ConfigurationRegistry.notifyEntryListeners(section, parameterKey, METHOD_VALUE_CHANGED);
            ConfigurationRegistry.notifySectionListeners(section, METHOD_SECTION_CHANGED);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    @Override
    public void removeEntry(String section, String parameterKey) throws ConfigurationException {
        try {
            doRemoveEntry(section, parameterKey);
            ConfigurationRegistry.notifyEntryListeners(section, parameterKey, METHOD_ENTRY_REMOVED);
            ConfigurationRegistry.notifySectionListeners(section, METHOD_SECTION_CHANGED);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    @Override
    public Map<String, String> getSection(String section) {
        return doGetSection(section);
    }

    @Override
    public boolean containsEntry(String section, String parameterKey) {
        return doContainsEntry(section, parameterKey);
    }

    @Override
    public void setSection(String section, Map<String, String> map) throws ConfigurationException {
        try {
            doSetSection(section, map);
            ConfigurationRegistry.notifySectionListeners(section, METHOD_SECTION_CHANGED);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    @Override
    public void removeSection(String section) throws ConfigurationException {
        try {
            doRemoveSection(section);
            ConfigurationRegistry.notifySectionListeners(section, METHOD_SECTION_REMOVED);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    @Override
    public boolean containsSection(String section) {
        return doContainsSection(section);
    }

    protected abstract String doGetValue(String section, String key);

    protected abstract String doGetValue(String section, String key, String defaultValue);

    protected abstract void doSetValue(String section, String key, String value) throws ConfigurationException;

    protected abstract void doRemoveEntry(String section, String key) throws ConfigurationException;

    protected abstract boolean doContainsEntry(String section, String key);

    protected abstract Map<String, String> doGetSection(String section);

    protected abstract void doSetSection(String section, Map<String, String> map) throws ConfigurationException;

    protected abstract void doRemoveSection(String section) throws ConfigurationException;

    protected abstract boolean doContainsSection(String section);
}
