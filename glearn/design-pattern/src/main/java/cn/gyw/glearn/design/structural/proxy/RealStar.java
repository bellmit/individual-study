package cn.gyw.glearn.design.structural.proxy;

/**
 * 真实角色（明星艺人）：
 */
public class RealStar implements Star{
	@Override
	public void confer() {

	}

	@Override
	public void signContract() {

	}

	@Override
	public void bookTicket() {

	}

	@Override
	public void sing() {
		System.out.println("唱歌啦。。。");
	}

	@Override
	public void collectMoney() {

	}
}
