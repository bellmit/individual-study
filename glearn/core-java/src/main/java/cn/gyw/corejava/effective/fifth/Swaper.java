package cn.gyw.corejava.effective.fifth;

import java.util.List;

public class Swaper {

    public static void swap(List<?> list, int i, int j) {
//        list.set(i, list.set(j, list.get(i))); // 编译异常，无法将？ 放入到 ？
        swapHelper(list, i , j);
    }

    // 捕获类型，辅助方法必须是参数类型
    // 知道参数是E，put E 是安全的
    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
