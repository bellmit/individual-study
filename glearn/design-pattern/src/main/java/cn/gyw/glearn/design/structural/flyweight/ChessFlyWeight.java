package cn.gyw.glearn.design.structural.flyweight;

/**
 * 抽象享元类
 * 例子：围棋
 * 把颜色、形状、大小给共享出来（内部状态）。位置不共享（外部状态）
 *
 * FlyWeight（抽象享元类）：通常是一个接口或者抽象类，声明公共方法，
 * 这些方法可以向外界提供对象的内部状态，设置外部状态。
 * Created by guanyw on 2018/7/11.
 */
public interface ChessFlyWeight {

	void setColor(String color);
	String getColor();

	// 显示棋子
	void display(Coordinate c);
}
