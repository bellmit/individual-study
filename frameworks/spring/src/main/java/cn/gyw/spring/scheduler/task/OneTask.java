package cn.gyw.spring.scheduler.task;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guanyw on 2018/7/27.
 */
@Component
public class OneTask {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void execute() {
        System.out.println("1当前Task-One-开始时间：" + sdf.format(new Date()));
        try {
            System.out.println("1当前Task-One 休眠：" + 8 + "秒");
            Thread.sleep(8 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1当前Task-One-结束时间：" + sdf.format(new Date()));
    }
}
