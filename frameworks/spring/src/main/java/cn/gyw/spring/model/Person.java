package cn.gyw.spring.model;

public class Person {

	private Long idcard;
	private String name;
	private Integer age;

	public Person(Long idcard, String name, Integer age) {
		super();
		this.idcard = idcard;
		this.name = name;
		this.age = age;
	}

	public Person() {
		super();
	}

	public Long getIdcard() {
		return idcard;
	}

	public void setIdcard(Long idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [idcard=" + idcard + ", name=" + name + ", age=" + age + "]";
	}

}
