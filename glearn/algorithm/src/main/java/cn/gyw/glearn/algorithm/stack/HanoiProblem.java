package cn.gyw.glearn.algorithm.stack;

import java.util.Stack;

/**
 * 用栈来解决汉诺塔问题
 *
 * 问题：
 * 限制不能从最左侧的塔直接移动到最右侧，也不能从最右侧直接移动到最左侧，而是必须经过中间
 * 求当塔有N层时，打印最优移动过程和最优移动步数。
 *
 * 例如 ：
 * 当塔有2层，最上层记为1，最下层记为2；
 * Move 1 from left to mid
 * Move 1 from mid to right
 * Move 2 from left to mid
 * Move 1 from right to mid
 * Move 1 from mid to left
 * Move 2 from mid to right
 * Move 1 from left to mid
 * Move 1 from mid to right
 * It will move 8 steps
 */
public class HanoiProblem {

    /**
     * 方法一、递归方法
     *
     * 假设只有一层塔：分为5种情况，递归的收敛条件
     * 多层塔情况分析：
     * 1）从左到中
     * 2）从右到中，从中到左，从中到右，和1）类似
     * 3）从左到右
     * 4）从有到左，和3）类似
     */
    public int solveMethod1(int num, String left, String mid, String right) {
        // 如果小于1排列完毕
        if (num < 1) {
            return 0;
        }
        return process(num, left, mid, right, left, right);
    }

    private int process(int num, String left, String mid, String right,
                        String from, String to) {
        // 只有顶层需要移动，递归的收敛条件
        if (num == 1) {
            // l-m,r-m,m-l,m-r, step=1
            if (from.equals(mid) || to.equals(mid)) {
                System.out.println("Move 1 from " + from + " to " + to);
                return 1;
            } else { // left-right,right-left,step=2
                System.out.println("Move 1 from " + from + " to " + mid);
                System.out.println("Move 1 from " + mid + " to " + to);
                return 2;
            }
        }
        // 1~N层塔，如果希望左移到中，从中移到左，右移到中，中移到右，需要三步
        /* 以 左到中 为例
        * 1.将1~N-1 从左移到右，递归来做
        * 2.将N 从左，移到中
        * 3.将1~N-1 从右移到中，递归来做
        * */
        if (from.equals(mid) || to.equals(mid)) {
            String another = (from.equals(left) || to.equals(left)) ? right : left;
            int part1 = process(num - 1, left, mid, right, from, another);
            int part2 = 1;
            System.out.println("Move " + num + " from " + from + " to " + to);
            int part3 = process(num - 1, left, mid, right, another, to);
            return part1 + part2 + part3;
        } else {
            // 1~N层塔，从左到右，从右到左，需要5步
            /* 左到右为例
            1.1~N-1 从左到右，递归
            2.N 从左到中
            3.1~N-1 从右到左，递归
            4.N 从中到右
            5.1~N-1 从左到右，递归
             */
            int part1 = process(num -1, left, mid, right, from, to);
            int part2 = 1;
            System.out.println("Move " + num + " from " + from + " to " + mid);
            int part3 = process(num -1, left, mid, right, to, from);
            int part4 = 1;
            System.out.println("Move " + num + " from " + mid + " to " + to);
            int part5 = process(num -1, left, mid, right, from, to);
            return part1 + part2 + part3 + part4 + part5;
        }
    }

    /**
     * 方法二、非递归方法，用栈来模拟汉诺塔的三个塔
     *
     * 分析：实际移动动作，实际为：左到中，中到左，右到中，中到右
     * 考虑把左、中、右抽象成三个栈：LS、MS、RS，数据最初都在LS栈上
     * 先决条件：
     * 1）不允许以大压小，from栈要压入to栈，那么num的值必须小于to栈的栈顶
     * 2）相邻不可逆：L->M 和 M->L 互逆，不可相邻
     *
     * 结论：
     * 1.游戏的第一个动作肯定是L->R
     * 2.最小步数的过程中，四个动作只有个一个动作不违反小压大和相邻不可逆的原则，另外三个动作都违反
     */
    public int solveMethod2(int num, String left, String mid, String right) {
        Stack<Integer> ls = new Stack<>();
        Stack<Integer> ms = new Stack<>();
        Stack<Integer> rs = new Stack<>();

        ls.push(Integer.MAX_VALUE);
        ms.push(Integer.MAX_VALUE);
        rs.push(Integer.MAX_VALUE);

        for (int i = num; i > 0 ; i --) {
            ls.push(i);
        }

        Action[] record = {Action.NO};
        int step = 0;
        // 如果最右边的个数没到ls的大小，则没完成
        while (rs.size() != (num + 1)) {
            step += fStackToStack(record, Action.MToL, Action.LToM, ls, ms, left, right);
            step += fStackToStack(record, Action.LToM, Action.MToL, ms, ls, mid, left);
            step += fStackToStack(record, Action.MToR, Action.RToM, rs, ms, right, mid);
            step += fStackToStack(record, Action.RToM, Action.MToR, ms, rs, mid, right);
        }
        return step;
    }

    private static int fStackToStack(Action[] record, Action preNoAct,
                                     Action nowAct, Stack<Integer> fromStack, Stack<Integer> toStack,
                                     String from, String to) {
        if (record[0] != preNoAct && fromStack.peek() < toStack.peek()) {
            toStack.push(fromStack.pop());
            System.out.println("Move " + toStack.peek() + " from " + from + " to " + to);
            record[0] = nowAct;
            return 1;
        }
        return 0;
    }

    public enum Action {
        NO,
        LToM,
        MToL,
        RToM,
        MToR;
    }
}
