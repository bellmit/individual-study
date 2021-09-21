package cn.gyw.glearn.design.structural.adapter;

/*
 * 接口适配器模式
 */
public class InterfaceAdapter extends AbstractILotImpl {

	@Override
	public void handle() {
		System.out.println("真正需要实现的接口方法handle");
	}
}
