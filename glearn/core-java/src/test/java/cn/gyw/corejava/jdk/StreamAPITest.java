package cn.gyw.corejava.jdk;

import cn.gyw.corejava.AbstractTest;
import cn.gyw.corejava.base.enums.FruitsType;
import cn.gyw.corejava.base.model.Fruits;
import cn.gyw.corejava.tuple.TwoTuple;
import cn.gyw.corejava.util.DataGenerator;
import cn.gyw.corejava.util.SystemUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JDK8 Stream 流的测试类
 *
 * 参考：
 * 1. https://www.jianshu.com/p/7eaa0969b424
 */
public class StreamAPITest extends AbstractTest {

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
     * 根据 'year' 分组
     */
    @Test
    public void groupByYear() {
        List<Map<String, Object>> datas = DataGenerator.ofYearPriceMapList();
        Map<String, List<Map<String, Object>>> dataMap = datas.stream()
                .sorted()
                .collect(Collectors.groupingBy((p) -> {
                    return p.get("year").toString();
                }));
        System.out.println(dataMap);
    }

    /**
     * 根据 'year' 和 'price' 排序
     */
    @Test
    public void sortByYearAndPrice() {
        List<Map<String, Object>> datas = DataGenerator.ofYearPriceMapList();
        List<Map<String, Object>> tmp = datas.stream().sorted((t1, t2) -> {
            int ret = Integer.parseInt(t1.get("year").toString()) - Integer.parseInt(t2.get("year").toString());
            if (ret > 0) {
                return 1;
            }
            if (ret < 0) {
                return -1;
            }
            return 0;
        }).collect(Collectors.toList());
        System.out.println(tmp);
    }

    /**
     * 集合的遍历
     */
    @Test
    public void foreachList() {
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    /**
     * 映射结果
     */
    @Test
    public void mapElem() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers.stream().map(i -> i * i).forEach(System.out::println);
    }

