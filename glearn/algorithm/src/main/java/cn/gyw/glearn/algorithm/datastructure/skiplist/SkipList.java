package cn.gyw.glearn.algorithm.datastructure.skiplist;

import java.util.Optional;
import java.util.Random;

/**
 * 跳表
 */
public class SkipList<T> {

    private Node<T> head, tail; // 头尾节点
    private int nodes; // 节点总数
    private int level; // 层数
    private Random random; // 随机生成器
    private static final float PROBABILITY = 0.5f; // i层元素出现在i+1 层的概率 p：1/2

    public SkipList() {
        super();
        random = new Random();
        clear();
    }

    public boolean isEmpty() {
        return nodes == 0;
    }

    public int size() {
        return nodes;
    }

    /**
     * 查找是否存储key，存在则返回该节点
     */
    public Optional<T> get(int key) {
        Node<T> cur = findPreNode(key);
        if (key == cur.getKey()) {
            return Optional.of(cur.getValue());
        }
        return Optional.empty();
    }

    /**
     * 新增
     * 
     * @param key
     * @param value
     */
    public void put(int key, T value) {
        Node<T> cur = findPreNode(key);
        if (cur.getKey() == key) { // 相同节点，覆盖
            cur.setValue(value);
            return;
        }
        Node<T> newNode = new Node<T>(key, value);
        backLink(cur, newNode);
        int currentLevel = 0; // 当前层级为0
        // 随机事件
        while (random.nextDouble() < PROBABILITY) { // 是否小于0.5
            if (currentLevel >= level) { // 当前层级超过最大层级，新建一层
                level++;
                Node<T> h = new Node<T>(Node.HEAD_KEY, null);
                Node<T> t = new Node<T>(Node.TAIL_KEY, null);
                horizontalLink(h, t);
                vertiacallLink(h, head);
                vertiacallLink(t, tail);
                head = h;
                tail = t;
            }
            // 将cur 移动到上一层
            while (cur.up == null) {
                cur = cur.left;
            }
            cur = cur.up;
            Node<T> element = new Node<T>(key, null); // 只需要保存key
            backLink(cur, element); // 将element 插入到cur 后
            vertiacallLink(element, newNode); // element <==> newNode
            newNode = element;
            currentLevel++;
        }
        nodes++; // 节点递增
    }

    /**
     * 在最下面一层，找到要插入的位置前面的那个key
     * 
     * @param key
     * @return
     */
    private Node<T> findPreNode(int key) {
        Node<T> cur = head;
        while (true) {
            while (cur.right.key != Node.TAIL_KEY && cur.right.key <= key) {
                cur = cur.right;
            }
            if (cur.down != null) {
                cur = cur.down;
            } else {
                break;
            }
        }
        return cur;
    }

    /**
     * 节点 back 插入到 节点 front
     * 
     * @param front
     * @param back
     */
    private void backLink(Node<T> front, Node<T> back) {
        back.left = front;
        back.right = front.right;
        front.right.left = back;
        front.right = back;
    }

    /**
     * 清空跳跃表
     */
    private void clear() {
        head = new Node<T>(Node.HEAD_KEY, null);
        tail = new Node<T>(Node.TAIL_KEY, null);
        nodes = 0;
        level = 0;
        horizontalLink(head, tail);
    }

    /**
     * 水平双向连接
     * 
     * @param n1
     * @param n2
     */
    private void horizontalLink(Node<T> n1, Node<T> n2) {
        n1.right = n2;
        n2.left = n1;
    }

    /**
     * 垂直双向连接
     * 
     * @param n1
     * @param n2
     */
    private void vertiacallLink(Node<T> n1, Node<T> n2) {
        n1.down = n2;
        n2.up = n1;
    }
}
