package cn.gyw.spring.db.route;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * 方法一：在应用配置中配置 数据源信息
 * 方法二：在数据库中动态配置数据源表（若配置库挂了，数据源无法获取，考虑zk）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    // 给每个数据源打个标记
    private DynamicDataSourceEntry dynamicDataSourceEntry;

    @Override
    protected Object determineCurrentLookupKey() {
        return this.dynamicDataSourceEntry.get();
    }

    public DynamicDataSourceEntry getDynamicDataSourceEntry() {
        return dynamicDataSourceEntry;
    }

    public void setDynamicDataSourceEntry(DynamicDataSourceEntry dynamicDataSourceEntry) {
        this.dynamicDataSourceEntry = dynamicDataSourceEntry;
    }
}
