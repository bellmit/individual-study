package cn.gyw.glearn.design.behaviour;

import org.junit.jupiter.api.Test;

import cn.gyw.glearn.design.behaviour.chainofresponsibility.Director;
import cn.gyw.glearn.design.behaviour.chainofresponsibility.GeneralManager;
import cn.gyw.glearn.design.behaviour.chainofresponsibility.LeaveRequest;
import cn.gyw.glearn.design.behaviour.chainofresponsibility.Manager;
import cn.gyw.glearn.design.behaviour.chainofresponsibility.Request;
import cn.gyw.glearn.design.behaviour.visitor.DataView;
import cn.gyw.glearn.design.behaviour.visitor.vis.impl.Parent;
import cn.gyw.glearn.design.behaviour.visitor.vis.impl.Principal;

/**
 * 行为型模式测试
 */
public class BehaviourTest {

	/**
	 * 责任链模式
	 */
	@Test
	public void responsibility() {
		Director director = new Director("主任");
		GeneralManager generalManager = new GeneralManager("总经理对象");
		Manager manager = new Manager("经理");
		director.setNextHandler(manager);
		manager.setNextHandler(generalManager);

		Request[] requests = new Request[5];
		requests[0] = new LeaveRequest("小王1", 3, "回家1");
		requests[1] = new LeaveRequest("小王2", 13, "回家2");
		requests[2] = new LeaveRequest("小王3", 23, "回家3");
		requests[3] = new LeaveRequest("小王4", 33, "回家4");
		requests[4] = new LeaveRequest("小王5", 43, "回家5");

		for (int i = 0; i < requests.length; i++) {
			director.handle(requests[i]);
		}
	}

	/**
	 * 命令模式
	 */
	@Test
	public void commandTest() {
	}

	/**
	 * 访问者模式
	 */
	@Test
	public void visitorTest() {
		DataView dataView = new DataView();
		System.out.println("家长视角访问：");
		dataView.show(new Parent());
		
		System.out.println("校长视角访问：");
		dataView.show(new Principal());
	}
}
