package cn.gyw.corejava.proxy.jdk;

/**
 * 程序员类
 *
 * Operate 行为接口的实现类
 *
 * Created by guanyw on 2019/3/4.
 */
public class Programmer implements Operation {

    @Override
    public String description() {
        return "程序员";
    }

    @Override
    public void execute() {
        System.out.println("hint computer to handle problems");
    }

}
