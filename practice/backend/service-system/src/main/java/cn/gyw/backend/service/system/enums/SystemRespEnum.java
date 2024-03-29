package cn.gyw.backend.service.system.enums;

import cn.gyw.backend.components.common.exceptions.BusinessExceptionAssert;

public enum SystemRespEnum implements BusinessExceptionAssert {
    REGISTER_FAILED(11001, "注册失败"),
    LOGIN_FAILED(11002, "用户名或密码不正确"),
    USER_DISABLE(11003, "用户禁用"),
    PASSWORD_ERROR(11004, "密码不正确"),
    ALLOC_ROLE_FAILED(11005, "分配角色失败"),
    ADD_MENU_FAILED(11006, "新增菜单失败"),
    ;

    private int code;
    private String message;

    SystemRespEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
