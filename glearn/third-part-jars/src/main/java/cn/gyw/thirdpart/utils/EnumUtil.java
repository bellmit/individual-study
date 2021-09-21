package cn.gyw.thirdpart.utils;

import java.util.Random;

public class EnumUtil {

	private static Random random = new Random(47);

	public static <T extends Enum<T>> T random(Class<T> ec) {
		return random(ec.getEnumConstants());
	}

	public static <T> T random(T[] arr) {
		return arr[random.nextInt(arr.length)];
	}
}
