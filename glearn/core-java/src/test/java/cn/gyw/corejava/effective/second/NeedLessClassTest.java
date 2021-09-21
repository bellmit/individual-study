package cn.gyw.corejava.effective.second;

import cn.gyw.corejava.AbstractTest;
import org.junit.Test;

/**
 * 避免构建多余的对象
 */
public class NeedLessClassTest extends AbstractTest {

    /**
     * 无意识的自动装箱
     * Time elapse : 4.968 s
     */
    @Test
    public void shouldAutoBox() {
        Long sum = 0L;
        for (int i = 0 ; i < Integer.MAX_VALUE ; i ++) {
            sum += i;
        }
        System.out.println("1>>" + sum);
    }

    /**
     * Time elapse : 0.957 s
     */
    @Test
    public void shouldNoAutoBox() {
        long sum = 0L;
        for (int i = 0 ; i < Integer.MAX_VALUE ; i ++) {
            sum += i;
        }
        System.out.println("2>>" + sum);
    }
}
