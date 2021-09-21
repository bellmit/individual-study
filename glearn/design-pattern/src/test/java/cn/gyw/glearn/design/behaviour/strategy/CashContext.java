package cn.gyw.glearn.design.behaviour.strategy;

/**
 * 策略模式
 */
public class CashContext {

    private CashSuper cashSuper;

    public CashContext(CashSuper cashSuper) {
        this.cashSuper = cashSuper;
    }

    /**
     * 工厂模式
     * @param type
     */
    public CashContext(String type) {
        switch (type) {
            case "打八折":
                cashSuper = new CashRebate(0.8);
                break;
            case "打9折":
                cashSuper = new CashRebate(0.9);
                break;
            default:

        }
    }

    public double doGetResult(double money) {
        return cashSuper.acceptCash(money);
    }

}
