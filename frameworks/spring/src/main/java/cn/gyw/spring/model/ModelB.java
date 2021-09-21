package cn.gyw.spring.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 循环依赖
 */
@Component
public class ModelB {

    @Autowired
    private ModelA modelA;

    ModelB() {
        System.out.println("model b created");
    }
    
    public ModelA getModelA() {
		return modelA;
	}
}
