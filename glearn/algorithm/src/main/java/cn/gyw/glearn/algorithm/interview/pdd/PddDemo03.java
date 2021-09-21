package cn.gyw.glearn.algorithm.interview.pdd;

import java.util.Arrays;

/**
 * 六一儿童节，老师带了很多好吃的巧克力到幼儿园。
 * 每块巧克力j的重量为w[j]，对于每个小朋友i，当他分到的巧克力大小达到h[i] (即w[j]>=h[i])，
 * 他才会上去表演节目。
 * 老师的目标是将巧克力分发给孩子们，使得最多的小孩上台表演。
 * 可以保证每个w[i]> 0且不能将多块巧克力分给一个孩子或将一块分给多个孩子。
---------------------

 * Created by guanyw on 2019/2/12.
 */
public class PddDemo03 {

    public static void main(String[] args) {
        // 巧格力
        int[] chocalates = new int[] {1, 2, 3, 4, 5, 6};

        // 孩纸期望的巧克力大小
        int[] childs = new int[] {13, 15, 12};

        // 排序
        Arrays.sort(chocalates);
        Arrays.sort(childs);

        int j = 0;
        // 表演人数
        int count = 0;
        for (int i = 0 ; i < chocalates.length ; i ++) {
            if (j < childs.length && chocalates[i] >= childs[j]) {
                count++;
                j++;
            }
        }
        System.out.println(count);
    }
}
