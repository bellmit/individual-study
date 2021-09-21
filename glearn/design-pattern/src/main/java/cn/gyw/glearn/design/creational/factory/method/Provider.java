package cn.gyw.glearn.design.creational.factory.method;

import cn.gyw.glearn.design.creational.entities.Sender;

/**
 * 工厂方法模式
 * 为不同的产品创建一个工厂类
 * 每增加一个新的产品，在原来的基础上，实现工厂接口，不修改原来的代码
 * 避免开闭原则
 */
public interface Provider {

	Sender produce();
}
