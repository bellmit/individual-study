package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化线程安全性
 */
public class TimeFormatTest extends BaseCondition {

    // 全局时间格式化
    private final static SimpleDateFormat LSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void buildTask(Integer index) {
        System.out.println(LSDF.format(new Date()));
    }

    @Override
    protected void printInfo() {

    }

    @Test
    public void test() throws InterruptedException {
        this.work();
    }

}
