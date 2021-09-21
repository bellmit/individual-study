package cn.gyw.glearn.design.structural.bridge;

/**
 * 电脑类型维度 抽象接口
 * 在抽象层，实现维度间的关系
 * <p>
 * Created by guanyw on 2018/7/10.
 */
public abstract class ComputerType {

	// 类型维度里持有品牌维度的引用
	private Brand brand;

	public void sale() {
		brand.sale();
	}

	public ComputerType(Brand brand) {
		this.brand = brand;
	}
}

class Desktop extends ComputerType {

	public Desktop(Brand brand) {
		super(brand);
	}

	@Override
	public void sale() {
		super.sale();
		System.out.println("台式机");
	}
}

class Laptop extends ComputerType {

	public Laptop(Brand brand) {
		super(brand);
	}

	@Override
	public void sale() {
		super.sale();
		System.out.println("笔记本");
	}
}
