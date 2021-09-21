package cn.gyw.corejava.clz.factory;

public class FamilyVsExactType {
    public static void main(String[] args) {
        test(new Base());
        System.out.println("----------------------------------------");
        test(new Derived());
    }

    static void test(Object x){
        System.out.println("Testing x of type " + x.getClass());
        System.out.println("x instanceof Base " + (x instanceof Base));
        System.out.println("x instanceof Derived " + (x instanceof Derived));
        System.out.println("Base.isInstance(x) " + Base.class.isInstance(x));
        System.out.println("Derived.isInstance(x) " + Derived.class.isInstance(x));
        System.out.println("x.getClass == Base.class" + (x.getClass() == Base.class));
        System.out.println("x.getClass.equals(Base.class)" + (x.getClass().equals(Base.class)));
        System.out.println("x.getClass.equals(Derived.class)" + (x.getClass().equals(Derived.class)));
    }
}

class Base{}
class Derived extends Base{}
