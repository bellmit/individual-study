package cn.gyw.platform.configuration.entity;

import java.io.Serializable;

/**
 * 主键生成器
 */
public class ConfigurationPK implements Serializable {

    private static final long serialVersionUID = -4997723114907860229L;
    private String section;
    private String parameterKey;

    public ConfigurationPK() {
    }

    public ConfigurationPK(String section, String parameterKey) {
        this.section = section;
        this.parameterKey = parameterKey;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((parameterKey == null) ? 0 : parameterKey.hashCode());
        result = prime * result + ((section == null) ? 0 : section.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ConfigurationPK)) {
            return false;
        }
        ConfigurationPK other = (ConfigurationPK) obj;
        if (parameterKey == null) {
            if (other.parameterKey != null) {
                return false;
            }
        } else if (!parameterKey.equals(other.parameterKey)) {
            return false;
        }
        if (section == null) {
            if (other.section != null) {
                return false;
            }
        } else if (!section.equals(other.section)) {
            return false;
        }
        return true;
    }
}
