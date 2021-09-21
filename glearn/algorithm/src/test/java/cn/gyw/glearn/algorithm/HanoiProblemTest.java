package cn.gyw.glearn.algorithm;

import org.junit.Test;

import cn.gyw.glearn.algorithm.stack.HanoiProblem;

/**
 * 汉诺塔问题测试
 */
public class HanoiProblemTest {

    /**
     * 方式一、递归
     */
    @Test
    public void shouldResolveByMethod1() {
        HanoiProblem hanoiProblem = new HanoiProblem();
        int step = hanoiProblem.solveMethod1(2, "L", "M", "R");
        System.out.printf("It will move %d steps", step);
    }

    /**
     * 方式二、非递归，栈实现
     */
    @Test
    public void shouldResolveByMethod2() {
        HanoiProblem hanoiProblem = new HanoiProblem();
        int step = hanoiProblem.solveMethod2(2, "LS", "MS", "RS");
        System.out.printf("It will move %d steps", step);
    }
}
