package cn.gyw.backend.components.common.exceptions;

import cn.gyw.backend.components.common.IRespCode;

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
