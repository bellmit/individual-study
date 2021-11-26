package cn.gyw.components.web.exceptions;

import cn.gyw.components.web.IRespCode;

public interface CommonExceptionAssert extends IRespCode, IExceptionAssert {

	@Override
	default BaseException newException(String message) {
		return new CommonException(this, message);
	}
	
	@Override
	default BaseException newException(Throwable t, String message) {
		return new CommonException(this, message, t);
	}
}
