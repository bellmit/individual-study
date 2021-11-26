package cn.gyw.components.web.exceptions;

import java.util.Arrays;

import cn.gyw.components.web.IRespCode;

/**
 * 基础异常
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private IRespCode respCode;

    public BaseException(IRespCode respCode) {
        this(respCode, null, null);
    }

    public BaseException(IRespCode respCode, String message) {
        this(respCode, message, null);
    }

    public BaseException(IRespCode respCode, String message, Throwable t) {
        super(String.join("|", respCode.getMessage(), message), t);
        this.respCode = respCode;
    }

    public IRespCode getRespCode() {
        return respCode;
    }
}
