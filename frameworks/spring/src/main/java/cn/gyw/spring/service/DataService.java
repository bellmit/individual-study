package cn.gyw.spring.service;

/**
 * 数据操作接口
 * 两个实现类
 *
 * @see  cn.gyw.spring.service.impl.DatabaseService
 * @see cn.gyw.spring.service.impl.CacheService
 */
public interface DataService {

    void showDesc();

    void showIntro();
}
