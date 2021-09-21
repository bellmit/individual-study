package cn.gyw.corejava.jvm;

import java.util.ArrayList;
import java.util.List;

public class JvmOptionBase {

	// 10M 容量
	private static final int _10M = 10 * 1024 * 1024;

	private static byte[] bytes = new byte[1024];

	public static void main(String[] args) {
		List<byte[]> list = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			// 不断new 是否会发生OOM
			byte[] alloc = new byte[_10M];
			 list.add(alloc);
		}
	}
}
