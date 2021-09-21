package cn.gyw.corejava.jdk;

import cn.gyw.corejava.AbstractTest;
import cn.gyw.corejava.util.DataGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JDK8 Stream 流的测试类
 */
public class StreamAPITest extends AbstractTest {

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
     * 流的创建方式
     */
    @Test
    public void createStream() {
        // 1.集合 创建串行流
        List<String> stringList = Arrays.asList("abc", "bc", "efg", "");
        List<String> result = stringList.stream()
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
        result.forEach(System.out::println);
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
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }

    /**
     * 终端操作API
     *
     * jdk8 提供了Collectors 工具类方便使用
     * 方法：
     * 1、toList: 转为List
     * 2、joining：合并字符串
     */
    @Test
    public void collectorsElem() {
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
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
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> outList = new ArrayList<>();
        for (String str : strings) {
            outList.add(str + "_110");
        }

        List<String> outList1 = strings.stream()
                .map(s -> s + "_110")
                .collect(Collectors.toList());
        Assert.assertEquals(outList, outList1);
    }
}
