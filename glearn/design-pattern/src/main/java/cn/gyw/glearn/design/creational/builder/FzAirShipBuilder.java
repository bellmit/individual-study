package cn.gyw.glearn.design.creational.builder;

/**
 * 具体飞船的构造
 * Created by guanyw on 2018/7/9.
 */
public class FzAirShipBuilder implements AirShipBuilder{
    @Override
    public Engine builderEngine() {
        System.out.println("万能牌发动机");
        return new Engine("万能牌发动机");
    }
    @Override
    public OrbitalModule builderOrbitalModule() {
        System.out.println("万能牌轨道舱");
        return new OrbitalModule("万能牌轨道舱");
    }
    @Override
    public EscapeTower builderEscapeTower() {
        System.out.println("万能牌逃逸塔");
        return new EscapeTower("万能牌逃逸塔");
    }
}