package cn.gyw.corejava.mix;

class Basic2 {
    private String value;

    public void Set(String val) {
        value = val;
    }

    public String get() {
        return value;
    }
}

class Decorator extends Basic2 {
    protected Basic2 basic;
    public Decorator(Basic2 basic) {
        this.basic = basic;
    }
    public void set(String val) {
        basic.Set(val);
    }
    public String get() {
        return basic.get();
    }
}

public class Decoration {

}
