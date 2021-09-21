package cn.gyw.glearn.algorithm.interview.pdd;

/**
 * 有两个用字符串表示的非常大的大整数,算出他们的乘积，也是用字符串表示。
 *
 * 不能用系统自带的大整数类型
 *
 * Created by guanyw on 2019/2/12.
 */
public class PddDemo02 {

    public static void main(String[] args) {
        String bigint1 = "11";
        String bigint2 = "22";

        System.out.println("1".charAt(0) - '0');
        System.out.println("1".charAt(0));

        System.out.println(bigint1.length() + "> max int :" + Integer.MAX_VALUE);
        System.out.println(new PddDemo02().multiply(bigint1, bigint2));
    }

    private String multiply(String str1, String str2) {
        // 低位先算，字符串反转
        String num1 = new StringBuilder(str1).reverse().toString();
        String num2 = new StringBuilder(str2).reverse().toString();
        // 存放结果的数组，最大长度为两者之和
        int count1 = num1.length();
        int count2 = num2.length();
        int[] arr = new int[count1 + count2];
        // 每个位的和
        for (int i = 0 ; i < count1 ; i ++) {
            for (int j = 0 ; j < count2 ; j++) {
                arr[i + j] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            }
        }

        StringBuilder builder = new StringBuilder();
        // 每个位满10进位
        for (int i = 0 ; i < arr.length ; i ++) {
            int cur = arr[i] % 10;
            int postCur = arr[i] / 10;
            if (i + 1 < arr.length) {
                arr[i + 1] += postCur;
            }
            builder.insert(i, cur);
        }
        if (builder.reverse().charAt(0) == '0') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }
}
