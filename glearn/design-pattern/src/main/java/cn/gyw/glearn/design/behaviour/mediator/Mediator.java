package cn.gyw.glearn.design.behaviour.mediator;

/**
 * 抽象中介者
 * 例子：部门管理，各部门管理
 */
public interface Mediator {
	//将具体的同事类注册到中介者中，让中介者知道所有的同事。以便进行交互
	void register(String dname, Department d);
	//通过部门名称，发出命令
	void command(String dname);
}
