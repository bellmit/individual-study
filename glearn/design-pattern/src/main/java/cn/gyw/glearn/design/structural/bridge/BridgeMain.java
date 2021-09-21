package cn.gyw.glearn.design.structural.bridge;

/**
 * Created by guanyw on 2018/7/10.
 */
public class BridgeMain {
	public static void main(String[] args) {
		ComputerType c = new Desktop(new Lenovo());
		c.sale();
	}
}
