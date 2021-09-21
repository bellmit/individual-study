package cn.gyw.glearn.algorithm.datastructure.tree.tire;

/**
 * 二叉树
 * 查找树
 * <p>
 * 1）左子树小于根节点
 * 2）右子树大于根节点
 */
public class NodeTreeSearch {

    int data;
    NodeTreeSearch left;
    NodeTreeSearch right;

    NodeTreeSearch() {

    }

    NodeTreeSearch(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    // 元素插入
    public void insert(NodeTreeSearch root, int data) {
        if (data > root.data) { // 右分支
            if (root.right == null) {
                NodeTreeSearch right = new NodeTreeSearch(data);
                root.right = right;
            } else {
                insert(root.right, data);
            }
        } else { // 左分支
            if (root.left == null) {
                NodeTreeSearch left = new NodeTreeSearch(data);
                root.left = left;
            } else {
                insert(root.left, data);
            }
        }
    }

    public void inOrder(NodeTreeSearch root) {
        if (root != null) {
            inOrder(root.left);
            System.out.println(root.data + " ");
            inOrder(root.right);
        }
    }


    public static void main(String[] args) {
        int[] data = {3, 14, 7, 1, 1, 8};

        NodeTreeSearch root = new NodeTreeSearch(0);
        for (int i = 0, len = data.length; i < len; i++) {
            root.insert(root, data[i]);
        }

        System.out.println("中序遍历");
        root.inOrder(root);
    }
}
