package cn.gyw.spring.aop.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;

/**
 * 测试的代理通知器
 * Created by guanyw on 2018/11/6.
 */
public class TestAdvisor implements Advisor {

	@Override
	public Advice getAdvice() {
		return null;
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}
}
