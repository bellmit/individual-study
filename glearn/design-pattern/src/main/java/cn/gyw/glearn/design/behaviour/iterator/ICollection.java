package cn.gyw.glearn.design.behaviour.iterator;

/**
 * 提供一种方法，顺序的访问一个聚合对象中的各个元素，而不是暴露各个对象的内部表示
 *
 */
public interface ICollection {

	Iterator iterator();

	Object get(int i);

	int size();
}
