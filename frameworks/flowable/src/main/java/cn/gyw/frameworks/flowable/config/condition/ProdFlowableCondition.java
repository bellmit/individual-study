package cn.gyw.frameworks.flowable.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This Condition can be used in @Configuration classes 
 * to disable flowable database debugging using an internal H2 Database WebServer.
 * 
 * This is the default configuration.
 *
 */
public class ProdFlowableCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return !context.getEnvironment().containsProperty("debug.flowable");
	}

}
