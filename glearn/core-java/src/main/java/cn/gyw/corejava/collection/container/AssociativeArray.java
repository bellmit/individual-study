package cn.gyw.corejava.collection.container;

public class AssociativeArray<K,V> {
	private Object[][] pairs;
	private int index;

	public AssociativeArray(int length) {
		pairs = new Object[length][2];
	}

	public void put(K key , V value) {
		if(index > pairs.length)
			throw new ArrayIndexOutOfBoundsException();
		pairs[index++] = new Object[] {key,value};
	}

	@SuppressWarnings("unchecked")
	public V get(K key) {
		for(int i = 0 ; i < index ; i++) {
			if(key.equals(pairs[i][0])){
				return (V) pairs[i][1];
			}
		}
		return null;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < index ; i++) {
			sb.append(pairs[i][0])
			  .append(" : ")
			  .append(pairs[i][1]);
		}
		return sb.toString();
	}
}
