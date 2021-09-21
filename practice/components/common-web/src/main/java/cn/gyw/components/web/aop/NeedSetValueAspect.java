package cn.gyw.components.web.aop;

import cn.gyw.components.web.utils.SpringContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NeedSetValueAspect {

	private final Logger log = LoggerFactory.getLogger(NeedSetValueAspect.class);
	
	@Autowired
	private SpringContextUtil contextUtil;
	
	@Around(value = "@annotation(cn.gyw.components.web.annotations.NeedSetValue)")
	public Object doSetNeedValueField(ProceedingJoinPoint joinPoint) {
		log.info("aspect by @NeedSetValue annotation");
		Object result = null;
		try {
			result = joinPoint.proceed();
			contextUtil.doSetNeedValueField(result);
		} catch (Throwable e) {
			log.error("error : {}", e);
		}
		return result;
	}
}
