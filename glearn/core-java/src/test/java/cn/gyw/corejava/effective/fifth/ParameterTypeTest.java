package cn.gyw.corejava.effective.fifth;

import org.junit.Test;

import java.util.*;

/**
 * 泛型测试
 */
public class ParameterTypeTest {

    /**
     * 列表优于数组
     */
    @Test
    public void listBetterThanArray() {
        // 编译合法
        Object[] objects = new Long[1];
        objects[0] = "I am gg"; // throw arrayStoreException

        // 编译不合法
//        List<Object> objectList = new ArrayList<Long>(); // Incompatible types
    }

    /**
     * 泛型通配符
     */
    @Test
    public void customeStack() {
        CustomStack<Number> numberStack = new CustomStack<>();
        Iterable<Integer> integers = Arrays.asList(24);
        numberStack.pushAll(integers);

        Collection<Object> datas = new ArrayList<>();
        numberStack.popAll(datas);
    }

    @Test
    public void unionSet() {

        Union union = new Union();

        Set<Integer> integerSet = new HashSet<>();
        Set<Double> doubleSet = new HashSet<>();
        // 显示类型参数
        Set<Number> numberSet = union.<Number>union(integerSet, doubleSet);
    }
}
