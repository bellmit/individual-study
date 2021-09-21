package cn.gyw.glearn.design.behaviour.mediator;

/**
 * 抽象同事：部门
 * Created by guanyw on 2018/7/10.
 */
public interface Department {
	//做本部门的事
	void selfAction();

	//向中介者 (总经理) 发出申请
	void outAction();
}
