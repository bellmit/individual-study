package cn.gyw.glearn.design.creational.builder;

/**
 * 构造者模式
 * 将构建零件(Builder)和组装零件(director)两个操作分开
 * 与抽象工厂方法区别：有个指导者，由指导者管理构建者，组装哪些零件
 * 类似于StringBuilder
 * <p>
 *     定义 builder
 * Created by guanyw on 2018/7/9.
 */
public class AirShip {
	private OrbitalModule orbitalModule;//轨道舱
	private Engine engine;//发动机
	private EscapeTower escapeTower;//逃逸塔

	public OrbitalModule getOrbitalModule() {
		return orbitalModule;
	}

	public void setOrbitalModule(OrbitalModule orbitalModule) {
		this.orbitalModule = orbitalModule;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public EscapeTower getEscapeTower() {
		return escapeTower;
	}

	public void setEscapeTower(EscapeTower escapeTower) {
		this.escapeTower = escapeTower;
	}
}

//轨道舱
class OrbitalModule {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrbitalModule(String name) {
		this.name = name;
	}

	public OrbitalModule() {
	}
}

//发动机
class Engine {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Engine(String name) {
		this.name = name;
	}

	public Engine() {
	}
}

//逃逸塔
class EscapeTower {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EscapeTower(String name) {
		this.name = name;
	}

	public EscapeTower() {
	}
}