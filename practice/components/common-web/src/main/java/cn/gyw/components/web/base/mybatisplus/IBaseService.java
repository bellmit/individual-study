package cn.gyw.components.web.base.mybatisplus;

import com.baomidou.mybatisplus.extension.service.IService;

public interface IBaseService<T> extends IService<T> {

    /*
    Save
    // 插入一条记录（选择字段，策略插入）
    boolean save(T entity);
    // 插入（批量）
    boolean saveBatch(Collection<T> entityList);
    // 插入（批量）
    boolean saveBatch(Collection<T> entityList, int batchSize);

    SaveOrUpdate
    // TableId 注解存在更新记录，否插入一条记录
    boolean saveOrUpdate(T entity);
    // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
    boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper); // 实体对象封装操作类 UpdateWrapper
    // 批量修改插入
    boolean saveOrUpdateBatch(Collection<T> entityList);
    // 批量修改插入
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    Remove
    // 根据 entity 条件，删除记录
    boolean remove(Wrapper<T> queryWrapper); // 实体包装类 QueryWrapper
    // 根据 ID 删除
    boolean removeById(Serializable id);
    // 根据 columnMap 条件，删除记录
    boolean removeByMap(Map<String, Object> columnMap);
    // 删除（根据ID 批量删除）
    boolean removeByIds(Collection<? extends Serializable> idList);

    Update
    // 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
    boolean update(Wrapper<T> updateWrapper);
    // 根据 whereEntity 条件，更新记录
    boolean update(T entity, Wrapper<T> updateWrapper); // 实体对象封装操作类 UpdateWrapper
    // 根据 ID 选择修改
    boolean updateById(T entity);
    // 根据ID 批量更新
    boolean updateBatchById(Collection<T> entityList);
    // 根据ID 批量更新
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    Get
    // 根据 ID 查询
    T getById(Serializable id);
    // 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
    T getOne(Wrapper<T> queryWrapper);
    // 根据 Wrapper，查询一条记录
    T getOne(Wrapper<T> queryWrapper, boolean throwEx); // throwEx: 有多个 result 是否抛出异常
    // 根据 Wrapper，查询一条记录
    Map<String, Object> getMap(Wrapper<T> queryWrapper);
    // 根据 Wrapper，查询一条记录
    <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper); // mapper 转换函数

    List
    // 查询所有
    List<T> list();
    // 查询列表
    List<T> list(Wrapper<T> queryWrapper);
    // 查询（根据ID 批量查询）
    Collection<T> listByIds(Collection<? extends Serializable> idList);
    // 查询（根据 columnMap 条件）
    Collection<T> listByMap(Map<String, Object> columnMap);
    // 查询所有列表
    List<Map<String, Object>> listMaps();
    // 查询列表
    List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);
    // 查询全部记录
    List<Object> listObjs();
    // 查询全部记录
    <V> List<V> listObjs(Function<? super Object, V> mapper);
    // 根据 Wrapper 条件，查询全部记录
    List<Object> listObjs(Wrapper<T> queryWrapper);
    // 根据 Wrapper 条件，查询全部记录
    <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    Page
    // 无条件翻页查询
    IPage<T> page(IPage<T> page);
    // 翻页查询
    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
    // 无条件翻页查询
    IPage<Map<String, Object>> pageMaps(IPage<T> page);
    // 翻页查询
    IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper); // 实体对象封装操作类 QueryWrapper

    Count
    // 查询总记录数
    int count();
    // 根据 Wrapper 条件，查询总记录数
    int count(Wrapper<T> queryWrapper);

    Chain
    query
    // 链式查询 普通
    QueryChainWrapper<T> query();
    // 链式查询 lambda 式。注意：不支持 Kotlin
    LambdaQueryChainWrapper<T> lambdaQuery();

    // 示例：
    query().eq("column", value).one();
    lambdaQuery().eq(Entity::getId, value).list();

    update
    // 链式更改 普通
    UpdateChainWrapper<T> update();
    // 链式更改 lambda 式。注意：不支持 Kotlin
    LambdaUpdateChainWrapper<T> lambdaUpdate();

    // 示例：
    update().eq("column", value).remove();
    lambdaUpdate().eq(Entity::getId, value).update(entity);


     */
}
