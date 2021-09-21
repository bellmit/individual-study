package cn.gyw.glearn.design.creational.factory.abastract;

/**
 * 高档汽车生产工厂
 * Created by guanyw on 2018/7/9.
 */
public class LuxuryCarFactory implements CarFactory {

	@Override
	public Engine createEngine() {
		return null;
	}

	@Override
	public Seat createSeat() {
		return null;
	}

	@Override
	public Tyre createTyre() {
		return null;
	}
}
