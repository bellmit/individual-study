package cn.gyw.corejava.collection.stack;

import cn.gyw.corejava.exceptions.StackEmptyException;

/**
 * 自定义栈接口
 * Created by guanyw on 2019/2/26.
 */
public interface StackExercise<T> {

    /**
     * Return and remove the most recent item from the top of stack.
     * @param <T>
     * @return
     * @throws StackEmptyException if the stack is empty
     */
    T pop() throws StackEmptyException;

    /**
     * Add an item to the top of stack.
     * @param t
     */
    void push(T t);

    /**
     * Return but do not remove the most recent item from the top of stack.
     * @return
     * @throws StackEmptyException if the stack is empty
     */
    T top() throws StackEmptyException;

    /**
     * Returns true if the stack is empty.
     * @return
     */
    boolean isEmpty();

}