    /**
     * 过滤集合
     */
    @Test
    public void filterElem() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }

    /**
     * 终端操作API
     * <p>
     * jdk8 提供了Collectors 工具类方便使用
     * 方法：
     * 1、toList: 转为List
     * 2、joining：合并字符串
     */
    @Test
    public void collectorsElem() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);
    }

    /**
     * Map 方法：数据转换
     */
    @Test
    public void shouldDataTransferByMap() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> outList = new ArrayList<>();
        for (String str : strings) {
            outList.add(str + "_110");
        }

        List<String> outList1 = strings.stream()
                .map(s -> s + "_110")
                .collect(Collectors.toList());
        Assert.assertEquals(outList, outList1);
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

    /******************* Collectors 工具类测试 *********************/

    /**
     * list 转 Map
     */
    @Test
    public void listToMap() {
        // key 唯一，java.lang.IllegalStateException: Duplicate key Fruits
        // Map<FruitsType, Fruits> result1 = fruitsDataList.stream().collect(Collectors.toMap(f -> f.getType(), v -> v));

        // key 不唯一
        Map<FruitsType, List<Fruits>> result2 = fruitsDataList.stream().collect(Collectors.toMap(f -> f.getType(), v -> new ArrayList<>(),
                (v1, v2) -> {
                    return v1;
                }));
        System.out.println("result = " + result2);

        // 不同的返回类型
        Map<FruitsType, Fruits> result3 = fruitsDataList.stream().collect(Collectors.toMap(f -> f.getType(), v -> v,
                (v1, v2) -> {
                    return v1;
                }, TreeMap::new));

    }

    /**
     * 终端操作
     * collect 收集，管道的终点操作，采集数据
     */
    @Test
    public void collectorsTest() {
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
                // new Fruits("梨2", FruitsType.PIE, null),
                new Fruits("梨1", FruitsType.PIE, 30)
        );
        /*
        static <T,K> Collector<T,?,Map<K,List<T>>> groupingBy(Function<? super T,? extends K> classifier)
        单属性分组只需要一个分类函数作为参数，分类函数会作用于流的所有元素，分类函数的返回值将作为分组结果的键
         */
        Map<FruitsType, List<Fruits>> ret1 = fruitsList.stream().collect(Collectors.groupingBy(fruits -> fruits.getType()));
        System.out.println("ret1 = " + ret1);

        /*
        分类函数不仅限于返回一个纯量或字符串，分类结果的键可以是任何类型，但是要求实现了必要的equals和hashcode方法
         */
        Map<TwoTuple<FruitsType, String>, List<Fruits>> ret2 = fruitsList.stream().collect(Collectors.groupingBy(fruits -> {
            return new TwoTuple<>(fruits.getType(), fruits.getDesc());
        }));
        System.out.println("ret2 = " + ret2);

        /*
        修改分组结果的值类型
        默认分组结果的值类型为List，可以通过提供第二个参数来修改返回的类型
         */
        Map<FruitsType, Set<Fruits>> ret3 = fruitsList.stream().collect(Collectors.groupingBy(fruits -> fruits.getType(), Collectors.toSet()));
        System.out.println("ret3 = " + ret3);

        /*
        多条件分组
         */
        Map<Object, Map<Object, List<Fruits>>> ret4 = fruitsList.stream()
                .collect(Collectors.groupingBy(fruits -> fruits.getType(), Collectors.groupingBy(fr -> fr.getDesc())));
        System.out.println("ret4 = " + ret4);

        /*
        分组结果进行Reduce 操作
         */
        Map<FruitsType, Double> avgPrice = fruitsList.stream()
                .collect(Collectors.groupingBy(Fruits::getType, Collectors.averagingInt(f -> f.getPrice())));
        System.out.println("avgPrice = " + avgPrice);

        /*
        分组求和
         */
        Map<FruitsType, Double> sumPrice = fruitsList.stream()
                .collect(Collectors.groupingBy(fruits -> fruits.getType(), Collectors.averagingInt(f -> f.getPrice())));
        System.out.println("sumPrice = " + sumPrice);

        /*
        最高的价格
        PS：分组计算的值，不可为空，否则报java.lang.NullPointerException
         */
        Map<FruitsType, Optional<Fruits>> maxPrice = fruitsList.stream()
                .collect(Collectors.groupingBy(fruits -> fruits.getType(), Collectors.maxBy(Comparator.comparing(Fruits::getPrice))));
        System.out.println("maxPrice = " + maxPrice);

        /*
        获取分组结果统计数据
        Stream专门提供了计算分组结果统计值的接口，统计值包括：最大值、最小值、平均值、总数、总和五个指标
         */
        Map<FruitsType, IntSummaryStatistics> summaryStatistics = fruitsList.stream()
                .collect(Collectors.groupingBy(Fruits::getType, Collectors.summarizingInt(Fruits::getPrice)));
        System.out.println("summaryStatistics = " + summaryStatistics);
    }

    /**
     * 对集合收集后再操作
     */
    @Test
    public void testCollectingAndThen() {
        List<Fruits> fruitsList = Arrays.asList(
                new Fruits("苹果", FruitsType.APPLE, 10),
                new Fruits("红富士", FruitsType.APPLE, 20),
                new Fruits("梨", FruitsType.PIE, 30)
        );
        // 对分组后的Map<FruitsType, List<Fruits>>，继续操作
        int size = fruitsList.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(f -> f.getType()),
                fruitsTypeListMap -> fruitsTypeListMap.size()));
        Assertions.assertEquals(2, size);
    }

    /**
     * 映射关系
     */
    @Test
    public void testMapping() {
        /*
        mapping(Function<? super T, ? extends U> mapper, Collector<? super U, A, R> downstream)
         */
        String result1 =
                fruitsDataList.stream().collect(Collectors.mapping(f -> f.getDesc(), Collectors.joining(",", "[", "]")));
        System.out.println("result1 = " + result1);

        String result2 = fruitsDataList.stream().map(f -> f.getDesc()).collect(Collectors.joining(",", "[", "]"));
        System.out.println("result2 = " + result2);
    }

    /**
     *
     */
    @Test
    public void testPartitioningBy() {
        Map<Boolean, List<Fruits>> result1 =
                fruitsDataList.stream().collect(Collectors.partitioningBy(fruits -> fruits.getPrice() > 10));
        System.out.println("result1 = " + result1);

        Map<Boolean, Long> result2 =
                fruitsDataList.stream().collect(Collectors.partitioningBy(fruits -> fruits.getPrice() > 10, Collectors.counting()));
        System.out.println("result2 = " + result2);
    }

    private List<Fruits> fruitsDataList = Arrays.asList(
            new Fruits("苹果1", FruitsType.APPLE, 10),
            new Fruits("苹果1", FruitsType.APPLE, 20),
            new Fruits("苹果2", FruitsType.APPLE, 30),
            new Fruits("梨1", FruitsType.PIE, 30)
    );
}
