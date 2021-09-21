package cn.gyw.glearn.design.behaviour.strategy;

import org.junit.Before;
import org.junit.Test;

/**
 * 商场促销打折
 * <p>
 * 场景：
 * 根据客户购买的商品单价和数量，向客户收费
 */
public class MarketPromotionTest {
    // 单价
    private double price;
    // 数量
    private int count;

    private double totalPrice;

    /**
     * 策略模式 + 简单工厂模式
     *
     * 只需要暴露Conetxt 类即可，降低耦合度
     */
    @Test
    public void calcTotalPriceV5() {
        String discountDesc = "打八折";
        CashContext cashContext = new CashContext(discountDesc);

        totalPrice = cashContext.doGetResult(price * count);
        System.out.println(discountDesc + "后总价格：" + totalPrice);
    }

    /**
     * 策略模式
     */
    @Test
    public void calcTotalPriceV4() {
        String discountDesc = "打八折";
        CashContext cashContext = null;
        switch (discountDesc) {
            case "打八折":
                cashContext = new CashContext(new CashRebate(0.8));
                break;
            case "打9折":
                cashContext = new CashContext(new CashRebate(0.9));
                break;
            default:

        }

        totalPrice = cashContext.doGetResult(price * count);
        System.out.println(discountDesc + "后总价格：" + totalPrice);
    }

    /**
     * 工厂模式实现
     * 缺点：每次增加优惠的策略，都要修改收费对象工厂，不符合开闭原则
     * 2) 需要提供两个类
     */
    @Test
    public void calcTotalPriceV3() {
        String discountDesc = "打八折";
        CashSuper cashSuper = CashFactory.createCashAccept(discountDesc);

        totalPrice = cashSuper.acceptCash(price * count);
        System.out.println(discountDesc + "后总价格：" + totalPrice);
    }

    /**
     * 增加打折
     */
    @Test
    public void calcTotalPriceV2() {
        String discountDesc = "打八折";

        switch (discountDesc) {
            case "打八折":
                totalPrice = price * count * 0.8;
                break;
            case "打9折":
                totalPrice = price * count * 0.9;
                break;
            default:

        }
        System.out.println(discountDesc + "后总价格：" + totalPrice);
    }

    /**
     * 计算总额
     */
    @Test
    public void calcTotalPriceV1() {

        totalPrice = price * count;
        System.out.println("总价格：" + totalPrice);
    }

    @Before
    public void before() {
        System.out.println("商品单价：" + (price = 10.5));
        System.out.println("购买商品数量：" + (count = 10));
        System.out.println("==================================");
    }

}
