package cn.gyw.gbatis.binding;

import cn.gyw.gbatis.config.Configuration;
import cn.gyw.gbatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口映射注册器
 * <p>
 * Created by guanyw on 2019/3/4.
 */
public class MapperRegistry {

    private final Configuration config;

    // 接口-代理工厂映射
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MapperRegistry(Configuration configuration) {
        this.config = configuration;
    }

    /**
     * 生成 接口 的代理对象
     *
     * @param type
     * @param sqlSession
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory) this.knownMappers.get(type);
        if (mapperProxyFactory == null) {
            System.out.println("Type :" + type + ", not registy");
        } else {
            return mapperProxyFactory.newInstance(sqlSession);
        }
        return null;
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            boolean loadCompleted = false;
            try {
                this.knownMappers.put(type, new MapperProxyFactory(type));
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    this.knownMappers.remove(type);
                }

            }
        }

    }
}
