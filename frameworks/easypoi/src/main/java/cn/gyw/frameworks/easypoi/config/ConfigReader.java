package cn.gyw.frameworks.easypoi.config;

import cn.gyw.platform.common.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfigReader {

    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);

    private static final String CONFIG_FILE = "config.properties";

    private String inputFilePath;
    private String outFilePath;
    private Map<String, Integer> rules = new HashMap<>();

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public Map<String, Integer> getRules() {
        return rules;
    }

    public static ConfigReader getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final ConfigReader INSTANCE = new ConfigReader();
    }

    private ConfigReader() {
        Properties configProps = readProps();
        if (configProps == null) {
            System.err.println("配置文件不存在[config.properties]");
            return;
        }
        this.inputFilePath = configProps.getProperty("excel.input.file");
        this.outFilePath = configProps.getProperty("excel.out.path");
        configProps.forEach((key, value) -> {
            if (String.valueOf(key).startsWith("rule")) {
                String[] ruleTuple;
                ruleTuple = new String(value.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)
                        .split("\\|");
                rules.put(ruleTuple[0], Integer.parseInt(ruleTuple[1]));
            }
        });
        log.debug("rules :{}", rules);
    }

    private Properties readProps() {
        Properties localProps = PropertiesUtil.read("./" + CONFIG_FILE);
        Properties clzProps = PropertiesUtil.read(CONFIG_FILE);
        if (localProps == null) {
            return clzProps;
        }
        if (clzProps != null) {
            clzProps.keySet().forEach((key) -> {
                if (!localProps.containsKey(key)) {
                    localProps.put(key, clzProps.get(key));
                }
            });
        }
        return localProps;
    }
}
