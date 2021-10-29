package cn.gyw.corejava.collections;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description Map 用法测试
 * @createdTime 2021/10/11 16:54
 */
public class MapUsageTest {

    /**
     * 测试计算方法 compute
     */
    @Test
    public void testCompute() {
        Map<String, Integer> map = new HashMap<>();
        // map.put("a", 1);
        map.put("b", 2);
        System.out.println(">>" + map);
        // 存在，修改；不存在，创建
        map.compute("c", (k, v) -> {
            System.out.println("compute1 param:" + k + "-" + v);
            return 3;
        });
        System.out.println("compute1<<" + map);
        map.compute("b", (k, v) -> {
            System.out.println("compute2 param:" + k + "-" + v);
            return 3;
        });
        System.out.println("compute2<<" + map);
        // 不存在，则创建；存在，原值
        Integer ret = map.computeIfAbsent("a", (v) -> {
            System.out.println("param :" + v);
            return 10;
        });
        System.out.println("computeIfAbsent<<" + map + ", ret :" + ret);
        // 存在，则修改；不存在，不创建
        map.computeIfPresent("a", (k, v) -> {
            System.out.println("param :" + k + "-" + v);
            return 10;
        });
        System.out.println("computeIfPresent<<" + map);
    }

    /**
     * 测试方法 getOrDefault
     */
    @Test
    public void testGetOrDefault() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", null);

        System.out.println(map.getOrDefault("1", 999));
    }
}
