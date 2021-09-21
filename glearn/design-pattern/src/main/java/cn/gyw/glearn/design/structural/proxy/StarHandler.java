package cn.gyw.glearn.design.structural.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理处理器
 * Created by guanyw on 2018/7/11.
 */
public class StarHandler implements InvocationHandler{

	private RealStar realStar;

	public StarHandler(RealStar realStar) {
		this.realStar = realStar;
	}

	public StarHandler() {
	}

	/**
     * 所有的流程控制都在invoke方法中
     * proxy：代理类
     * method：正在调用的方法
     * args：方法的参数
     */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object object = null;
		System.out.println("真实角色方法调用前处理...");
		if (method.getName().equalsIgnoreCase("sing")) {
			object = method.invoke(realStar, args);
		}
		System.out.println("真实角色调用之后的处理.....");
		return object;
	}
}
