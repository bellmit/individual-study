package cn.gyw.corejava.effective.first;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * noninstantiable class for service registration and access
 * 不可实例化类提供服务注册和访问
 */
public class Services {
    // prevents instantiation
    private Services(){}

    // Maps service names to services
    private static final Map<String, Provider> providers = new ConcurrentHashMap<>();
    public static final String DEFAULT_PROVIDER_NAME = "<def>";

    // Provider registration API：服务提供者注册服务API
    public static void registerDefaultProvider(Provider p) {
        registerDefaultProvider(DEFAULT_PROVIDER_NAME, p);
    }
    public static void registerDefaultProvider(String name, Provider p) {
        providers.put(name, p);
    }

    // Services access API：服务访问API
    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }
    public static Service newInstance(String name) {
        Provider provider = providers.get(name);
        if (provider == null) {
            throw new IllegalArgumentException("No provider registered with name :" + name);
        }
        return provider.newService();
    }
}
