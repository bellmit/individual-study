package cn.gyw.thirdpart.cgr;

//自限定采取额外的步骤，强制泛型当作其自己的边界参数来使用。
class SelfBounded<T extends SelfBounded<T>> {
    T element;
    SelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }
    T get(){
        return element;
    }
}

class A extends SelfBounded<A>{}
class B extends SelfBounded<A>{}

class C extends SelfBounded<C>{
    C setAndGet(C arg){
        set(arg);
        return get();
    }
}

class D {}

class F extends SelfBounded{}

public class SelfBounding{
    public static void main(String[] args) {
        A a = new A();
        a.set(new A());
        a = a.set(new A()).get();
        a = a.get();
        C c = new C();
        c = c.setAndGet(new C());
    }
}