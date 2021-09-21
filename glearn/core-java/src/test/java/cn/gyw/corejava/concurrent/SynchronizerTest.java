package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.util.Vector;

/**
 * 同步容器
 */
public class SynchronizerTest {

    /**
     * 复合操作导致同步容器异常
     */
    @Test
    public void vectorComplex() {

    }

    public static Object getLast(Vector list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }
    public static void deleteLast(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }
}
