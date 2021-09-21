package cn.gyw.glearn.design.creational.factory.abastract;

/**
 * Created by guanyw on 2018/7/9.
 */
public class LuxuryEngine implements Engine{
    @Override
    public void run() {
        System.out.println("好发动机转的快");
    }
    @Override
    public void start() {
        System.out.println("启动快，自动启停");
    }
}
