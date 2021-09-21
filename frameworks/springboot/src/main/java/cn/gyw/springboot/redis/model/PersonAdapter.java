package cn.gyw.springboot.redis.model;

import java.io.Serializable;

public class PersonAdapter implements IPerson, Serializable {

	private static final long serialVersionUID = 1L;
	
	private IPerson person;
	
	public PersonAdapter() {
		super();
	}

	public PersonAdapter(IPerson person) {
		super();
		this.person = person;
	}

	@Override
	public String getName() {
		return person.getName();
	}

	@Override
	public int getAge() {
		return person.getAge();
	}

}
