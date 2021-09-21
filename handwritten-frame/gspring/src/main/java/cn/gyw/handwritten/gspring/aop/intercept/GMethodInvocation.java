package cn.gyw.handwritten.gspring.aop.intercept;

import java.lang.reflect.Method;

import cn.gyw.handwritten.gspring.aop.adapter.GJoinPoint;

public interface GMethodInvocation extends GJoinPoint {

    Method getMethod();
}
