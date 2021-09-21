package cn.gyw.glearn.design.creational.factory.abastract;

/**
 * Created by guanyw on 2018/7/9.
 */
public class LowEngine implements Engine{
    @Override
    public void run() {
        System.out.println("转的慢");
    }
    @Override
    public void start() {
        System.out.println("启动慢");
    }
}