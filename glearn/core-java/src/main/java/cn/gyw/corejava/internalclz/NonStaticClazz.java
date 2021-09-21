package cn.gyw.corejava.internalclz;

import java.util.List;

/**
 * 非静态内部类
 */
public class NonStaticClazz {

    private String name = "outter";
    private static String age = "age";

    public void show() {
        System.out.println("show...");
        CustomClz customClz = new CustomClz();
        System.out.println(customClz);
    }

    class CustomClz {

        private List<String> values;

        private String name = "inner";

        public void printInner() {
            System.out.println("NonStaticClazz.CustomClz printInner()");
            // 访问外部类的字段
            System.out.println(NonStaticClazz.this.name);
            System.out.println(NonStaticClazz.this.age);
        }

    }
}
