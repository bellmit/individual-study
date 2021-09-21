package cn.gyw.glearn.algorithm.datastructure.tree.tire;

import java.util.Collections;
import java.util.List;

/**
 * 基础字典树
 * <p>
 * 示例：构建一个英文字典树
 * <p>
 * 时间复杂度：O(n)
 * 空间复杂度：空间换时间，每一层都是一个26大小的数组，耗费空间
 */
public class TireTree {

    private TreeNode root;

    public TireTree() {
        root = new TreeNode();
    }

    // 构建字典树
    public void createTireTree(String source) {
        //ascii A => 65,a=>97 -97 >0
        //a->0 b->1,c->2
        TreeNode node = root;
        char[] chars = source.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            //转成0~25之间的数字了
            int loc = chars[i] - 'a';

            if (node.childs[loc] == null) {
                node.childs[loc] = new TreeNode();
                node.childs[loc].data = chars[i];
            }
            node = node.childs[loc];
        }
        // 最后一个节点一定是叶子节点
        node.isEnd = true;
    }

    // 查找
    public boolean find(String target) {
        char[] chars = target.toCharArray();
        TreeNode node = root;
        for (int i = 0, len = chars.length; i < len; i++) {
            int loc = chars[i] - 'a';
            if (node.childs[loc] != null) {
                // 继续寻找下一层节点
                node = node.childs[loc];
            } else {
                return false;
            }
        }
        // 只有叶子节点才是完整的单词
        return node.isEnd;
    }

    // 自动补全 TODO
    public List<String> autoComplete(String source) {
        TreeNode node = root;
        char[] chars = source.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            int loc = chars[i] - 'a';
            if (node.childs[loc] != null) {
                node = node.childs[loc];
            } else {
                return Collections.emptyList();
            }
        }
        System.out.println("current node data :" + node.data);

        for (int i = 0, len = node.childs.length; i < len; i++) {
            nextChar(node.childs[i]);
        }

        return Collections.emptyList();
    }

    private char nextChar(TreeNode node) {
        TreeNode cnode = node;
        if (cnode == null) {
            nextChar(cnode);
        }
        for (int i = 0, len = node.childs.length; i < len; i++) {
            nextChar(node.childs[i]);
        }

        System.out.println("node.data :" + node.data);
        return node.data;
    }


    class TreeNode {

        // 小写英文字母26
        private final static int MAX_SIZE = 26;

        // 节点数据，字符
        private char data;

        // 表示子节点
        private TreeNode[] childs;

        // 是否叶子节点
        private boolean isEnd = false;

        TreeNode() {
            childs = new TreeNode[MAX_SIZE];
            isEnd = false;
        }
    }

    public static void main(String[] args) {
        TireTree tireTree = new TireTree();

        String[] sources = new String[]{"java", "php", "c", "linux", "jav"};

        for (String source : sources) {
            tireTree.createTireTree(source);
        }

        /*String target = "java";
        System.out.println(tireTree.find(target));
        target = "jav";
        System.out.println(tireTree.find(target));*/

        tireTree.autoComplete("jav");
    }
}