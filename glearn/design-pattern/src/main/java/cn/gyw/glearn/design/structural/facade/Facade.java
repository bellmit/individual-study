package cn.gyw.glearn.design.structural.facade;

/**
 * 外观模式
 * Created by guanyw on 2018/7/11.
 */
public class Facade {

	//所有子系统的引用
	private SubSystemClass1 s1 = null;
	private SubSystemClass2 s2 = null;
	private SubSystemClass3 s3 = null;

	//构造子系统的引用
	public Facade() {
		this.s1 = new SubSystemClass1();
		this.s2 = new SubSystemClass2();
		this.s3 = new SubSystemClass3();
	}

	/**
	 * 具体的外观方法
	 */
	public void facade() {
		s1.methodOne();
		s2.methodTwo();
		s3.methodThree();
	}

}
