package cn.gyw.corejava.junit;

import cn.gyw.corejava.junit.annotations.JunitCustom;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.Objects;

public class JunitCustomRunner extends BlockJUnit4ClassRunner {

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        Statement junit4Statement = super.methodInvoker(method, test);
        JunitCustomStatement myStatement = new JunitCustomStatement(junit4Statement);

        JunitCustom junitCustom = null;
        Class<?> currentClz = test.getClass();
        junitCustom = currentClz.getAnnotation(JunitCustom.class);
        if (Objects.isNull(junitCustom)) {
            junitCustom = currentClz.getSuperclass().getAnnotation(JunitCustom.class);
        }

        if (Objects.isNull(junitCustom)) {
            return junit4Statement;
        }

        Class<?>[] intercepts = junitCustom.values();

        try {
            for (Class<?> one : intercepts) {
                myStatement.addIntercepter((JunitIntercepter) one.newInstance());
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
    public JunitCustomRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }
}
