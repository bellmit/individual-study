package cn.gyw.corejava.effective.third;

// adds a value component without violating the equals contract
public class NewColorPoint {

    private Point point;
    private Color color;

    public NewColorPoint(int x, int y, Color color) {
        if (color == null) {
            throw new NullPointerException();
        }
        point = new Point(x, y);
        this.color = color;
    }

    /*
    Returns the point-view of this color point
     */
    public Point asPoint() {
        return point;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NewColorPoint)) {
            return false;
        }
        NewColorPoint colorPoint = (NewColorPoint) obj;
        return colorPoint.asPoint().equals(this.point) && colorPoint.color == this.color;
    }
}
