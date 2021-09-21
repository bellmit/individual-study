package cn.gyw.corejava.jdk.jdk8.lambda;


import java.util.*;

/**
 * Created by guanyw on 2018/8/20.
 */
public class LambdaExpress {

	/**
	 * 未使用Lambda表达式的排序，匿名内部类
	 */
	public void oldSortMethod(List<String> datas) {
		Collections.sort(datas, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
		});

		System.out.println(datas);
	}

	/**
	 * 使用Lambda表达式的排序
	 */
	public void newSortMethod(List<String> datas) {

		Collections.sort(datas, (String a, String b) -> {
			return a.compareTo(b);
		});
	}
}
