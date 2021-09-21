package cn.gyw.glearn.design.behaviour.strategy;

/**
 * 普通收费
 */
public class CashNomal extends CashSuper {

    @Override
    public double acceptCash(double money) {
        return money;
    }
}
