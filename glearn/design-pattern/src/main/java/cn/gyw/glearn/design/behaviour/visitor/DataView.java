package cn.gyw.glearn.design.behaviour.visitor;

import java.util.ArrayList;
import java.util.List;

import cn.gyw.glearn.design.behaviour.visitor.user.AbstractUser;
import cn.gyw.glearn.design.behaviour.visitor.user.impl.Student;
import cn.gyw.glearn.design.behaviour.visitor.user.impl.Teacher;
import cn.gyw.glearn.design.behaviour.visitor.vis.IVisitor;

/**
 * 数据看板建设，不同访问者返回不同的结果
 */
public class DataView {

	List<AbstractUser> userList = new ArrayList<>();
	
	public DataView() {
		userList.add(new Student("张三", "重点班", "101"));
		userList.add(new Student("李四", "重点班", "101"));
		userList.add(new Student("王五", "重点班", "101"));
		
		userList.add(new Teacher("小三", "普通教师", "101"));
		userList.add(new Teacher("小四", "特级教师", "101"));
		userList.add(new Teacher("小五", "重点班", "101"));
	}
	
	public void show(IVisitor visitor) {
		for (AbstractUser user : userList) {
			user.accept(visitor);
		}
	}
}
