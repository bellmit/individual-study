package cn.gyw.backend.components.common.log.entity;

import java.io.Serializable;
import java.util.Date;

public class ApiLog implements Serializable {

    private static final long serialVersionUID = -2819065952237845813L;

    /**
     * 日志id
     */
    private String sequenceNum;

    /**
     * 当前操作人id
     */
    private String loginAccount;

    /**
     * 服务api
     */
    private String host;

    private String port;

    /**
     * 操作请求的链接
     */
    private String actionUrl;

    /**
     * 执行的模块
     */
    private String module;

    /**
     * 执行的方法
     */
    private String method;

    /**
     * 描述
     */
    private String description;

    private String requestData;

    private String responseCode;

    private String responseData;

    /**
     * 执行的时间
     */
    private Date gmtCreate;

    public String getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(String sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "ApiLog [sequenceNum=" + sequenceNum + ", loginAccount=" + loginAccount + ", host=" + host + ", port="
                + port + ", actionUrl=" + actionUrl + ", module=" + module + ", method=" + method + ", description="
                + description + ", requestData=" + requestData + ", responseCode=" + responseCode
				+ ", gmtCreate=" + gmtCreate + "]";
    }
}
