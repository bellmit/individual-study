package cn.gyw.glearn.algorithm.interview.pdd;

import java.util.Scanner;

/**
 * 任意给你一个数n，然后对应一个n*n的矩阵，然后顺时针从1开始填入矩阵中，一直到填满
 * <p>
 * Created by guanyw on 2019/2/12.
 */
public class PddDemo04 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入任意整数：");
        int n = sc.nextInt();
        int[][] m = new int[n][n];
        rotating(n, m);
        print(n, m);
    }

    private static void rotating(int n, int[][] m) {
        int k = 1;
        for (int i = 0; i <= n / 2 ; i++) {

            for (int j = i; j < n - i ; j++) {
                m[i][j] = k++;
            }
            for (int j = i + 1; j < n - i; j++) {
                m[j][n - i - 1] = k++;
            }
            for (int j = n - i - 2 ; j >= i; j--) {
                m[n - i - 1][j] = k++;
            }
            for (int j = n - i - 2 ; j > i ; j --) {
                m[j][i] = k++;
            }
        }
    }

    private static void print(int n, int[][] m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%4d", m[i][j]);
            }
            System.out.print("\n");
        }
    }
}
