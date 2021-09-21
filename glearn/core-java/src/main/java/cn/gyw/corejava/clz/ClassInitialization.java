package cn.gyw.corejava.clz;

import java.util.Random;

public class ClassInitialization {
    public static Random rand = new Random(47);
    public static void main(String[] args) throws Exception {
        Class initable = Initable.class;
        System.out.println("Afetr creating Initable ref .");
        System.out.println(Initable.staticFinal);

        System.out.println(Initable.staticFinal2);

        System.out.println(Initable2.staticFinal);

        Class initable3 = Class.forName("com.clz.Initable3");
        System.out.println("Afetr creating Initable3 ref .");
        System.out.println(Initable3.staticFinal);
    }
}

class Initable{
    static final int staticFinal = 47;
    static final int staticFinal2 =
            ClassInitialization.rand.nextInt(1000);
    static {
        System.out.println("Initializing Intitable");
    }
}

class Initable2{
    static int staticFinal = 147;
    static {
        System.out.println("Initializing Intitable2");
    }
}

class Initable3{
    static int staticFinal = 147;
    static {
        System.out.println("Initializing Intitable3");
    }
}
