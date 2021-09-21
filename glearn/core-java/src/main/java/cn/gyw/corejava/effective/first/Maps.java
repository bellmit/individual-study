package cn.gyw.corejava.effective.first;

import java.util.HashMap;
import java.util.Map;

/**
 * 优势四：类型推导
 */
public class Maps {

    /**
     * 通过菱形法则可以解决这个缺点
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    private Maps() {}
}
