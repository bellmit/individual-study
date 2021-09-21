package cn.gyw.thirdpart.htttpclient.v4_5_3.exception;

public class MethodNotSupportException extends Exception {

	private static final long serialVersionUID = 1L;

	public MethodNotSupportException(String methodName) {
        super("Method " + methodName + " not support!");
    }
}
