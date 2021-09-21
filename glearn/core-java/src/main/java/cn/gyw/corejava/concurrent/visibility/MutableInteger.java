package cn.gyw.corejava.concurrent.visibility;

/**
 * 非线程安全
 *
 * getter和setter访问value域，却没有同步，会产生过期数据
 * 写线程：调用set方法
 * 读线程：调用get方法，获取不到写线程的最新数据
 */
public class MutableInteger {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
