package cn.gyw.corejava.effective.fifth;

import java.util.HashSet;
import java.util.Set;

/**
 * 泛型方法
 */
public class Union {

    public <E> Set<E> union(Set<? extends E> s1,
                            Set<? extends E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }
}
