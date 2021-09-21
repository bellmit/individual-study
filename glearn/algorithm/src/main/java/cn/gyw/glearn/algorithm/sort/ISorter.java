package cn.gyw.glearn.algorithm.sort;

/**
 * 排序算法
 */
public interface ISorter {

    /**
     * 排序
     * @param data
     */
    void sort(int[] data);

    /**
     * 返回执行次数
     * @return
     */
    int getRunCnt();
}
