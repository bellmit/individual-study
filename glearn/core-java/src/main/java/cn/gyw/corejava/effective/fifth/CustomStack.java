package cn.gyw.corejava.effective.fifth;

import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * 优先使用泛型 重写NomalStack
 * @param <E>
 */
public class CustomStack<E> {

//    private E[] elements; 方法一
    private Object[] elements;
    private int size;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // elements数组只会包含 E 类型的实例，私有域，保证安全
    // 虽然运行时，不是E[] 而是 Object[]
//    @SuppressWarnings("unchecked") // 方法一
    public CustomStack() {
//        elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY]; // 方法一
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        @SuppressWarnings("unchecked")
        E result = (E) elements[--size];
        // 过期引用，防止内存溢出
        elements[size] = null; // Eliminate obsolete reference
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    /**
     * 有限制的通配符
     */
    public void pushAll(Iterable<? extends E> src) { // E 的子类型
        for (E e : src) {
            push(e);
        }
    }

    public void popAll(Collection<? super E> target) { // E 的超类
        while (!isEmpty()) {
            target.add(pop());
        }
    }
}
