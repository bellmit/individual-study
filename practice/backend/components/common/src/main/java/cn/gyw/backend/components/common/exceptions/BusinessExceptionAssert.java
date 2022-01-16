package cn.gyw.backend.components.common.exceptions;

import cn.gyw.backend.components.common.IRespCode;

/**
 * 业务异常断言
 */
public interface BusinessExceptionAssert extends IRespCode, IExceptionAssert {

    @Override
    default BaseException newException(String message) {
        return new BusinessException(this, message);
    }

    @Override
    default BaseException newException(Throwable t, String message) {
        return new BusinessException(this, message, t);
    }
}
