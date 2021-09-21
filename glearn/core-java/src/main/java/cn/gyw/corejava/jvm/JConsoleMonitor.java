package cn.gyw.corejava.jvm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JConsole 监视代码
 *
 * vm:-Xms100m -Xmx100m -XX:+UseSerialGC
 * Created by Administrator on 2018/10/9.
 */
public class JConsoleMonitor {

	/**
	 * 内存占位符对象，一个OOMObject大约占64KB
	 */
	static class OOMObject {
		public byte[] placeholder = new byte[64 * 1024];
	}

	public static void fillHeap(int num) throws InterruptedException {
		List<OOMObject> list = new ArrayList<>();
		for (int i = 0 ; i < num ; i ++) {
			// 稍作延时，令监视曲线的变化更加明显
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		fillHeap(1000);
	}
}
