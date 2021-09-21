package cn.gyw.glearn.design.behaviour.chainofresponsibility;

/**
 * 责任链模式
 * 处理请求的接口
 * 定义链上的下一个继承者，和一个处理请求的抽象方法
 *
 * 应用：以请假流程为例
 */
public interface Handler {

	/*
	责任链上下一个继承者
	 */
	void setNextHandler(Handler handler);

	/*
	处理请求
	 */
	void handle (Request request);

}
