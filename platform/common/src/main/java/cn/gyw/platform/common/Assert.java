package cn.gyw.platform.common;

/**
 * 断言
 */
public abstract class Assert {

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    
}
