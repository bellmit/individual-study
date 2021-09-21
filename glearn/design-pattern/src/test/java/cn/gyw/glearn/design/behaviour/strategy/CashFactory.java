package cn.gyw.glearn.design.behaviour.strategy;

/**
 * 简单工厂方法
 *
 * 现金收取对象创建工厂
 */
public class CashFactory {

    public static CashSuper createCashAccept(String type) {
        switch (type) {
            case "正常收费":
                return new CashNomal();
            case "打八折":
                return new CashRebate(0.8D);
            case "打九折":
                return new CashRebate(0.9D);
            case "满100减10":
                return new CashReturn("100", "10");
            default:
                throw new RuntimeException("type is not match");
        }
    }

}
