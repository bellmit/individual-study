package cn.gyw.corejava.exceptions;

/**
 * 栈为空异常
 * Created by guanyw on 2019/2/26.
 */
public class StackEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StackEmptyException() {
    }

    public StackEmptyException(String message) {
        super(message);
    }

    public StackEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public StackEmptyException(Throwable cause) {
        super(cause);
    }

    public StackEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
