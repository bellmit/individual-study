package cn.gyw.glearn.design.creational.builder;

/**
 * 组装接口:用于将飞船组件组装起来
 * Created by guanyw on 2018/7/9.
 */
public interface AirShipDirector {
	 /**
     * 组装飞船
     * @return
     */
    AirShip directorAirShip();
}
