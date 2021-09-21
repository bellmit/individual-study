package cn.gyw.frameworks.flowable.leave.event;

import org.flowable.engine.common.api.delegate.event.FlowableEvent;
import org.flowable.engine.common.api.delegate.event.FlowableEventListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 事件监听器
 */
public class MyEventListener implements FlowableEventListener, JavaDelegate {

	@Override
	public void onEvent(FlowableEvent event) {
		System.out.println(">>>>> Event received: " + event.getType());
	}

	@Override
	public boolean isFailOnException() {
		// The logic in the onEvent method of this listener is not critical, exceptions
		// can be ignored if logging fails...
		return false;
	}

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println(". MyEventListener");
	}

}
