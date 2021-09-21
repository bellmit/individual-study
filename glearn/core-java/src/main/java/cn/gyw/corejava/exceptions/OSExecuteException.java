package cn.gyw.corejava.exceptions;

/**
 * 系统运行时异常
 */
public class OSExecuteException extends RuntimeException {

	public OSExecuteException(String reason) {
		super(reason);
	}

}
