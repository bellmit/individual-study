package cn.gyw.corejava.collection.container;

public class TestAssociativeArray {
	public static void main(String[] args) {
		AssociativeArray<String, String> map = new AssociativeArray<>(5);
		map.put("AA", "first");
		map.put("BB", "second");
		map.put("CC", "111");
		map.put("DD", "222");
		map.put("EE", "333");
//		map.put("FF", "444"); java.lang.ArrayIndexOutOfBoundsException

//		Map map1 = new HashMap<>(arg0, arg1)

		System.out.println(map);
	}
}
