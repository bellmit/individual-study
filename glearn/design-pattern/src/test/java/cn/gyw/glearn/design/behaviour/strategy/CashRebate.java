package cn.gyw.glearn.design.behaviour.strategy;

/**
 * 打折
 */
public class CashRebate extends CashSuper {

    private double rebate;

    public CashRebate(double rebate) {
        this.rebate = rebate;
    }

    @Override
    public double acceptCash(double money) {
        return money * rebate;
    }
}
