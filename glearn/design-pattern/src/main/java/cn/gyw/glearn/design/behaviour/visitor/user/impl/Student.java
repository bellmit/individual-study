package cn.gyw.glearn.design.behaviour.visitor.user.impl;

import cn.gyw.glearn.design.behaviour.visitor.user.AbstractUser;
import cn.gyw.glearn.design.behaviour.visitor.vis.IVisitor;

public class Student extends AbstractUser {

	public Student(String name, String identify, String clazz) {
		super(name, identify, clazz);
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	public int ranking() {
		return (int) (Math.random() * 100);
	}
}
