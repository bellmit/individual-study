package cn.gyw.glearn.design.creational.simplefactory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 计算器 实现
 */
public class CalculatorImplTest {

    /**
     * 实现简单的计算
     * <p>
     * 参数：两个数、运算符号
     */
    @Test
    public void shouldSimpleCalc() {

        Calculator calculator = new Calculator();
        assertEquals(3, calculator.calc1("+", 1, 2));
    }

    /**
     * 简单工厂方法，解耦客户端和业务代码
     */
    @Test
    public void shouldExtendCalc() {
        Operation operation = Calculator.getOperation("*", 1, 2);
        assertEquals(2, operation.doGetResult());
    }
}
