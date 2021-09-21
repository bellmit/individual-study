package cn.gyw.glearn.algorithm;

import java.util.*;

/**
 * 描述：
 * 给定一个字符串列表words，找到words最长的word，使得这个word可用words中的其他word一次一个字符地构建。
 * 如果有多个可选答案，则返回最长的且具有最小字典序的word。
 *
 * Ⅰ. Input: words = ["w","wo","wor","worl", "world"]
 *     Output: "world"
 *     Explanation: “world”可通过”w”, “wo”, “wor”, “worl”一次一个字符进行构建。
 * Ⅱ . Input: words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
 *      Output:"apple"
 *     Explanation: “apply”和”apple”都可以由其他字符构建。但”apple”的字典序要小于”apply”。
 *
 *
 * 注意：
 *       所有的输入字符只包含小写字符。
 *       words的长度在[1, 1000]范围内。
 *       words[i]的长度在[1, 30]范围内。
 */
public class WordSearch {

    private String[] words = {"a", "banana", "app", "appl", "ap", "apply", "apple"};

    /**
     * 方法一 hash
     *
     */
    private void solution1() {
        Set<String> set = new HashSet<>(Arrays.asList(words));
        String ans = "";

        for (String word : set) {
            boolean flag = true;
            System.out.println("current word :" + word);
            for (int i = 0, len = word.length() ; i < len - 1 ; i ++) {
                String temp = word.substring(0, i + 1);
                System.out.println(i + ">>" + temp);
                if (!set.contains(temp)) {
                    flag = false;
                    // 数据不存在下个单词
                    break;
                }
            }

            if (flag) {
                if (word.length() > ans.length()
                        || (word.length() == ans.length() && word.compareTo(ans) > 0)) {
                    ans = word;
                }
            }
            System.out.println(flag + "---->" + ans);
        }

        System.out.println("final result :" + ans);
    }

    /**
     * 方法二 Trie树
     */
    private void solution2() {
        Trie trie = new Trie(words);
        for (int i = 0 ; i < words.length ; i ++) {
            trie.insert(words[i], i + 1);
        }
        System.out.println(">> final result :" + trie.dfs());
    }

    private class Trie {
        Node root;
        String[] words;

        Trie(String[] words) {
            this.root = new Node('0');
            this.words = words;
        }

        void insert(String word, int index) {
            Node cur = root;
            for (char c : word.toCharArray()) {
                cur.children.putIfAbsent(c, new Node(c));
                cur = cur.children.get(c);
            }
            cur.end = index;
        }

        /**
         * 深度优先遍历
         * @return
         */
        String dfs() {
            String ans = "";
            Stack<Node> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                Node t = stack.pop();
                if (t.end > 0 || t == root) {
                    if (t != root) {
                        String word = words[t.end - 1];
                        if (word.length() > ans.length()
                                || (word.length() == ans.length() && word.compareTo(ans) > 0)) {
                            ans = word;
                        }
                    }

                    for (Node n : t.children.values()) {
                        stack.push(n);
                    }
                }
            }

            return ans;
        }
    }

    private class Node {
        char c;
        Map<Character, Node> children = new HashMap<>();
        int end; // 非 0 表示非根节点

        Node(char c) {
            this.c = c;
        }
    }


    public static void main(String[] args) {
        WordSearch wordSearch = new WordSearch();
        // wordSearch.solution1();
        wordSearch.solution2();
    }
}
