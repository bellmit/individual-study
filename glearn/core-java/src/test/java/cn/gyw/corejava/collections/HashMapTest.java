package cn.gyw.corejava.collections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HashMapTest {

	/**
	 * 扩容机制
	 * 
	 * @throws Exception
	 * @throws NoSuchFieldException
	 */
	@Test
	public void expansionMechanism() throws Exception {
		Map<String, String> map = new HashMap<>();
		for (int i = 1; i < 13; i++) {
			map.put(String.valueOf(i), "AAA");
		}
		printInnerFields(map);
		System.out.println("--------------------------------");
		map.put("13", "AAA");
		printInnerFields(map);
	}

	private void printInnerFields(Map<String, String> map) throws Exception {
		Class<?> mapClaz = map.getClass();

		Method capacity = mapClaz.getDeclaredMethod("capacity");
		capacity.setAccessible(true);
		System.out.println("capacity :" + capacity.invoke(map));

		Field size = mapClaz.getDeclaredField("size");
		size.setAccessible(true);
		System.out.println("size :" + size.get(map));

		Field loadFactor = mapClaz.getDeclaredField("loadFactor");
		loadFactor.setAccessible(true);
		System.out.println("loadFactor :" + loadFactor.get(map));

		Field threshold = mapClaz.getDeclaredField("threshold");
		threshold.setAccessible(true);
		System.out.println("threshold :" + threshold.get(map));
	}
}
