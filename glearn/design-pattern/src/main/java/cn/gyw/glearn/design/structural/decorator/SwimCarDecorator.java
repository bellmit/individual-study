package cn.gyw.glearn.design.structural.decorator;

/**
 * Created by guanyw on 2018/7/10.
 */
public class SwimCarDecorator extends Decorator {
	public SwimCarDecorator(ICar car) {
		super(car);
	}

	@Override
	public void move() {
		super.move();
		System.out.println("增加游水的功能...");
	}
}
