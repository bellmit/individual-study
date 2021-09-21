package cn.gyw.corejava.effective.third;

/**
 * 传递性
 *
 */
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Point)) {
//            return false;
//        }
//        Point tmp = (Point) obj;
//        return (tmp.x == this.x) && (tmp.y == this.y);
//    }

    /**
     * 违反了 里氏替换 原则（一个类型的任何重要属性也将适用于它的子类型）
     *
     * 如果子类继承，那么就会导致子类永远不能参与equals
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Point p = (Point) obj;
        return p.x == this.x && p.y == this.y;
    }
}
