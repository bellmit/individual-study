package cn.gyw.corejava.exceptions;

/**
 * 异常工具
 */
public final class ExUtil {

    /**
     * 如果Throwable 是Error ，那么抛出它；
     * 如果是RuntimeException，那么返回它；
     * 否则返回：IllegalStateException；
     * @param t
     * @return
     */
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }

    private ExUtil() {}
}
