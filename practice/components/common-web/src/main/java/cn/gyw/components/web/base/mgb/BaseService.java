package cn.gyw.components.web.base.mgb;

import cn.gyw.components.web.utils.BeanMapUtil;
import cn.gyw.components.web.utils.CommonUtil;
import com.github.pagehelper.PageHelper;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseService<T> implements IBaseService<T> {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    protected String entityClassFullName;
    protected String entityClassSimpleName;
    protected Class<T> entityClass;

    protected BaseDao<T> baseDao;
    private static final String SUFFIX = "Mapper";
    private static final String KEY_KEYWORD = "keyword";

    /**
     * 初始化方法
     */
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() throws IllegalAccessException {
        Type genericInterfaces = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genericInterfaces).getActualTypeArguments();
        entityClass = (Class<T>) params[0];
        entityClassFullName = entityClass.getName();
        entityClassSimpleName = entityClass.getSimpleName();
        StringBuilder serviceBuilder = new StringBuilder();
        serviceBuilder.append(entityClassSimpleName.substring(0, 1).toLowerCase())
                .append(entityClassSimpleName.substring(1)).append(SUFFIX);
        log.info("base service name:{}", serviceBuilder.toString());
        // forceAccess: 访问非public 属性
        baseDao = (BaseDao<T>) FieldUtils.readField(this, serviceBuilder.toString(), true);
    }

    @Override
    public int count(T condition) {
        return baseDao.selectCount(condition);
    }

    @Override
    public List<T> queryAll() {
        return baseDao.selectAll();
    }

    @Override
    public List<T> query(T record) {
        Example example = new Example(entityClass);
        example.createCriteria().andEqualTo(record);
        return Optional.ofNullable(baseDao.selectByExample(example)).orElse(Collections.emptyList());
    }

    @Override
    public List<T> query(T condition, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(condition);
        return baseDao.selectByExample(example);
    }

    @Override
    public List<T> query(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        T condition;
        try {
            condition = BeanMapUtil.mapToBean(params, entityClass);
            log.debug("query condition bean :{}", condition);
            Example example = new Example(entityClass);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo(condition);
            params.entrySet().forEach((entry) -> {
                String key = entry.getKey();
                String val = entry.getValue() == null ? "" : entry.getValue().toString();
                if (StringUtils.isNotBlank(val) && key.startsWith(KEY_KEYWORD)) {
                    String propName = key.replaceFirst("^" + KEY_KEYWORD, "");
                    propName = CommonUtil.toFirstLower(propName);
                    criteria.andLike(propName, "%" + entry.getValue() + "%");
                }
            });
            return baseDao.selectByExample(example);
        } catch (Exception e) {
            log.error("Map to bean error :", e);
        }
        return Collections.emptyList();
    }

    @Override
    public int remove(Object key) {
        return baseDao.deleteByPrimaryKey(key);
    }

    @Override
    public int save(T record) {
        return baseDao.insertSelective(record);
    }

    @Override
    public int update(T record) {
        return baseDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public T selectOne(T record) {
        return baseDao.selectOne(record);
    }
}
