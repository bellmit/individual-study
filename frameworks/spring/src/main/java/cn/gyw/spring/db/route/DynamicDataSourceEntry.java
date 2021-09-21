package cn.gyw.spring.db.route;

import cn.gyw.spring.db.DataSourceType;

/**
 * 数据源管理器
 */
public final class DynamicDataSourceEntry {

    // 默认数据源
    private DataSourceType DEFAULT = null;

    private final static ThreadLocal<DataSourceType> local = ThreadLocal.withInitial(() -> DataSourceType.MASTER);

    public DataSourceType get() {
        return local.get();
    }

    // 数据源级别切换
    public void set(DataSourceType type) {
        local.set(type);
    }

    public void reset() {
        local.set(DataSourceType.MASTER);
    }
    /**
     * 清空
     */
    public void clear() {
        local.remove();
    }

    // 线程级别切换
    public static void setGlobal(DataSourceType type) {
        local.set(type);
    }
    public static void resetGlobal() {
        local.set(DataSourceType.MASTER);
    }

    public DynamicDataSourceEntry() {
    }
}
