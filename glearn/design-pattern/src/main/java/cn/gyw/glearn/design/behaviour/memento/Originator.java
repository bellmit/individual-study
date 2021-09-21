package cn.gyw.glearn.design.behaviour.memento;

/**
 * 发起人：发起人内部有自身的内部状态，
 * 并且发起人可以创建备忘录和从备忘录恢复的功能
 */
public class Originator {
	// 发起人需要备份的自身属性
	private String name;
	private String value;
	private int age;
	private double salary;

	// 备份
	public Memento createMemento() {
		Memento memento = new Memento();
		memento.setName(this.name);
		memento.setValue(this.value);
		memento.setAge(this.age);
		memento.setSalary(this.salary);
		return memento;
	}

	// 还原
	public void restoreMemento(Memento memento) {
		this.name = memento.getName();
		this.value = memento.getValue();
		this.age = memento.getAge();
		this.salary = memento.getSalary();
	}

	public Originator() {
	}

	public Originator(String name, String value, int age, double salary) {
		this.name = name;
		this.value = value;
		this.age = age;
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Originator{" +
				"name='" + name + '\'' +
				", value='" + value + '\'' +
				", age=" + age +
				", salary=" + salary +
				'}';
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
