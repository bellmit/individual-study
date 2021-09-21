package cn.gyw.thirdpart.utils;

/**
 * 输出工具类
 * Created by guanyw on 2019/1/9.
 */
public final class PrintUtil {

    /**
     * 数组打印
     * @param array 指定数组
     */
    public static void printArray(Object[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0 ; i < array.length ; i++) {
            builder.append(array[i]).append(",");
        }
        builder.append("]");
        System.out.println(builder.toString());
    }

    /**
     * 打印携带线程信息
     *
     * @param obj
     */
    public static void printWithThread(Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append(Thread.currentThread().getName())
                .append(":")
                .append(obj);
        System.out.println(builder.toString());
    }

    private PrintUtil() {

    }
}
