package cn.gyw.glearn.design.behaviour.command;

/**
 * 烤鸡翅命令
 */
public class BakeChickenWingCommand extends BakeCommand {

    // 串数
    private int num;

    public BakeChickenWingCommand(Barbecuer barbecuer, int num) {
        super(barbecuer);
        this.num = num;
    }

    @Override
    void executeCommand() {
        for (int i = 0 ; i < num ; i ++) {
            barbecuer.bakeChickenWing();
        }
    }
}
