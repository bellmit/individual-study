package cn.gyw.middleware.activemq.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ActiveMQ 配置
 */
public final class ActiveMQConfig {

    private final static String CONFIG_FILE = "config.properties";

    private final Properties properties = new Properties();

    public static ActiveMQConfig build() {
        return Singleton.INSTANCE.getInstance();
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    private void init() {
        InputStream in = null;
        try {
            in = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private ActiveMQConfig() {
        init();
    }

    private enum Singleton {
        INSTANCE;

        private ActiveMQConfig instance;

        Singleton() {
            this.instance = new ActiveMQConfig();
        }

        ActiveMQConfig getInstance() {
            return this.instance;
        }
    }
}
