package cn.gyw.spring.propvalue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Spring 注解属性注入的测试Bean
 *
 * @date 2021/12/7 17:22
 */
@Component
public class ValueDIBean {

    private String propValue;

    public String getPropValue() {
        return propValue;
    }

    /**
     * setter方法@Value 注入成功
     */
    // @Value("${property.value}")
    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    /**
     * 非setter方法@Value 注入成功
     *
     * 说明：Spring 不是根据setter方法来做的inject
     * 1. 初始化扫描，扫描字段、方法上有没有注解
     * 2. 如果有注解则创建 InjectionMetadata 对象
     * 3. 在属性初始化时，populateBean() 方法执行时，
     * 通过 InstantiationAwareBeanPostProcessor 来实例化，调用InjectionMetadata
     * 的方法执行 method.invoke()
     */
    @Value("${property.value}")
    public void otherMethod(String propValue) {
        this.propValue = propValue;
    }
}
