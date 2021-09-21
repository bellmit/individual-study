package cn.gyw.platform.configuration.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.gyw.platform.configuration.exception.ConfigurationException;

@Service
public class ConfigurationServiceOnFileINI extends AbstractConfigurationService {

    private static final long serialVersionUID = 2166333706628099132L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationServiceOnFileINI.class);

    private ReloadingFileBasedConfigurationBuilder<INIConfiguration> builder = null;

    private INIConfiguration configuration = null;

    private Lock lock = new ReentrantLock();

    @Override
    protected String doGetValue(String section, String key) {
        return null;
    }

    @Override
    protected String doGetValue(String section, String key, String defaultValue) {
        return null;
    }

    @Override
    protected void doSetValue(String section, String key, String value) throws ConfigurationException {

    }

    @Override
    protected void doRemoveEntry(String section, String key) throws ConfigurationException {

    }

    @Override
    protected boolean doContainsEntry(String section, String key) {
        return false;
    }

    @Override
    protected Map<String, String> doGetSection(String section) {
        return null;
    }

    @Override
    protected void doSetSection(String section, Map<String, String> map) throws ConfigurationException {

    }

    @Override
    protected void doRemoveSection(String section) throws ConfigurationException {

    }

    @Override
    protected boolean doContainsSection(String section) {
        return false;
    }

    private class BuilderChangeListener implements EventListener<ConfigurationBuilderEvent> {

        @Override
        public void onEvent(ConfigurationBuilderEvent event) {
            String type = event.getEventType().getName();
            LOGGER.info("type [{}]", type);
            if (type.equals("RESET")) {
                lock.lock();
                try {
                    INIConfiguration newConfig = builder.getConfiguration();
                    // backup original config, and reset
                    INIConfiguration oldConfig = (INIConfiguration) configuration.clone();
                    configuration = newConfig;
                    List<String> oldSections = sortSections(oldConfig);
                    List<String> newSections = sortSections(newConfig);

                    int len = oldSections.size() < newSections.size() ? oldSections.size() : newSections.size();
                    for (int i = 0; i < len; i++) {
                        String oldSection = preHandleSection(oldSections.get(i));
                        String newSection = preHandleSection(newSections.get(i));
                    }
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }

        private List<String> sortSections(INIConfiguration config) {
            List<String> sections = new ArrayList<String>();
            sections.addAll(config.getSections());
            Collections.sort(sections);
            return sections;
        }
        
        private String preHandleSection(String section) {
            return section.replaceAll("\\.\\.", ".");
        }
    }
}
