package cn.gyw.corejava.effective.tenth;

/**
 * 延迟加载
 */
public class LazyLoad {

    /**
     * 静态域的延迟加载
     */
    private static class FieldHolder {
        static final LazyLoad lazyLoad = new LazyLoad();
    }

    static LazyLoad getInstance() {
        return FieldHolder.lazyLoad;
    }

    /**
     * 实例域的延迟加载
     */
    private volatile LazyLoad lazyLoad;
    LazyLoad getLazyLoad() {
        // 局部变量，确保lazyLoad 只在已经被初始化的情况下读取一次
        LazyLoad result = lazyLoad;
        if (result == null) {
            synchronized (this) {
                result = lazyLoad;
                if (result == null) {
                    lazyLoad = result = new LazyLoad();
                }
            }
        }
        return result;
    }
}
