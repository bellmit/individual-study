package cn.gyw.glearn.design.behaviour.memento;

public class Memento {
	// 自身属性
	private String name;
	private String value;
	private int age;
	private double salary;

	public Memento() {
	}

	public Memento(Originator originator) {
		this.name = originator.getName();
		this.value = originator.getValue();
		this.age = originator.getAge();
		this.salary = originator.getSalary();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}
