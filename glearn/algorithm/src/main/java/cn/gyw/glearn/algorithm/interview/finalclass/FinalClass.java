package cn.gyw.glearn.algorithm.interview.finalclass;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 写一个不可变类
 *
 * 要点：
 * 1.类修饰符为final ，不可以被继承
 * 2.变量私有
 * 3.变量对外不提供Setter方法
 * 4.变量final ，只允许赋值一次
 * 5.通过构造器初始化所有成员，引用表面使用深拷贝
 * 6.getter方法中，不直接返回对象本身，而是克隆对象，并返回对象的拷贝
 * Created by guanyw on 2018/7/11.
 */
public final class FinalClass {

	private final int id;

	private final HashMap map;

	/**
	 * 实现深拷贝的构造器
	 * @param id
	 * @param map
	 */
	public FinalClass(int id, HashMap map) {
		System.out.println("Performing Deep Copy for Object initialization");
		this.id = id;
		Map tempMap = new HashMap();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			tempMap.put(key, map.get(key));
		}
		this.map = (HashMap) tempMap;
	}

	/**
	 * 浅拷贝
	 * @param id
	 * @param map
	 */
	/*public FinalClass(int id, HashMap map) {
		System.out.println("Performing Shallow Copy for Object initialization");
        this.id=id;
        this.map=map;
	}*/

	/**
	 * 基本数据类型直接返回
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 对象不直接返回
	 * @return
	 */
	public HashMap getMap() {
		return (HashMap) map.clone();
	}

	public static void main(String[] args) {
		HashMap map = new HashMap();
		map.put("first", "AAA");

		int id = 10;
		FinalClass finalClass = new FinalClass(id, map);

		System.out.println("id==final.id >" + (id==finalClass.getId()));
		System.out.println("map==final.map >" + (map==finalClass.getMap()));

		id = 30;
		map.put("second", "BBB");

		System.out.println("id :" + finalClass.getId());
		System.out.println("map :" + finalClass.getMap());


	}
}
