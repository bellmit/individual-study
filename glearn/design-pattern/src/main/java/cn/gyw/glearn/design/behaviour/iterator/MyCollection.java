package cn.gyw.glearn.design.behaviour.iterator;

public class MyCollection implements ICollection {

	String[] strs= {"A","B","C","D","E","F","G"};

	@Override
	public Iterator iterator() {
		return new MyIterator();
	}

	@Override
	public Object get(int i) {
		return strs[i];
	}

	@Override
	public int size() {
		return strs.length;
	}

	/**
	 * 使用内部类实现Iterator接口
	 * 好处是：可以使用外部类的属性
	 */
	private class MyIterator implements Iterator {

		// 定义一个迭代器的游标
		private int cursor;

		@Override
		public void first() {
			cursor = 0;
		}

		@Override
		public void next() {
			if (cursor < strs.length) {
				cursor ++;
			}
		}

		@Override
		public boolean hasNext() {
			if (cursor < strs.length) {
				return true;
			}
			return false;
		}

		@Override
		public boolean isFirst() {
			return cursor == 0 ? true : false;
		}

		@Override
		public boolean isLast() {
			return cursor == (strs.length - 1) ? true : false;
		}

		@Override
		public Object getCurrentObj() {
			return strs[cursor];
		}
	}

}
