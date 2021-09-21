package cn.gyw.platform.configuration.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 配置模型
 */
@IdClass(ConfigurationPK.class)
@Cacheable
@Entity
@Table
public class Configuration implements Serializable {

    private static final long serialVersionUID = -6617677367372895060L;

    @Id
    private String section;

    @Id
    private String parameterKey;

    @Column
    private String parameterValue;

    @Column
    private String initValue;

    public Configuration() {
        super();
    }

    public Configuration(String section) {
        this.section = section;
    }

    public Configuration(String section, String parameterKey) {
        this.section = section;
        this.parameterKey = parameterKey;
    }

    public Configuration(String section, String parameterKey, String parameterValue) {
        this.section = section;
        this.parameterKey = parameterKey;
        this.parameterValue = parameterValue;
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

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }
}
