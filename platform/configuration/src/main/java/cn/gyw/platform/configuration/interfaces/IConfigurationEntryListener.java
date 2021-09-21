package cn.gyw.platform.configuration.interfaces;

/**
 * entry listener
 */
public interface IConfigurationEntryListener {

    void valueChanged(String section, String parameterKey);

    void entryRemoved(String section, String parameterKey);
}
