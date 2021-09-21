package cn.gyw.glearn.design.behaviour.command;

/**
 * 烤羊肉串命令
 */
public class BakeMuttonCommand extends BakeCommand {

    // 库存
    private int repository = 10;

    // 串数
    private int num;

    public BakeMuttonCommand(Barbecuer barbecuer, int num) {
        super(barbecuer);
        this.num = num;
    }

    @Override
    void executeCommand() {
        for (int i = 0 ; i < num ; i ++) {
            barbecuer.bakeMutton();
        }
    }
}
