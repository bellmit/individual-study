package cn.gyw.glearn.design.behaviour.visitor.vis.impl;

import cn.gyw.glearn.design.behaviour.visitor.user.impl.Student;
import cn.gyw.glearn.design.behaviour.visitor.user.impl.Teacher;
import cn.gyw.glearn.design.behaviour.visitor.vis.IVisitor;

public class Principal implements IVisitor {

	@Override
	public void visit(Student student) {
		System.out.println("学生信息，" + student.getName() + ", 班级：" + student.getClazz());
	}

	@Override
	public void visit(Teacher teacher) {
		System.out.println("教师信息，" + teacher.getName() + "， 升学率：" + teacher.entranceRatio());
	}

}
