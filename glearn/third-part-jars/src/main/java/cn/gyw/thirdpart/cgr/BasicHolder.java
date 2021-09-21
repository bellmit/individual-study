package cn.gyw.thirdpart.cgr;

public class BasicHolder<T> {

    T element;

    void set(T arg) {
        this.element = arg;
    }

    T get() {
        return this.element;
    }

    void print() {
        System.out.println(element.getClass().getSimpleName());
    }

}
