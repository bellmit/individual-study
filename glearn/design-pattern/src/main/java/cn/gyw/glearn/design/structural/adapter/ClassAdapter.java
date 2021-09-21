package cn.gyw.glearn.design.structural.adapter;

/**
 * 类适配器模式
 *
 * 普通的业务类BusinessSource不完全满足客户端需求接口INeed
 * 继承source类，实现需求接口；
 * @author guanyw
 * @see BusinessSource
 * @see INeed
 */
public class ClassAdapter extends BusinessSource implements INeed {

	@Override
	public void print() {
		System.out.println("这是类适配器");
	}

}
