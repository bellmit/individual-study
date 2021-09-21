package cn.gyw.components.web.exceptions;

import cn.gyw.components.web.IRespCode;

/**
 * 业务相关异常
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException(IRespCode resp, Object[] args) {
        super(resp, args);
    }

    public BusinessException(IRespCode resp, Object[] args, Throwable t) {
        super(resp, args, t);
    }
}
