package cn.gyw.glearn.design.structural.adapter;

/**
 * 对象适配器模式
 *
 * 普通的业务类BusinessSource不完全满足客户端需求接口INeed
 * 持有source对象，实现客户端需求接口；
 * @author guanyw
 * @see BusinessSource
 * @see INeed
 */
public class ObjectAdapter implements INeed {

	private BusinessSource source;

	public ObjectAdapter(){}

	public ObjectAdapter(BusinessSource source) {
		this.source = source;
	}

	@Override
	public void handle() {
		source.handle();
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

}
