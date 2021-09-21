package cn.gyw.corejava.concurrent;

import java.io.IOException;

/**
 * 维护线程限制
 *
 * get 访问器 总是返回由当前执行线程通过set设置的最新值
 */
public class ThreadLocalDemo {

    private static ThreadLocal<String> strLocal = new ThreadLocal<>();
    
    public static void main(String[] args) throws IOException {

    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				strLocal.set("12344");
				
				strLocal.set("1233444");
			}
		}).start();
    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				strLocal.set("234");
				
				strLocal.set("234");
			}
		}).start();
    	
    	System.in.read();
	}
}
