package cn.gyw.corejava.effective.sixth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyRunner {

    private Class<?> targetClass;

    public MyRunner(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public void runTest() {
        Method[] methods = targetClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(MyTest.class)) {
                try {
                    method.invoke(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(exc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void runExceptionTest() {
        Method[] methods = targetClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(ExceptionTest.class)) {
                try {
                    method.invoke(null);
                } catch (Throwable e) {
                    Throwable exc = e.getCause();
                    Class<? extends Exception>[] excTypes = method.getAnnotation(ExceptionTest.class).value();
                    for (Class<? extends Exception> excType : excTypes) {
                        if (excType.isInstance(e)) {
                            System.out.println(e.getClass().getSimpleName());
                        }
                    }
                }
            }
        }
    }

}
