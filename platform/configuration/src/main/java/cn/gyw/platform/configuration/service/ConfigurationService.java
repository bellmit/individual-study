package cn.gyw.platform.configuration.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.gyw.platform.configuration.dao.ConfigurationDao;
import cn.gyw.platform.configuration.entity.Configuration;
import cn.gyw.platform.configuration.exception.ConfigurationException;

/**
 * 配置服务
 */
@Service
public class ConfigurationService extends AbstractConfigurationService {

    private static final long serialVersionUID = -925370444278946036L;
    
    @Autowired
    private ConfigurationDao configurationDao;

    @Override
    protected String doGetValue(String section, String key) {
        checkPK(section, key);
        Optional<Configuration> optional = configurationDao.findOne(Example.of(new Configuration(section, key)));
        if (optional.isPresent()) {
            Configuration config = optional.get();
            return Objects.isNull(config.getParameterValue()) ? config.getInitValue() : config.getParameterValue();
        }
        return null;
    }

    @Override
    protected String doGetValue(String section, String key, String defaultValue) {
        String value = doGetValue(section, key);
        if (Objects.isNull(value)) {
            return defaultValue;
        }
        return value;
    }

    @Override
    protected void doSetValue(String section, String key, String value) throws ConfigurationException {
        checkPK(section, key);
        Optional<Configuration> optional = configurationDao.findOne(Example.of(new Configuration(section, key)));
        Configuration config = optional.orElse(new Configuration(section, key, value));
        configurationDao.save(config);
    }

    @Override
    protected void doRemoveEntry(String section, String key) throws ConfigurationException {
        checkPK(section, key);
        configurationDao.delete(new Configuration(section, key));
    }

    @Override
    protected boolean doContainsEntry(String section, String key) {
        String value = doGetValue(section, key);
        return Objects.nonNull(value);
    }

    @Override
    protected Map<String, String> doGetSection(String section) {
        checkPK(section);
        Collection<Configuration> list = configurationDao.findBySection(section);
        if (Objects.nonNull(list) && list.size() > 0) {
            Map<String, String> configMap = new HashMap<>();
            for (Configuration config : list) {
                configMap.put(config.getParameterKey(), getValueOrInitValue(config));
            }
            return configMap;
        }
        return Collections.emptyMap();
    }

    @Override
    protected void doSetSection(String section, Map<String, String> map) throws ConfigurationException {
        doRemoveSection(section);
        if (Objects.nonNull(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                doSetValue(section, entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    protected void doRemoveSection(String section) throws ConfigurationException {
        checkPK(section);
        configurationDao.removeBySection(section);
    }

    @Override
    protected boolean doContainsSection(String section) {
        checkPK(section);
        return configurationDao.exists(Example.of(new Configuration(section)));
    }

    private void checkPK(String... keys) {
        for (String key : keys) {
            if (StringUtils.isEmpty(key)) {
                throw new IllegalArgumentException("section or key must not empty");
            }
        }
    }

    private String getValueOrInitValue(Configuration configuration) {
        return Objects.isNull(configuration.getParameterValue()) ? configuration.getInitValue() :
                configuration.getParameterValue();
    }
}
