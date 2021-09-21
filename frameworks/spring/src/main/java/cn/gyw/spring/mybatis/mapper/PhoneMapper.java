package cn.gyw.spring.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Mapper;

import cn.gyw.spring.mybatis.model.Phone;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
// 二级缓存 接口+XML 配置相关
@CacheNamespaceRef(value = PhoneMapper.class)
//@CacheNamespace
public interface PhoneMapper extends BaseMapper<Phone> {

    List<Phone> queryAll();

    List<Map<String, Object>> findByOneToOne();

    /**
     * SQL 查询 排行榜
     * 优点：用户每次查询只需要查该sql就行
     * 缺点：sql执行速度慢
     *
     * 另一种：表新增rank字段，定时任务更新该字段；会有一定时间的延迟，每次更新数据，会给数据库带来压力
     * 而且，计划任务更新数据，用户也在更新数据，会出现脏读的情况
     * @return
     */
    List<Map<String, Object>> rankByCoin();

    Phone selectById(Integer id);
    
    int updateById(Phone phone);
}
