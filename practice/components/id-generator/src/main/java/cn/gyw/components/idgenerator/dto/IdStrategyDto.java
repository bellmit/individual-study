package cn.gyw.components.idgenerator.dto;

import java.io.Serializable;

import cn.gyw.platform.common.model.ToStringObject;

public class IdStrategyDto extends ToStringObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bizTag;
    
    private Long maxId;
    
    private Integer step;
    
    private String bizDesc;
    
    public String getBizTag() {
        return bizTag;
    }

    public void setBizTag(String bizTag) {
        this.bizTag = bizTag;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getBizDesc() {
        return bizDesc;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }

}
