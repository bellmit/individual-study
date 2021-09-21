package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class CollectionsTest extends BaseCondition {

    private final static List<Integer> list
            // = new ArrayList<>();
            = new CopyOnWriteArrayList<>();
    private final static Set<Integer> hashSet
            // = new HashSet<>();
            // = new CopyOnWriteArraySet<>();
            = new ConcurrentSkipListSet<>();
    private final static Map<Integer, Integer> map
            = new HashMap<>();

    @Override
    protected void buildTask(Integer index) {
        // list.add(index);
        hashSet.add(index);

    }

    @Override
    protected void printInfo() {
        System.out.println(list.size());
        System.out.println(hashSet.size());
    }

    @Test
    public void testList() {
        this.work();
    }

    @Test
    public void testSet() {
        this.work();
    }
}
