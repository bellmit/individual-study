package cn.gyw.backend.system.dto;

import java.io.Serializable;

public class StatsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Object key;
    
    private Object value;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
}
