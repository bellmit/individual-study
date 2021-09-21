package cn.gyw.platform.configuration.exception;

/**
 * Configuration Exception
 */
public class ConfigurationException extends Exception {

    private static final long serialVersionUID = -5191760826217198395L;

    public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
