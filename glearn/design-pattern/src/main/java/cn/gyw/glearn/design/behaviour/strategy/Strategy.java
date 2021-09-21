package cn.gyw.glearn.design.behaviour.strategy;

/**
 * 算法族的接口
 *
 * 策略模式
 * 定义一系列算法，
 * 算法不影响客户
 * 系统提供不同的算法，新增、删除算法，对算法进行封装。
 * 外部决定使用哪个方法即可
 * @author guanyw
 *
 */
public interface Strategy {

	int calculate(String exp);

}
