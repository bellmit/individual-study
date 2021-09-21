package cn.gyw.corejava.internalclz;

/**
 * 静态成员类
 */
public class StaticClazz {

    private String nomalStr = "sky";
    private static String outerStr = "fly";

    public void printOuterStr() {
        CustomClz customClz = new CustomClz();
        customClz.printOuterStr();
        customClz.printOuterStr1();
        System.out.println(customClz.name);
    }

    protected static class CustomClz {

        private String name = "CustomClz";

        public void printOuterStr() {
            System.out.println(outerStr);
            System.out.println(new StaticClazz().nomalStr);
        }

        private void printOuterStr1() {
            System.out.println(outerStr);
        }

    }
}
