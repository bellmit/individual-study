package cn.gyw.corejava.effective.sixth;

import java.util.Set;

/**
 * 位域表示 替代方法 EnumSet
 *
 */
public class Text {

    public static final int STYLE_BOLD = 1 << 0; // 1
    public static final int STYLE_ITALIC = 1 << 1; // 2
    public static final int STYLE_UNDERLINE = 1 << 2; // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8

    // 参数可以使用 OR 位运算将几个常量合并到一个集合中，称为位域
    // 可以有效的执行联合和交集等集合操作
    // new Text().applyStyles(STYLE_BOLD | STYLE_ITALIC);
    public void applyStyles(int styles) {
        System.out.println("result | :" + styles);
    }

    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRICKETHROUGH;
    }

    // 用EnumSet 代替 位域操作
    public void applyStyles(Set<Style> styles) {

    }

}
