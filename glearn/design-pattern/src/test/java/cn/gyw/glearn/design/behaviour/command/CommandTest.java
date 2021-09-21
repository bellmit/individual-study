package cn.gyw.glearn.design.behaviour.command;

import org.junit.Test;

import java.io.IOException;

/**
 * 命令模式测试
 */
public class CommandTest {

    /**
     * 命令模式
     */
    @Test
    public void commandPattern() {
        Barbecuer boy = new Barbecuer();
        // 开门营业
        Waiter waiter = new Waiter();

        // 命令分配
        BakeMuttonCommand bakeMuttonCommand = new BakeMuttonCommand(boy, 10);
        BakeChickenWingCommand bakeChickenWingCommand = new BakeChickenWingCommand(boy, 3);
        // 第一个：3个鸡翅，10个烤肉
        waiter.setOrder(bakeChickenWingCommand);
        waiter.setOrder(bakeMuttonCommand);
        waiter.notifyWork();
        System.out.println("-----------------------------");
        bakeMuttonCommand = new BakeMuttonCommand(boy, 5);
        bakeChickenWingCommand = new BakeChickenWingCommand(boy, 4);
        // 第二个人：5个鸡翅，4个烤肉
        waiter.setOrder(bakeChickenWingCommand);
        waiter.setOrder(bakeMuttonCommand);
        waiter.notifyWork();
    }

    /**
     * 紧耦合
     * 客户端程序与烤肉者紧耦合，尽管简单，僵化
     */
    @Test
    public void nomal() throws IOException {
        Barbecuer barbecuer = new Barbecuer();
        barbecuer.bakeMutton();
        barbecuer.bakeMutton();
        barbecuer.bakeChickenWing();

        barbecuer.bakeChickenWing();
        barbecuer.bakeChickenWing();
        barbecuer.bakeMutton();
    }

}
