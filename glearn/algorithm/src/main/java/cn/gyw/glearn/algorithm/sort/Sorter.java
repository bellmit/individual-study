package cn.gyw.glearn.algorithm.sort;

import java.util.Comparator;

/**
 * 排序器接口
 *
 * @description 策略模式（封装算法到共同的接口的实现子类中，便于互相替换）
 *
 * Created by guanyw on 2019/1/9.
 */
public interface Sorter {

    /**
     * 数组排序
     * @param list 待排序数组
     * @param <T> 实现Comparable接口的类
     */
    <T extends Comparable<T>> void sort(T[] list);

    /**
     * 数组排序
     * @param list 待排序数组
     * @param comparator 比较器
     * @param <T>
     */
    <T> void sort(T[] list, Comparator<T> comparator);
}
