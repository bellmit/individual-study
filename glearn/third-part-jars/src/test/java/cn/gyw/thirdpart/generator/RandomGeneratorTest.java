package cn.gyw.thirdpart.generator;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.junit.Test;

/**
 * 随机生成器测试
 */
public class RandomGeneratorTest {

    RandomGenerator randomGenerator = new JDKRandomGenerator();

    /**
     * int 型生成器
     */
    @Test
    public void intGenerator() {
        for (int i = 0 ; i < 10 ; i ++) {
            System.out.println(randomGenerator.nextInt());
        }
    }

    /**
     * Long 型 生成器
     */
    @Test
    public void longGenerator() {
        for (int i = 0 ; i < 50 ; i ++) {
            System.out.println(randomGenerator.nextLong());
        }
    }
}
