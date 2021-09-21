package cn.gyw.components.web.exceptions;

import cn.gyw.components.web.IRespCode;

public interface CommonExceptionAssert extends IRespCode, IExceptionAssert {

	@Override
	default BaseException newException(Object... args) {
		return new CommonException(this, args);
	}
	
	@Override
	default BaseException newException(Throwable t, Object... args) {
		return new CommonException(this, args, t);
	}
}
