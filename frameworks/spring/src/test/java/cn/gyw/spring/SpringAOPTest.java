package cn.gyw.spring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.gyw.spring.aop.AopConfig;
import cn.gyw.spring.service.MathCalculator;

/**
 * Bean 生命周期测试
 */
public class SpringAOPTest extends AbstractTest {

    public SpringAOPTest() {
        super(AopConfig.class);
    }

    @Test
    public void testInitMethod() {
        MathCalculator mathCalculator = apc.getBean(MathCalculator.class);

        mathCalculator.div(1, 1);

        ((AnnotationConfigApplicationContext) apc).close();
    }
}
