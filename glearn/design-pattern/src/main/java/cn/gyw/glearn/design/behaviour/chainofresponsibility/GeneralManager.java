package cn.gyw.glearn.design.behaviour.chainofresponsibility;

/**
 * 总经理对象：处理大于等于10天，小于30天的请假信息
 * Created by guanyw on 2018/7/10.
 */
public class GeneralManager extends AbstractHandler {
	@Override
	public void handle(Request request) {
		if (request.getLeaveDays() <= 30) {
			if (request instanceof LeaveRequest) {
				LeaveRequest lr = (LeaveRequest) request;
				System.out.println("请假人：" + lr.getEmpName() + ",天数：" + lr.getLeaveDays() + ",理由：" + lr.getReason());
				System.out.println("审批人：" + this.name + " 总经理，审批通过！");
			}
		} else {
			// 如果有下一个处理者
			if (this.nextHandler != null) {
				// 由下一个继承者来处理
				this.nextHandler.handle(request);
			} else {
				System.out.println("请假申请，最终不通过！最终审批人：" + this.name + "  总经理");
			}
		}
	}

	public GeneralManager(String name) {
		super(name);
	}
}
