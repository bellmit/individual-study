package cn.gyw.corejava.jvm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JVM java virtual machine 参数测试
 * <p>
 * -Xms10m -Xmx10m 指定堆内存最小值、最大值
 * -Xss10m -Xmn10m 指定栈内存最小值、最大值
 */
public class JvmMain {

    // 10M 容量
    private static final int _10M = 10 * 1024 * 1024;

    private static byte[] bytes = new byte[1024];

    public static void main(String[] args) {

        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            // 不断new 是否会发生OOM
            byte[] alloc = new byte[_10M];
            // list.add(alloc);
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


// -XX:+PrintCommandLineFlags -Xms100m -Xmx100m -XX:+UseConcMarkSweepGC
// -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
// -XX:+PrintGCDetails
/*
[GC (Allocation Failure) [ParNew
Desired survivor size 1736704 bytes, new threshold 1 (max 1)
: 21675K->0K(30720K), 0.0087520 secs] 42157K->41584K(99008K), 0.0087840 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

// 初始标记
[GC (CMS Initial Mark) [1 CMS-initial-mark: 41584K(68288K)] 51824K(99008K), 0.0001425 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
并发标记
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
并发预清理
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
可停止的并发预处理
[CMS-concurrent-abortable-preclean-start]
[GC (Allocation Failure) [ParNew
Desired survivor size 1736704 bytes, new threshold 1 (max 1)
- age   1:      20736 bytes,      20736 total
: 21196K->70K(30720K), 0.0113873 secs] 62780K->62134K(99008K), 0.0114137 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[CMS-concurrent-abortable-preclean: 0.000/0.012 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
重新标记
[GC (CMS Final Remark) [YG occupancy: 21200 K (30720 K)][Rescan (parallel) , 0.0034097 secs][weak refs processing, 0.0000107 secs][class unloading, 0.0002597 secs][scrub symbol table, 0.0004974 secs][scrub string table, 0.0001212 secs][1 CMS-remark: 62064K(68288K)] 83265K(99008K), 0.0043839 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
并发清理
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
并发重置
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

 */
