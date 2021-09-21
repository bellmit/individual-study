package cn.gyw.springboot.redis.model;

import java.io.Serializable;

class Worker implements IPerson, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int age;
	
	public Worker() {
		super();
	}

	public Worker(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getAge() {
		return age;
	}

}
