package cn.gyw.platform.common.httpclient.exception;

public class MethodNotSupportException extends Exception {

	private static final long serialVersionUID = 1L;

	public MethodNotSupportException(String methodName) {
        super("Method " + methodName + " not support!");
    }
}
