package cn.gyw.glearn.algorithm.datastructure.skiplist;

/**
 * Skip List Node
 * 1) key - value
 * 2) 上下左右四个指针
 * @param <T>
 */
class Node<T> {

    public int key;
    public T value;
    public Node<T> up, right, down, left; // 上下左右四个指针
    public static final int HEAD_KEY = Integer.MIN_VALUE; // 默认跳表head
    public static final int TAIL_KEY = Integer.MAX_VALUE; // 默认跳表tail

    public Node(int key, T value) {
        super();
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Node<?>)) {
            return false;
        }
        Node<?> ent;
        try {
            ent = (Node<T>) obj; // 转换类型
        } catch (ClassCastException e) {
            return false;
        }
        return (ent.getKey() == key) && (ent.getValue() == value);
    }
    
    @Override
    public String toString() {
        return "key-value:" + key + ">" + value;
    }
}
