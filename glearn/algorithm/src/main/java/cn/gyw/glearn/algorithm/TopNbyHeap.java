package cn.gyw.glearn.algorithm;

/**
 * 选取Top N 个数
 * <p>
 * 场景：10亿数据取最大的1000个数
 * 假设：存储的是int 32位（4个字节）4GB的存储量
 * 内存：2G
 * <p>
 * 应用：找出一堆数中，最大的前100，不用排序（使用小顶堆，根节点比子节点都小）
 */
public class TopNbyHeap {

    private int parent(int n) {
        return (n - 1) / 2;
    }

    private int left(int n) {
        return 2 * n + 1;
    }

    private int right(int n) {
        return 2 * n + 2;
    }

    /**
     * 获取一组元素中，最大的前n 个元素（未排序）
     * @param n 前n 个元素
     * @param data 源数据
     */
    public int[] findTopN(int n, int[] data) {
        int[] target = new int[n];
        // 数组拷贝
        System.arraycopy(data, 0, target, 0, n);

        // 构建
        buildHeap(n, target);

        for (int i = n, len = data.length; i < len; i ++) {
            adjustHeap(data[i], n, target);
        }

        return target;
    }

    // 构建小顶堆
    private void buildHeap(int n , int[] data) {
        for (int i = 1 ; i < n ; i ++) {
            int t = i;
            while (t != 0 && data[parent(t)] > data[t]) {
                int temp = data[t];
                data[t] = data[parent(t)];
                data[parent(t)] = temp;
                t = parent(t);
            }
        }
    }

    // 调整小顶堆
    private void adjustHeap(int curVal, int n, int[] data) {
        // 根节点最小，比根节点还小，直接返回
        if (curVal < data[0]) {
            return ;
        }

        int temp = curVal;
        data[0] = temp;

        int t =0;
        while ((left(t) < n && data[left(t)] < curVal)
            || (right(t) < n && data[right(t)] < curVal)) {
            if (right(t) < n && data[right(t)] < data[left(t)]) {
                temp = data[t];
                data[t] = data[right(t)];
                data[right(t)] = temp;
                t = right(t);
            } else {
                temp = data[t];
                data[t] = data[left(t)];
                data[left(t)] = temp;
                t = left(t);
            }
        }
    }

}
