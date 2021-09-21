package cn.gyw.components.web.exceptions;

import java.util.Arrays;

import cn.gyw.components.web.IRespCode;

/**
 * 基础异常
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private IRespCode respCode;

    private Object[] args;

    public BaseException(IRespCode respCode) {
        this(respCode, null, null);
    }

    public BaseException(IRespCode respCode, Object[] args) {
        this(respCode, args, null);
    }

    public BaseException(IRespCode respCode, Object[] args, Throwable t) {
        super(String.join(",", respCode.getMessage(), Arrays.toString(args)), t);
        this.respCode = respCode;
        this.args = args;
    }

    public IRespCode getRespCode() {
		return respCode;
	}

    public Object[] getArgs() {
        return args;
    }
}
