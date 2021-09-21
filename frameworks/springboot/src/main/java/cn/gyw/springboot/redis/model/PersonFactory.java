package cn.gyw.springboot.redis.model;

public class PersonFactory {

	public static IPerson createWorker(String name, int age) {
		return new Worker(name, age);
	}
}
