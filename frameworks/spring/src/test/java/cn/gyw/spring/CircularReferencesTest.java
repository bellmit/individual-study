package cn.gyw.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.gyw.spring.config.ModelConfig;
import cn.gyw.spring.model.ModelA;

/**
 * Spring 如何解决循环依赖
 * 
 */
public class CircularReferencesTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext apc = new AnnotationConfigApplicationContext();
		apc.register(ModelConfig.class);
        // 循环依赖设置
        apc.setAllowCircularReferences(true);
        apc.refresh();
        
        ModelA modelA = apc.getBean(ModelA.class);
        System.out.println("result :" + modelA);
        
        apc.close();
	}
}
