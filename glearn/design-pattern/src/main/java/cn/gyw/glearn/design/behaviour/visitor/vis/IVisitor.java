package cn.gyw.glearn.design.behaviour.visitor.vis;

import cn.gyw.glearn.design.behaviour.visitor.user.impl.Student;
import cn.gyw.glearn.design.behaviour.visitor.user.impl.Teacher;

/**
 * 访问者接口，用于不同的人员操作：校长和家长
 */
public interface IVisitor {

	// 访问学生信息
	void visit(Student student);

	// 访问老师信息
	void visit(Teacher teacher);
}
