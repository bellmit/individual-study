package cn.gyw.glearn.design.behaviour.command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务员类
 */
public class Waiter {

    private List<BakeCommand> bakeCommands;

    public Waiter() {
        bakeCommands = new ArrayList<>();
    }

    // 设置命令（不论客户想要什么，都是命令，记录订单，然后通知烤肉者）
    public void setOrder(BakeCommand bakeCommand) {
        if (bakeCommand instanceof BakeMuttonCommand) {
            System.out.println("服务员：烤羊肉没有了，请换其他的");
            return ;
        }
        this.bakeCommands.add(bakeCommand);
        System.out.println("增加订单：" + bakeCommand + ", 时间：" + LocalDateTime.now());
    }

    // 取消订单
    public void cancelOrder(BakeCommand bakeCommand) {
        bakeCommands.remove(bakeCommand);
        System.out.println("取消订单：" + bakeCommand + ", 时间：" + LocalDateTime.now());
    }

    // 通知执行
    public void notifyWork() {
        // 遍历所有下单
        bakeCommands.forEach((bakeCommand -> {
            bakeCommand.executeCommand();
        }));
        // 清空所有的命令
        bakeCommands.clear();
    }
}
