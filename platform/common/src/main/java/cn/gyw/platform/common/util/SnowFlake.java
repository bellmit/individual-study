package cn.gyw.platform.common.util;

/**
 * 全局唯一ID 雪花算法
 *
 * 1bit(标记，未使用) + 41bit(yyyyMMddhhmmssSSS) + 10bit(5bit数据中心 + 5bit机器标识) +
 * 12bit(序列号，每一毫秒支持2^12 -1 的并发)
 *
 * ps:10bit 亦可以完全是机器标识
 * 
 * 存在问题：
 *  若服务器回钟，会造成ID 的重复，分布式服务经常会有10ms 的回钟
 */
public final class SnowFlake {

    // 起始时间戳
    private final static long START_STMP = 1480166465631L;

    // 12bit 序列号
    private final static long SEQUENCE_BIT = 12L;
    // 5bit 数据中心
    private final static long DATACENTER_BIT = 5L;
    // 5bit 机器标识
    private final static long MACHINE_BIT = 5L;

    // 每一部分的最大值
    private final static long MAX_SEQUENCE_NUM = ~(-1 << SEQUENCE_BIT);
    private final static long MAX_DATACENTER_NUM = ~(-1 << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1 << MACHINE_BIT);

    // 每一部分左移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = MACHINE_LEFT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    // 数据中心标识
    private long datacenterId;
    // 机器标识
    private long machineId;
    // 序列号
    private long sequence;
    // 上一次时间戳
    private long lastStmp = -1L;

    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId < 0 || datacenterId > MAX_DATACENTER_NUM) {
            throw new IllegalArgumentException("datacenterid error");
        }
        if (machineId < 0 || machineId > MAX_MACHINE_NUM) {
            throw new IllegalArgumentException("datacenterid error");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public long nextId() {
        long curStmp = currentTimeStamp();
        if (curStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (curStmp == lastStmp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            // 同一毫秒，序列数已至最大
            if (sequence == 0L) {
                curStmp = nextMill();
            }
        } else {
            // 不同毫秒内，序列号为0
            sequence = 0L;
        }

        lastStmp = curStmp;

        return (curStmp - START_STMP) << TIMESTAMP_LEFT // 时间戳部分
                | datacenterId << DATACENTER_LEFT // 数据中心部分
                | machineId << MACHINE_LEFT // 机器标识部分
                | sequence; // 序列号部分
    }

    /**
     * 获取当前毫秒值
     *
     * @return
     */
    private long currentTimeStamp() {
        // 可以使用jdk8
        return System.currentTimeMillis();
    }

    /**
     * 下一毫秒
     *
     * @return
     */
    private long nextMill() {
        long mill = currentTimeStamp();
        while (mill <= lastStmp) {
            mill = nextMill();
        }
        return mill;
    }

}
