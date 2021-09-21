package cn.gyw.glearn.design.structural.flyweight;

/**
 * 享元的具体实现：ConcreteFlyWeight（具体享元类）：为内部状态提供成员变量进行存储
 * <p>
 * Created by guanyw on 2018/7/11.
 */
public class ConcretedFlyWeight implements ChessFlyWeight {

	//这里就是为内部状态提供成员变量进行存储
	private String color;

	//构造的时候初始化color属性
	public ConcretedFlyWeight(String color) {
		super();
		this.color = color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public void display(Coordinate c) {
		System.out.println("棋子颜色：" + color);
		System.out.println("棋子位置：(" + c.getX() + "," + c.getY() + ")");
	}
}
