package cn.gyw.glearn.design.behaviour.mediator;

import java.util.HashMap;
import java.util.Map;

/**
 * 具体中介者：总经理
 * Created by guanyw on 2018/7/10.
 */
public class Manager implements Mediator {

	// 中介者需要知道所有的具体同时类
	private Map<String, Department> departments = new HashMap<>();

	// 将部门注册到总经理的部门类容器中
	@Override
	public void register(String dname, Department d) {
		departments.put(dname, d);
	}

	// 根据部门名向具体部门下达命令
	@Override
	public void command(String dname) {
		departments.get(dname).selfAction();
	}
}
