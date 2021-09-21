package cn.gyw.glearn.design.creational.factory.abastract;

/**
 * 生产不同产品族的全部产品，不适合生产产品族内的一个配件
 * Created by guanyw on 2018/7/9.
 */
public interface CarFactory {

	Engine createEngine();//创建发动机
    Seat createSeat();//创建座椅
    Tyre createTyre();//创建轮胎

}
