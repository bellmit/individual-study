package cn.gyw.components.web.log;

import org.springframework.context.ApplicationEvent;

public class ApiLogEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	/**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ApiLogEvent(Object source) {
        super(source);
    }
}
