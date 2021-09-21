package cn.gyw.corejava.effective.third;

/**
 * 添加颜色
 */
public class ColorPoint extends Point {

    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    // 不添加equals方法，在比较时颜色会被忽略掉，违反传递性


    /**
     * 违反对称性
     *
     * 在比较有色点和普通点时，不对称
     * @param obj
     * @return
     */
    /*@Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColorPoint)) {
            return false;
        }
        return super.equals(obj) && this.color == ((ColorPoint) obj).color;
    }*/

    /**
     * 混合比较，维护了对称性，损失一致性
     * @param obj
     * @return
     */
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Point)) {
//            return false;
//        }
//        // 如果是普通点，使用普通点比较
//        if (!(obj instanceof ColorPoint)) {
//            return obj.equals(this);
//        }
//        // 有色点，使用有色点比较
//        return super.equals(obj) && this.color == ((ColorPoint) obj).color;
//    }



}
