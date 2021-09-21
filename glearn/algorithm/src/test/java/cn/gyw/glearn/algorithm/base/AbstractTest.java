package cn.gyw.glearn.algorithm.base;

import java.time.format.DateTimeFormatter;

public abstract class AbstractTest {

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	private long startTime;

	public void startTime() {
		startTime = System.currentTimeMillis();
	}

	public void endTime() {
		long elapsedTime = System.currentTimeMillis() - startTime;
		double seconds = (double)elapsedTime / 1_000.0;
		System.out.println(" : " + seconds + " s");
	}
}
