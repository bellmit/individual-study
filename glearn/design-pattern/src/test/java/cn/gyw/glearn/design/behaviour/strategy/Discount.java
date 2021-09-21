package cn.gyw.glearn.design.behaviour.strategy;

enum Discount {
    NORMAL("正常收费", 1D),

    TWENTY_PERCENT("打八折", 0.8D),
    SEVEN_PERCENT("打七折", 0.7D),
    FIVE_PERCENT("打五折", 0.5D);

    private String discountDesc;
    private double discount;
    Discount(String discountDesc, double discount) {
        this.discountDesc = discountDesc;
        this.discount = discount;
    }

    public static final Discount getDiscount(String discountDesc) {
        for (Discount discount : Discount.values()) {
            if (discountDesc.equals(discount.discountDesc)) {
                return discount;
            }
        }
        return Discount.NORMAL;
    }
}