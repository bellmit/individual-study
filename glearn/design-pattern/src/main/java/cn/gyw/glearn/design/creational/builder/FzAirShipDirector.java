package cn.gyw.glearn.design.creational.builder;

/**
 * Created by guanyw on 2018/7/9.
 */
public class FzAirShipDirector implements AirShipDirector {

	private AirShipBuilder builder;//创建构建者的引用

	public FzAirShipDirector(AirShipBuilder airShipBuilder) {
		this.builder = airShipBuilder;
	}

	/**
	 * 组装具体的对象，为了简单，这里的组装步骤比较简单。实际产品中较复杂
	 */
	@Override
	public AirShip directorAirShip() {
		Engine e = builder.builderEngine();//构建发动机
		EscapeTower et = builder.builderEscapeTower();//构建逃逸塔
		OrbitalModule o = builder.builderOrbitalModule();//构建轨道舱
		//装配对象
		AirShip ship = new AirShip();
		ship.setEngine(e);
		ship.setEscapeTower(et);
		ship.setOrbitalModule(o);
		return ship;
	}

}