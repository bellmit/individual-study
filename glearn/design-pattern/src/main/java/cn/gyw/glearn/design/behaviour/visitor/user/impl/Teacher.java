package cn.gyw.glearn.design.behaviour.visitor.user.impl;

import java.math.BigDecimal;

import cn.gyw.glearn.design.behaviour.visitor.user.AbstractUser;
import cn.gyw.glearn.design.behaviour.visitor.vis.IVisitor;

public class Teacher extends AbstractUser {

	public Teacher(String name, String identify, String clazz) {
		super(name, identify, clazz);
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	// 升本率
	public double entranceRatio() {
		return BigDecimal.valueOf(Math.random() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
