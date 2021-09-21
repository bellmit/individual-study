package cn.gyw.components.web.exceptions;

import cn.gyw.components.web.IRespCode;

/**
 * 业务异常断言
 */
public interface BusinessExceptionAssert extends IRespCode, IExceptionAssert {

    @Override
    default BaseException newException(Object... args) {
        return new BusinessException(this, args);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        return new BusinessException(this, args, t);
    }
}
