package cn.gyw.corejava.effective.sixth;

/**
 * EnumMap 代替
 *
 * 例子：表示一种烹饪用的香草
 */
public class Herb {

    // 一年生、多年生、两年生
    public enum Type {
        ANNUAL, PERENNIAL, BIENNIAL;
    }

    private String name;
    private Type type;

    public Herb(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
