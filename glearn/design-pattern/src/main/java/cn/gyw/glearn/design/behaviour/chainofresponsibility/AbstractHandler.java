package cn.gyw.glearn.design.behaviour.chainofresponsibility;

/**
 * 抽象的处理者类
 */
public abstract class AbstractHandler implements Handler {
	// Handler 名称
	protected String name;

	// 下一个 handler
	protected Handler nextHandler;

	public AbstractHandler() {
	}

	public AbstractHandler(String name) {
		this.name = name;
	}

	@Override
	public void setNextHandler(Handler handler) {
		this.nextHandler = handler;
	}

}
