package cn.gyw.glearn.design.behaviour.memento;

import java.util.*;

/**
 * 管理者：管理备忘录
 */
public class Storage {

	//需要管理的备忘录对象，这里也可以使用一个list容器来存储。这样可以备份多个点
	List<Memento> mementos = new ArrayList<>();

	// 使用Stack 来管理
	private Stack<Memento> stack = new Stack<>();

	public void addMemento(Memento memento) {
		mementos.add(memento);
	}

	public Memento getMemento(int index) {
		return mementos.get(index);
	}

	public void addMementoStack(Memento memento) {
		stack.push(memento);
	}

	// 从栈中获取最近一次的备份
	public Memento getFromStack() {
		if (stack.empty()) {
			return null;
		}
		// 只获取，不删除
		return stack.peek();
	}

	// 从栈中获取最近一次的备份
	public Memento getAndRemoveFromStack() {
		if (stack.empty()) {
			return null;
		}
		// 删除元素
		return stack.pop();
	}

	private static Storage instance;

	private Storage(){}

	// 单例模式
	public static Storage getInstance1() {
		return getInstance();
	}

	// 单例模式
	public static Storage getInstance2() {
		return Singleton2.getInstance();
	}

	// 懒汉式，延迟加载，double check
	// 根据JVM的内存管理分配，可能会出现问题
	private static Storage getInstance() {
		if (instance == null) {
			Storage storage;
			synchronized (Storage.class) {
				storage = instance;
				if (storage == null) {
					synchronized (Storage.class) {
						if (storage == null) {
							storage = new Storage();
						}
					}
					instance = storage;
				}
			}
		}

		return instance;
	}

	// 静态内部类实现单例模式：延迟加载
	private static class Singleton2 {
		private final static Storage instance = new Storage();

		public static Storage getInstance() {
			return instance;
		}
	}
}
