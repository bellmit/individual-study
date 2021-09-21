package cn.gyw.glearn.algorithm.interview;

/**
 * 字符串反转
 *
 * 方法：
 * 1.递归
 */
public class StringReverseRecursion {

    private static String reverse(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return reverse(str.substring(1)) + str.charAt(0);
    }

    public static void main(String[] args) {
        String str = "ABCDEF";
        System.out.println(reverse(str));
    }
}
