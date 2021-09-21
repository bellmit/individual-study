package cn.gyw.corejava.effective.eighth;

import org.junit.Test;

import java.util.Random;

/**
 * 通用程序设计
 */
public class CommonDesignAPI {

    /**
     * float 和 double 不适合做精确计算，尤其是货币计算
     */
    @Test
    public void floatAndDouble() {
        System.out.println(1.03 - 0.43); // 0.6000000000000001
    }

    private static final Random rand = new Random();

    static int random(int n) {
        return Math.abs(rand.nextInt()) % n;
    }

    /**
     * 选取一个范围的随机数 [0, n)
     * 随机数范围概率，前半部分
     */
    @Test
    public void randomLib() {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0 ; i < 100000000 ; i ++) {
            if (random(n) < n / 2) {
                low ++;
            }
        }
        // 0.6666 ,超过了50%
        System.out.println(low / (100000000 * 1.0));

        low = 0; // reset
        // 使用java类库
        for (int i = 0 ; i < 100000000 ; i ++) {
            if (rand.nextInt(n) < n / 2) {
                low ++;
            }
        }
        System.out.println(">>" + low / (100000000 * 1.0));
    }

}
