package cn.gyw.corejava.collections;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class CollectionsTest {
	
	@Test
	public void list() {
		ArrayList<Object> list1 = new ArrayList<>();
		list1.add(null);
	}

	/**
	 * HashMap 寻找数组下标的算法
	 */
	@Test
	public void hashIndex() {
		String[] arr = new String[8];
		int hashcode = 10000;
		for (int i = 0; i < 1000; i++) {
			System.out.println("bucket index :" + String.valueOf((hashcode + i) & (arr.length - 1)));
		}
	}
	
	@Test
	public void hashSetQueryByHashCode() {
		Stream<String> generator = Stream.iterate("1", i -> i + "2");

		Set<String> set = generator.limit(100).collect(Collectors.toSet());

		set.contains("12");
	}
}
