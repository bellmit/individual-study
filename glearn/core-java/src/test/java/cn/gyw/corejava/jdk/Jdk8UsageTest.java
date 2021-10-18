package cn.gyw.corejava.jdk;

import cn.gyw.corejava.tuple.TwoTuple;
import cn.gyw.corejava.util.SystemUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java Development Kit 8 Usage
 */
public class Jdk8UsageTest {

    // ************************ Stream *************************

    /**
     * 创建流的方式
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
                .peek((item) -> {
                    System.out.println("peek 当前数据：" + item);
                })
                .distinct() // 去重
                .limit(100) // 限制大小
                .filter(item -> item >= 20) // 过滤操作
                .filter(item -> item < 40)
                .map(data -> data + 2).collect(Collectors.toList())
                .forEach(System.out::println);
    }

    /**
     * flatMap api
     * 二维映射，flatMap()操作为每个输入值生成任意数量（零个或多个）的输出值
     * <p>
     * 合并两个Stream 成一个Stream
     */
    @Test
    public void flatMapTest() {
        List<String> fun1 = Arrays.asList("one", "two", "three");
        List<String> fun2 = Arrays.asList("four", "five", "six");
        List<List<String>> nestedList = Arrays.asList(fun1, fun2);

        // 遍历
        List<Stream<String>> result1 = nestedList.stream().map(x -> x.stream().map(String::toUpperCase))
                .collect(Collectors.toList());
        System.out.println("map方法：对象嵌套集合，返回的还是Stream ：" + result1);
        // 用flatMap 替换，相当于把两个Stream 合并成一个Stream
        SystemUtil.printCutOffRule();
        List<String> result2 = nestedList.stream().flatMap(x -> x.stream().map(String::toUpperCase))
                .collect(Collectors.toList());
        System.out.println("flatMap方法：对象嵌套集合，返回的是基础数据 ：" + result2);
    }

    /**
     * 终端状态
     * reduce 合并操作
     * <p>
     * BinaryOperator<T>是一个函数式接口【2】，代表一个在两个操作数上执行的操作，生成一个和操作数类型相同的结果
     */
    @Test
    public void reduceTest() {
        int result = 0;
        // 1. Optional<T> reduce(BinaryOperator<T> accumulator)
        result = Arrays.asList(1, 2, 3, 4).stream().reduce((num1, num2) -> num1 + num2).get();
        Assert.assertEquals(10, result);

        // 2. T reduce(T identity,  BinaryOperator<T> accumulator)
        // 自动处理stream为空的情况 && 自定义初始值
        List<Integer> list = new ArrayList<>();
        result = list.stream().reduce(10, (num1, num2) -> num1 + num2);
        Assert.assertEquals(10, result);
        result = Stream.of(1, 2).reduce(10, (num1, num2) -> num1 + num2);
        Assert.assertEquals(13, result);

        /* 3.
        <U> U reduce (U identity,
              BiFunction<U,? super [T],U> accumulator,
              BinaryOperator<U> combiner)
         */

    }

    /**
     * 终端操作
     * collect 收集，管道的终点操作，采集数据
     */
    @Test
    public void collectTest() {
        int ret = 0;
        // 合并计算
        ret = Arrays.asList(1, 2, 3, 4).stream().collect(Collectors.reducing((i, j) -> i + j)).get();
        // 转List
        Arrays.asList(1, 2, 3, 4).stream().collect(Collectors.toList());
        // 最大值
        ret = Arrays.asList(1, 2, 3, 4).stream().collect(Collectors.maxBy((t1, t2) -> {
            if (t1 > t2) {
                return 1;
            } else {
                return -1;
            }
        })).get();
        System.out.println(ret);
        // 字符串拼接
        String result = Arrays.asList("1", "2", "3").stream().collect(Collectors.joining("^^&"));
        System.out.println(result);
    }

    /**
     * 分组函数
     */
    @Test
    public void groupByTest() {
        List<Fruits> fruitsList = Arrays.asList(
                new Fruits("苹果1", FruitsType.APPLE, 10),
                new Fruits("苹果1", FruitsType.APPLE, 20),
                new Fruits("苹果2", FruitsType.APPLE, 30),
                new Fruits("梨1", FruitsType.PIE, 30)
        );
        /*
        static <T,K> Collector<T,?,Map<K,List<T>>> groupingBy(Function<? super T,? extends K> classifier)
        单属性分组只需要一个分类函数作为参数，分类函数会作用于流的所有元素，分类函数的返回值将作为分组结果的键
         */
        Map<FruitsType, List<Fruits>> ret1 = fruitsList.stream().collect(Collectors.groupingBy(fruits -> fruits.type));
        System.out.println("ret1 = " + ret1);

        /*
        分类函数不仅限于返回一个纯量或字符串，分类结果的键可以是任何类型，但是要求实现了必要的equals和hashcode方法
         */
        Map<TwoTuple<FruitsType, String>, List<Fruits>> ret2 = fruitsList.stream().collect(Collectors.groupingBy(fruits -> {
            return new TwoTuple<>(fruits.type, fruits.desc);
        }));
        System.out.println("ret2 = " + ret2);

        /*
        修改分组结果的值类型
        默认分组结果的值类型为List，可以通过提供第二个参数来修改返回的类型
         */
        Map<FruitsType, Set<Fruits>> ret3 = fruitsList.stream().collect(Collectors.groupingBy(fruits -> fruits.type, Collectors.toSet()));
        System.out.println("ret3 = " + ret3);

        /*
        多条件分组
         */
        Map<String, Map<FruitsType, List<Fruits>>> ret4 = fruitsList.stream().collect(Collectors.groupingBy(fruits -> fruits.type, Collectors.groupingBy(fr -> fr.desc)));
    }

    /**
     * 数值统计
     * <p>
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

    enum FruitsType {
        APPLE, PIE;
    }

    class Fruits {
        String desc;
        FruitsType type;
        int price;

        public Fruits(String desc, FruitsType type, int price) {
            this.desc = desc;
            this.type = type;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Fruits{" +
                    "desc='" + desc + '\'' +
                    ", type=" + type +
                    ", price=" + price +
                    '}';
        }
    }

}
