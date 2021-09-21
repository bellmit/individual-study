package cn.gyw.corejava.jdk;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java Development Kit 8 Usage
 */
public class Jdk8UsageTest {

    // ************************ Stream *************************

    /**
     * 创建流的方式
     * 1.
     */
    @Test
    @Ignore
    public void createStream() {
        Stream.of(1, 2, 3).forEach(System.out::println);
        Stream.iterate(0, i -> i + 2).limit(10).collect(Collectors.toList())
                .forEach(System.out::println);
        Collections.EMPTY_LIST.stream();
    }

    /**
     * 并行流测试
     */
    @Test
    @Ignore
    public void parallel() {
        long start = System.nanoTime();
        Stream.iterate(0, i -> i + 1).limit(100000).parallel().forEach(j -> {
        });
        System.out.println("2>cost :" + (System.nanoTime() - start));
    }

    /**
     * 串行流测试
     */
    @Test
    @Ignore
    public void nonParallel() {
        long start = System.nanoTime();
        Stream.iterate(0, i -> i + 1).limit(100000).forEach(j -> {
        });
        System.out.println("1>cost :" + (System.nanoTime() - start));
    }

    /**
     * 管道运算
     */
    @Test
    public void middleState() {
        Arrays.asList(1, 2, 20, 23, 24, 25, 40).stream()
                .filter(item -> item >= 20) // 过滤操作
                .filter(item -> item < 40)
                .map(data -> data + 2).collect(Collectors.toList())
                .forEach(System.out::println);
    }

    /**
     * flatMap api
     */
    @Test
    public void flatMapTest() {
        Arrays.asList("章三 1234", "王五 2345").stream()
                .flatMap(item -> Stream.of(item.split(" ")))
                .forEach(System.out::println);
    }

    /**
     * 终端状态
     * reduce 合并操作
     */
    @Test
    public void reduceTest() {
        int result = Arrays.asList(1, 2, 3, 4).stream().reduce((t, n) -> t + n).get();
        System.out.println(">>" + result);
    }

    /**
     * 终端操作
     * collect 收集，管道的终点操作，采集数据
     */
    @Test
    public void collectTest() {
        int ret = 0;
        Arrays.asList(1, 2, 3, 4).stream().collect(Collectors.reducing((i, j) -> i + j));
        Arrays.asList(1, 2, 3, 4).stream().collect(Collectors.toList());
        ret = Arrays.asList(1, 2, 3, 4).stream().collect(Collectors.maxBy((t1, t2) -> {
            if (t1 > t2) {
                return 1;
            } else {
                return -1;
            }
        })).get();
        System.out.println(ret);

        String result = Arrays.asList("1", "2", "3").stream().collect(Collectors.joining("^^&"));
        System.out.println(result);
    }

    /**
     * 数值统计
     *
     * IntStream
     * DoubleStream
     * LongStream
     */
    @Test
    public void numStatic() {
        // IntStream
        IntSummaryStatistics intSummary = Arrays.asList(1, 2, 3, 4, 5, 6).stream().mapToInt(i -> i + 1).summaryStatistics();
        System.out.println("Max:" + intSummary.getMax());
        System.out.println("Average:" + intSummary.getAverage());
        System.out.println("Count:" + intSummary.getCount());
        System.out.println("Sum:" + intSummary.getSum());
    }
}
