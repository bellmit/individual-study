package cn.gyw.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import cn.gyw.spring.config.MainConfigOfCreateBean;
import cn.gyw.spring.model.Red;

/**
 * 基于注解的配置
 */
public class BeanCreateTest extends AbstractTest {

    public BeanCreateTest() {
        super(MainConfigOfCreateBean.class);
    }

    @Test
    public void testImport() {

        Red red = apc.getBean(Red.class);
        System.out.println(red);

        //工厂Bean获取的是调用getObject创建的对象
        Object bean2 = apc.getBean("redFactoryBean");
        Object bean3 = apc.getBean("redFactoryBean");
        System.out.println("bean type :" + bean2.getClass());
        System.out.println(bean2 == bean3);

        Object bean4 = apc.getBean("&redFactoryBean");
        System.out.println(bean4.getClass());
    }

    @Test
    public void shouldLoadByConfig() {

        Environment environment = apc.getEnvironment();
        printBeansName(apc);

//        String[] namesForType = apc.getBeanNamesForType(Person.class);
//        for (String name : namesForType) {
//            System.out.println("1>" + name);
//        }
//        apc.getBean("commonPerson");
    }

    private void printBeansName(ApplicationContext apc) {
        String[] beansName = apc.getBeanDefinitionNames();
        for (int i = 0, len = beansName.length ; i < len ; i ++) {
            System.out.println(beansName[i]);
        }
    }

}
