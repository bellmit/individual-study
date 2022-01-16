package cn.gyw.backend.components.common.exceptions;

import cn.gyw.backend.components.common.IRespCode;

/**
 * 业务相关异常
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException(IRespCode resp, String message) {
        super(resp, message);
    }

    public BusinessException(IRespCode resp, String message, Throwable t) {
        super(resp, message, t);
    }
}
