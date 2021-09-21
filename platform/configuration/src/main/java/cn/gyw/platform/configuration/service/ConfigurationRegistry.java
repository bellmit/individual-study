package cn.gyw.platform.configuration.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.gyw.platform.configuration.interfaces.IConfigurationEntryListener;
import cn.gyw.platform.configuration.interfaces.IConfigurationSectionListener;

/**
 * Configuration 注册器
 */
public abstract class ConfigurationRegistry {

    private static Map<String, Set<IConfigurationEntryListener>> entryListeners = new HashMap<>();
    private static Map<String, Set<IConfigurationSectionListener>> sectionListeners = new HashMap<>();

    private static final String SEPERATOR = ".";
    private static final Lock lock = new ReentrantLock();

    /**
     * 注册 section 域监听器
     * @param section
     * @param listener
     */
    public static void registerSectionListener(String section, IConfigurationSectionListener listener) {
        if (Objects.nonNull(listener)) {
            lock.lock();
            try {
                if (!sectionListeners.containsKey(section)) {
                    sectionListeners.put(section, new HashSet<>());
                }
                sectionListeners.get(section).add(listener);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void registerEntryListener(String section, String parameterKey, IConfigurationEntryListener listener) {
        if (Objects.nonNull(listener)) {
            String keyName = section + SEPERATOR + parameterKey;
            lock.lock();
            try {
                if (!entryListeners.containsKey(section)) {
                    entryListeners.put(keyName, new HashSet<>());
                }
                entryListeners.get(section).add(listener);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void unregisterSectionListener(String section, IConfigurationSectionListener listener) {
        Set<IConfigurationSectionListener> listeners = sectionListeners.get(section);
        if (Objects.nonNull(listeners) && listeners.size() > 0) {
            listeners.remove(listener);
        }
    }

    public static void unregisterEntryListener(String section, String parameterKey, IConfigurationEntryListener listener) {
        String keyName = section + SEPERATOR + parameterKey;
        Set<IConfigurationEntryListener> listeners = entryListeners.get(keyName);
        if (Objects.nonNull(listeners) && listeners.size() < 0) {
            listeners.remove(listener);
        }
    }

    protected static void notifySectionListeners(String section, Method method) {
        sectionListeners.get(section).stream().forEach((listener -> {
            try {
                method.invoke(listener, section);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }));
    }

    protected static void notifyEntryListeners(String section, String parameterKey, Method method) {
        String keyName = section + SEPERATOR + parameterKey;
        entryListeners.get(keyName).forEach(listener -> {
            try {
                method.invoke(listener, section, parameterKey);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
