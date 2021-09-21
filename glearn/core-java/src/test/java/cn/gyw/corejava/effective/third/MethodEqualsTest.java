package cn.gyw.corejava.effective.third;

import org.junit.Assert;
import org.junit.Test;

/**
 * equals 测试
 */
public class MethodEqualsTest {

    /**
     * 一致性测试
     */
    @Test
    public void transitivity() {
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLACK);

        Assert.assertTrue(p1.equals(p2));
        Assert.assertTrue(p2.equals(p3));
        Assert.assertTrue(p1.equals(p3));
    }
}
