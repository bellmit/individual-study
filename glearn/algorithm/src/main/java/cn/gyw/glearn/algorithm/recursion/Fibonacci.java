package cn.gyw.glearn.algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归示例
 *
 * 斐波拉契数列
 */
public class Fibonacci {

    private static List<Integer> datas = new ArrayList<>();

    // 结果对象，引用传递，保存递归返回值
    public void fn(int first, int second, int count, List<Integer> result) {
        count --;
        int next = first + second;
        System.out.println(next);

        result.add(next);
        if (count == 0) {
            // 每次递归里的list 都是一个副本，运行结果长度为1
            // datas.add(next);

            return ;
        }

        fn (second, next, count, result);
    }

    private int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int first = fib(n - 2);
        int second = fib(n -1);
        return fib(n - 2) + fib(n -1);
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        datas.add(1);
        datas.add(1);
        fibonacci.fn(1,1, 6, datas);
        System.out.println("列表长度" + datas.size());
        datas.forEach((data) -> {
            System.out.print(data + "\t");
        });
        System.out.println();
        for (int i = 0 ; i < 8 ; i++) {
            System.out.println(fibonacci.fib(i));
        }

    }
}
