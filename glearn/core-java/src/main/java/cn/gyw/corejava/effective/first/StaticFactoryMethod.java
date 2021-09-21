package cn.gyw.corejava.effective.first;

/**
 * 第一条：考虑用静态工厂方法代替构造器
 * 【优势】
 * 1.有名称，为不同类型的对象提供不同的静态方法，而通过构造函数，如果没有正确描述返回对象的类型，不利于代码阅读；
 */
public class StaticFactoryMethod {

    /**
     * 返回类的实例的静态方法
     * @return
     */
    public static StaticFactoryMethod of() {
        return new StaticFactoryMethod();
    }

    /**
     * 优势一
     * 例：生成是素数的对象
     * @return
     */
    public static StaticFactoryMethod probablePrime() {
        return new StaticFactoryMethod();
    }

    /**
     * 优势三：返回子类对象，灵活性高
     * @return
     */
    public static StaticFactoryMethod subString() {
        return new StringStaticFactoryMethod();
    }

    /**
     * 惯用名称
     * valueOf
     * of
     * getInstance（根据参数返回不同实例，单例返回一致）
     * newInstance（返回的实例都不相同）
     * get{Type}（处于不同类中使用）
     * new{Type}（工厂方法处于不同类中使用）
     */

    public void info() {
        System.out.println("this is parent");
    }

    /**
     * String 静态工厂类
     */
    private static class StringStaticFactoryMethod extends StaticFactoryMethod {

        @Override
        public void info() {
            System.out.println("this is sub");
        }
    }
}
