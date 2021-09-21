package cn.gyw.glearn.design.behaviour.chainofresponsibility;

/**
 * 经理对象：处理大于3天，小于等于10天的假期
 * Created by guanyw on 2018/7/10.
 */
public class Manager extends AbstractHandler {

	@Override
	public void handle(Request request) {
		if (request.getLeaveDays() <= 10) {
			if (request instanceof LeaveRequest) {
				LeaveRequest lr = (LeaveRequest) request;
				System.out.println("请假人：" + lr.getEmpName() + ",天数：" + lr.getLeaveDays() + ",理由：" + lr.getReason());
				System.out.println("审批人：" + this.name + " 经理，审批通过！");
			}
		} else {
			// 如果有下一个处理者
			if (this.nextHandler != null) {
				// 由下一个继承者来处理
				this.nextHandler.handle(request);
			}
		}
	}

	public Manager(String name) {
		super(name);
	}
}
