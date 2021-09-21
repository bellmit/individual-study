package cn.gyw.platform.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class PrintUtil {

	private boolean isInstance = true;

	public static void print(String... args) {
		StringBuilder builder = new StringBuilder();
		builder.append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now())).append(" [")
				.append(Thread.currentThread().getName()).append("] ");
		boolean isFirst = true;
		for (String arg : args) {
			if (isFirst) {
				builder.append(arg);
				isFirst = false;
				continue;
			}
			builder.append(" >> ").append(arg);
		}
		System.out.println(builder.toString());
	}

	private PrintUtil() {
		if (isInstance) {
			throw new RuntimeException("Do not instance");
		}
	}
}
