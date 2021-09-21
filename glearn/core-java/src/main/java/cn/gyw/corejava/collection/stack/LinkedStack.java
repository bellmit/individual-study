package cn.gyw.corejava.collection.stack;

import java.util.Objects;

import cn.gyw.corejava.exceptions.StackEmptyException;

public class LinkedStack<T> implements StackExercise<T>{

    private Node<T> top;
    private int size;

    @Override
    public T pop(){
        if (top == null) {
            throw new StackEmptyException("Stack is empty.");
        }
        T result = top.item;
        if(!top.end()) {
            top = top.next;
        }
        size--;
        return result;
    }

    @Override
    public void push(T item) {
        top = new Node<>(item, top);
        size++;
    }

    @Override
    public T top() throws StackEmptyException {
        if (Objects.isNull(top)) {
            throw new StackEmptyException("Stack is empty.");
        }
        return top.item;
    }

    @Override
    public boolean isEmpty() {
        if (Objects.isNull(top)) {
            return true;
        }
        return false;
    }

    private static class Node<T> {
        T item;
        Node<T> next;

        Node(){
            item = null;
            next = null;
        }

        Node(T item , Node<T> next){
            this.item = item;
            this.next = next;
        }

        boolean end(){
            return item==null && next==null;
        }
    }

}
