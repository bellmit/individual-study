package cn.gyw.spring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import cn.gyw.spring.propvalue.PropertyValueConfig;
import cn.gyw.spring.model.UserSpring;

/**
 * Bean 属性赋值 测试
 */
public class BeanPropertyValueTest extends AbstractTest {

    public BeanPropertyValueTest() {
        super(PropertyValueConfig.class);
    }

    @Test
    public void testInitMethod() {
        UserSpring user = apc.getBean(UserSpring.class);
        System.out.println("user >>" + user);

        Environment environment = apc.getEnvironment();
        System.out.println(">>" + environment.getProperty("person.name"));

        ((AnnotationConfigApplicationContext) apc).close();
    }
}
