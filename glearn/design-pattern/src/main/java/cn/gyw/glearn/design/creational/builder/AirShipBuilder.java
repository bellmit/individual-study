package cn.gyw.glearn.design.creational.builder;

/**
 * 构建接口:用来构建AirShipz中组件的接口
 * Created by guanyw on 2018/7/9.
 */
public interface AirShipBuilder {

	Engine builderEngine();//构建发动机
    OrbitalModule builderOrbitalModule();//构建轨道舱
    EscapeTower builderEscapeTower();//构建逃逸塔

}
