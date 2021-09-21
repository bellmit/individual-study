package cn.gyw.backend.system.enums;

import cn.gyw.components.web.exceptions.BusinessExceptionAssert;

/**
 * 系统服务异常
 */
public enum SystemExceptionEnum implements BusinessExceptionAssert {

    USER_NOT_EXISTS(60001, "用户账号或密码错误")
    ;

    private SystemExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
    
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
