package cn.gyw.glearn.design.behaviour.visitor.user;

import cn.gyw.glearn.design.behaviour.visitor.vis.IVisitor;

/**
 * 用户类和抽象访问方法，再由不同的用户实现：老师和学生
 */
public abstract class AbstractUser {

	// 姓名
	protected String name;
	// 身份；重点班、普通班；特级教师、普通教师
	protected String identify;
	// 班级
	protected String clazz;

	public AbstractUser(String name, String identify, String clazz) {
		super();
		this.name = name;
		this.identify = identify;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public String getIdentify() {
		return identify;
	}

	public String getClazz() {
		return clazz;
	}

	// 核心访问方法，供外部访问使用
	public abstract void accept(IVisitor visitor);

}
