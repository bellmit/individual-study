package cn.gyw.platform.configuration.interfaces;

/**
 * section listener
 */
public interface IConfigurationSectionListener {

    void sectionChanged(String section);

    void sectionRemoved(String section);
}
