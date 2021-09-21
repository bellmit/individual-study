package cn.gyw.glearn.design.structural.decorator;

/**
 * 抽象装饰器类，继承共有接口
 * 对于具体的类来说，并不知道装饰器类的存在
 * Created by guanyw on 2018/7/10.
 */
public abstract class Decorator implements ICar {

	private ICar car;

	public Decorator(ICar car) {
		this.car = car;
	}

	@Override
	public void move() {
		car.move();
	}
}
