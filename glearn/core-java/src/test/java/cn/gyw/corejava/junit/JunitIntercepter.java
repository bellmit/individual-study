package cn.gyw.corejava.junit;

/**
 * 自定义junit 测试拦截器
 */
public interface JunitIntercepter {

    /**
     * 拦截之前
     */
    void interceptBefore();

    /**
     * 拦截之后
     */
    void interceptAfter();
}
