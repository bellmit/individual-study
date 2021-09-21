package cn.gyw.glearn.algorithm.datastructure.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.gyw.glearn.algorithm.util.DataGenerator;

public class SmallRootHeapTest {

	private static int[] data;
	private static int n;

	@Test
	public void testFindTopN() {
		System.out.println("SmallRootHeap>>");
		SmallRootHeap heap = new SmallRootHeap(n);
		for (int i = 0; i < n ; i++) {
			if (i < n) {
				heap.offer(data[i]);				
			} else if (heap.peek() < data[i]) {
				heap.poll();
				heap.offer(data[i]);
			}
		}
		int[] array = heap.getData();
		for (int i = 0 , len = n ; i < len ; i ++) {
			System.out.print(array[i] + ", ");
		}
		System.out.println();
	}

	@Test
	public void findTonN() {
		System.out.println("PriorityQueue>>");
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(n);
		for (int i = 0; i < n ; i++) {
			if (i < n) {
				queue.add(data[i]);				
			} else if (queue.element() < data[i]) {
				queue.remove();
				queue.add(data[i]);
			}
		}
		printQueue(queue);
	}
	
	private void printQueue(PriorityQueue<Integer> queue) {
		for (Integer i : queue) {
			System.out.print(i + ", ");
		}
		System.out.println();
	}

	@Before
	public void before() {
		System.out.println("*********Method start************");
		System.out.println(">> data:" + Arrays.toString(data));
	}
	
	@After
	public void after() {
		System.out.println("*********Method end************");
	}
	
	@BeforeClass
	public static void initData() {
		n = 10;
		data = DataGenerator.generateRandomArray(n << 2, 0, 100);
	}
}
