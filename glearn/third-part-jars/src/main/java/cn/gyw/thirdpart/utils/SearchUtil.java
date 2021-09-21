package cn.gyw.thirdpart.utils;

import java.util.Comparator;
import java.util.List;

/**
 * 搜索工具
 *
 * Created by guanyw on 2019/1/9.
 */
public class SearchUtil {

    /**
     * 折半查找 （有序数组查找算法)
     * @param list 可变数组
     * @param val 查找对象
     * @param <T>
     */
    public static <T extends Comparable<T>> int binarySearch(List<T> list, T val) {
        return binarySearch(list, val, 0, list.size() - 1);
    }

    /**
     * 折半查找 (有序数组查找算法)
     * @param list
     * @param val
     * @param comparator
     * @param <T>
     * @return
     */
    public static <T> int binarySearch(List<T> list, T val, Comparator<T> comparator) {
        int low = 0, mid = 0, high = list.size() - 1;
        do {
            mid = low + (high - low) / 2;
            int cmp = comparator.compare(val, list.get(mid));
            if (cmp == 0) {
                return mid;
            }
            if (cmp > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        } while (low >= high);

        return -1;
    }

    /*
    递归查询
     */
    private static <T extends Comparable<T>> int binarySearch(List<T> list, T val, int low, int high) {
        // 收敛条件
        if (low <= high) {
            int mid = low + (high - low) >> 1;
            if (val.compareTo(list.get(mid)) == 0) {
                return mid;
            } else if (val.compareTo(list.get(mid)) < 0) {
                return binarySearch(list, val, mid + 1, high);
            } else if (val.compareTo(list.get(mid)) > 0) {
                return binarySearch(list, val, low, mid - 1);
            }
        }
        return -1;
    }

    private SearchUtil() {}
}
