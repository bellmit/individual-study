package cn.gyw.glearn.design.creational.singleton;

import cn.gyw.platform.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS 乐观锁实现
 */
@ThreadSafe
public class LazySingleton5 {

    // 避免ABA 问题
    private static final AtomicStampedReference<LazySingleton5> CAS_INSTANCE =
            new AtomicStampedReference<>(null, 0);

    private static LazySingleton5 instance;

    public static LazySingleton5 getInstance() {
        while (true) { // 可能会一直获取
            instance = CAS_INSTANCE.get(new int[] {1});
            if (instance != null) {
                return instance;
            }
            CAS_INSTANCE.compareAndSet(null, new LazySingleton5(), 0, 1);
            return CAS_INSTANCE.get(new int[] {1});
        }
    }
}
