package cn.gyw.thirdpart.generator;

import org.apache.commons.text.RandomStringGenerator;

import java.util.Random;

/**
 * 随机数据生成器
 * <p>
 * 说明：
 * 1.策略设计模式：每一个不同的Generator都表示一个不同的策略
 * 2.嵌套的Generator惯用法
 */
public class RandomGenerator {

    private static Random r = new Random(47);

    private final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 随机整数
     *
     * @return
     */
    public static int randInt() {
        return new IntegerGenerator().next();
    }

    /**
     *
     * @return 长整型
     */
    public static long randLong() {
        return new LongGenerator().next();
    }

    /**
     * 随机布尔值
     *
     * @return
     */
    public static boolean randBoolean() {
        return new BooleanGenerator().next();
    }

    /**
     * 随机字符串
     *
     * Generates random Unicode strings containing the specified number of code points. Instances are created using a builder class, which allows the callers to define the properties of the generator. See the documentation for the RandomStringGenerator.Builder class to see available properties.
     * <p>
     * // Generates a 20 code point string, using only the letters a-z
     * RandomStringGenerator generator = new RandomStringGenerator.Builder()
     * .withinRange('a', 'z').build();
     * String randomLetters = generator.generate(20);
     * <p>
     * <p>
     * // Using Apache Commons RNG for randomness
     * UniformRandomProvider rng = RandomSource.create(...);
     * // Generates a 20 code point string, using only the letters a-z
     * RandomStringGenerator generator = new RandomStringGenerator.Builder()
     * .withinRange('a', 'z')
     * .usingRandom(rng::nextInt) // uses Java 8 syntax
     * .build();
     * String randomLetters = generator.generate(20);
     * <p>
     * RandomStringGenerator instances are thread-safe when using the default random number generator (RNG). If a custom RNG is set by calling the method Builder.usingRandom(TextRandomProvider), thread-safety must be ensured externally.
     *
     * @return
     */
    public static String randString() {
        return randString(20);
    }

    /**
     * 随机字符串
     * @param length 指定长度
     * @return 字符串
     */
    public static String randString(int length) {
        // Generates a 20 code point string, using only the letters a-z
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').build();
        return generator.generate(length);
    }

    private static class BooleanGenerator implements Generator<Boolean> {
        @Override
        public Boolean next() {
            return r.nextBoolean();
        }
    }

    /**
     * Integer生成器
     */
    private static class IntegerGenerator implements Generator<Integer> {
        private int mod = 10000;

        @Override
        public Integer next() {
            return r.nextInt(mod);
        }
    }

    /**
     * Long 型生成器
     */
    private static class LongGenerator implements Generator<Long> {

        private Long range = 10000000000L;

        @Override
        public Long next() {
            return r.nextLong();
        }
    }

    /**
     * 私有化工具类构造器
     */
    private RandomGenerator() {}
}