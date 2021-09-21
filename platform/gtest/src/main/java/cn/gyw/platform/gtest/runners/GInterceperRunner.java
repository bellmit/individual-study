package cn.gyw.platform.gtest.runners;

import cn.gyw.platform.gtest.annotations.GJunit;
import cn.gyw.platform.gtest.intercepters.GIntercepter;
import cn.gyw.platform.gtest.statements.GIntercepterStatement;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.Objects;

/**
 * Junit 执行器类
 */
public class GInterceperRunner extends BlockJUnit4ClassRunner {

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        Statement junit4Statement = super.methodInvoker(method, test);
        GIntercepterStatement myStatement = new GIntercepterStatement(junit4Statement);

        GJunit gjunit = null;
        Class<?> currentClz = test.getClass();
        gjunit = currentClz.getAnnotation(GJunit.class);
        if (Objects.isNull(gjunit)) {
            gjunit = currentClz.getSuperclass().getAnnotation(GJunit.class);
        }

        if (Objects.isNull(gjunit)) {
            return junit4Statement;
        }

        Class<?>[] intercepts = gjunit.values();

        try {
            for (Class<?> one : intercepts) {
                myStatement.addIntercepter((GIntercepter) one.newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return myStatement;
    }

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public GInterceperRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }
}
