package cn.gyw.spring.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 循环依赖
 */
@Component
public class ModelA {

    @Autowired
    private ModelB modelB;

    ModelA() {
        System.out.println("model a created");
    }
    
    public ModelB getModelB() {
		return modelB;
	}
}
