package cn.gyw.glearn.design.behaviour.strategy;

/**
 * 满送
 */
public class CashReturn extends CashSuper {

    private double moneyCondition;
    private double moneyReturn;

    public CashReturn(String moneyCondition, String moneyReturn) {
        this.moneyCondition = Double.parseDouble(moneyCondition);
        this.moneyReturn = Double.parseDouble(moneyReturn);
    }

    @Override
    public double acceptCash(double money) {
        if (money < 0D) {
            throw new IllegalArgumentException("money :" + money);
        }

        int cnt = (int) Math.floor(money / moneyCondition);

        return money - cnt * moneyReturn;
    }
}
