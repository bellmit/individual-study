package cn.gyw.frameworks.flowable.leave.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendEmailDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println(">>>>>>>>>>>>>>>>>>");
		System.out.println(execution);
		System.out.println("send email :" + execution.getVariable("employee"));
		System.out.println("<<<<<<<<<<<<<<<<<<");
	}
}
