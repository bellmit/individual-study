package cn.gyw.corejava.serializable;

import java.io.Serializable;
import java.util.Date;

/**
 * 序列化原始对象
 */
public class SourceObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int age;
	private Date birthday;
	private transient String gender;

	public SourceObject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "SourceObject [name=" + name + ", age=" + age + ", birthday=" + birthday + ", gender=" + gender + "]";
	}

}
