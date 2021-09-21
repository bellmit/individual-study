package cn.gyw.glearn.algorithm.datastructure.heap;

/**
 * 小根堆
 * 
 * 场景：10亿数据取最大的1000个数 假设：存储的是int 32位（4个字节）4GB的存储量 内存：2G
 *
 * 应用：找出一堆数中，最大的前100，不用排序（使用小顶堆，根节点比子节点都小）
 * 
 * 注意：元素大小并不是按数组下标来排序的，下图的数字对应数组的坐标 0 1 2 3 4 5 6
 */
public class SmallRootHeap {

	// 一个完全二叉树，所以它也就可以用数组存储
	private int[] inarray;

	private int size = 0;

	public SmallRootHeap(int n) {
		this.inarray = new int[n];
	}

	public SmallRootHeap(int n, int[] data) {
		this.inarray = new int[n];
		heapify(data);
	}

	// 构建小根堆 Heap (取top的n个数，data[]原数组)
	private void heapify(int[] data) {
		for (int i = 0, len = inarray.length; i < len; i++) {
			offer(data[i]);
		}
	}

	public int peek() {
		return this.inarray[0];
	}
	
	public int[] getData() {
		return this.inarray;
	}

	public int size() {
		return this.size;
	}

	// 插入
	// 每次都在最后一个插入，然后上浮到合适位置
	public boolean offer(int value) {
		int i = size ;
		size = size + 1;
		if (i == 0) {
			inarray[0] = value;
		} else {
			siftUp(i, value);			
		}
		return true;
	}

	// 弹出根元素
	// 让根元素和尾元素交换，让现在的根元素下沉即可
	public int poll() {
		if (size ==0 ) {
			return -1;
		}
		int lastIndex = --size;
		int head = inarray[0];
		int tail = inarray[lastIndex];
		inarray[lastIndex] = -1;
		if (lastIndex != 0) {
			siftDown(0, tail);
		}
		return head;
	}

	/**
	 * 下沉：将大的元素向下移动、当删除元素时，将首位交换，弹出尾部，首部下移即可调整位置
	 * 
	 * @param i
	 */
	public void siftDown(int index, int element) {
		// 非叶子节点循环，[0,9] -> 非叶子节点 [0,3]
		int half = size >>> 1;
		while (index < half) { // 只比较非叶子节点
			// assume left child is least
			int child = left(index);
			int rightIndex = right(index);

			int min = inarray[child];
			// 比较左右孩子谁更小，右孩子更小，使用右孩子
			if (rightIndex < size && inarray[rightIndex] < inarray[child]) { // 右孩子更小
				min = inarray[child = rightIndex];
			}
			// 当前节点小于孩子节点，直接退出
			if (element < inarray[child]) {
				break;
			}
			inarray[index] = min;
			index = child; // 当前孩子节点开始
		}
		inarray[index] = element;
	}

	/**
	 * 上浮：将小的元素往上移动、当插入元素时，将元素插入末尾，这样上移即可调整位置
	 */
	public void siftUp(int index, int element) {
		while (index > 0) {
			int parentIndex = parent(index);
			int parent = inarray[parentIndex];
			// 父节点小于等于当前节点，直接返回
			if (element >= parent) {
				break;
			}
			// 父节点大于当前节点，当前节点上移
			inarray[index] = parent;
			index = parentIndex;
		}
		inarray[index] = element;
	}

	// 下标为 i 的结点的父节点的下标是 （i - 1）/ 2
	private int parent(int i) {
		return (i - 1) / 2;
	}

	// 左孩子结点为 (2 * i) + 1;
	private int left(int i) {
		return 2 * i + 1;
	}

	// 右孩子结点为 （2 * i ）+ 2
	private int right(int i) {
		return 2 * i + 2;
	}

}
