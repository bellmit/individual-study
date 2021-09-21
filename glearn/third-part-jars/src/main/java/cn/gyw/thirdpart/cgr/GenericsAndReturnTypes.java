package cn.gyw.thirdpart.cgr;

interface GenericGetter<T extends GenericGetter<T>> {
    T get();
}

interface Getter extends GenericGetter<Getter>{

}

public class GenericsAndReturnTypes {
    void test(Getter g) {
        Getter result = g.get();
        GenericGetter gg = g.get();
        System.out.println("1"+result.getClass().getSimpleName());
        System.out.println("2"+gg.getClass().getSimpleName());
    }
}
