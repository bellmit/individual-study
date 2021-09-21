package cn.gyw.glearn.algorithm.interview;

import java.lang.reflect.Field;

/**
 * 面试题：
 * 两个Integer 数据交换
 *
 * 考察知识点：
 * 1.java 方法参数都是按值传递，只是基本数据类型传递值的副本，引用类型传递引用地址；
 * 2.java 自动装箱、拆箱
 *   javap -v 反编译class文件，num1 = Integer.valueOf(1)
 * 3.Integer.valueOf ,缓存机制，-128 ~ 127 直接返回 IntegerCache对应的值
 * 4.反射修改Integer 类中private final 字段 value值
 */
public class TwoIntegerSwap {

    private static void swap(Integer num1, Integer num2) throws NoSuchFieldException, IllegalAccessException {
//        Integer tmp = num1; // 引用num1的地址，在setInt会被修改
        Integer tmp = new Integer(num1); // new 一个新的存储空间地址
        Field value = Integer.class.getDeclaredField("value");
        value.setAccessible(true);
        value.setInt(num1, num2);
        num2 = tmp;
        System.out.println("after swap : " + num1 + "<>" + num2);
    }

    public static void main(String[] args) throws Exception {
        Integer num1 = 1, num2 = 2;
        System.out.println(num1.hashCode() + " ; " + num2.hashCode());
        System.out.println("before swap : " + num1 + "<>" + num2);
        swap(num1, num2);
        System.out.println(num1.hashCode() + " ; " + num2.hashCode());
        System.out.println("final result : " + num1 + "<>" + num2);
    }
}
