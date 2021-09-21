package cn.gyw.corejava.effective.fifth;

import java.util.HashMap;
import java.util.Map;

/**
 * 异构容器
 * 允许客户端从任意数量的其他类中，保存一个“最喜爱” 的实例。
 * class 对象作为参数化键的部分
 */
public class Favorities {

    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavoritite(Class<T> type, T instance) {
        favorites.put(type, type.cast(instance)); // 防止传入 原生类型 Class
    }

    public <T> T getFavorite(Class<T> type) {
        if (favorites.containsKey(type)) {
            return type.cast(favorites.get(type));
        }
        return null;
    }
}
