package cn.gyw.backend.components.common.exceptions;

import cn.gyw.backend.components.common.IRespCode;

/**
 * 通用异常类
 */
public class CommonException extends BaseException {

	private static final long serialVersionUID = 1L;

	public CommonException(IRespCode resp) {
		super(resp);
	}

	public CommonException(IRespCode resp, String message) {
		super(resp, message);
	}

	public CommonException(IRespCode resp, String message, Throwable t) {
		super(resp, message, t);
	}

}
