package cn.gyw.glearn.design.behaviour.command;

/**
 * 抽象命令
 */
public abstract class BakeCommand {

    protected Barbecuer barbecuer;

    public BakeCommand(Barbecuer barbecuer) {
        this.barbecuer = barbecuer;
    }

    // 执行命令
    abstract void executeCommand();
}
