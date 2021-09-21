package cn.gyw.handwritten.gspring.aop.adapter;

import java.lang.reflect.Method;

public abstract class GAbstractAspectAdvice {

    private Method aspectMethod;
    private Object aspectTarget;

    public GAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    protected Object invokeAdviceMethod(GJoinPoint joinPoint, Object returnValue, Throwable e) throws Exception {
        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if (null == paramTypes) {
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[paramTypes.length];
            for (int i = 0, len = paramTypes.length; i < len; i++) {
                if (paramTypes[i] == GJoinPoint.class) {
                    args[i] = joinPoint;
                } else if (paramTypes[i] == Throwable.class) {
                    args[i] = e;
                } else if (paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget, args);
        }
    }

}
