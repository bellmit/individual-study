package cn.gyw.corejava.util;

/**
 * RNG rand number generator 工具
 */
public class RngUtil {

    /**
     * 随机数函数
     * @param y
     * @return
     */
    public static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
