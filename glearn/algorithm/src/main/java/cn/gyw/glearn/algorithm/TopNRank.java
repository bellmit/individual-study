package cn.gyw.glearn.algorithm;

import java.util.*;

/**
 * Rank n 获取最新的n个数据算法
 * Created by guanyw on 2018/7/27.
 */
public class TopNRank {

	private Map<Long, Long> datas;
	private List<Long> topNList;

	public static void main(String[] args) {
//		new TopNRank().rank2();
		List<String> list = new ArrayList<>();
		list.set(1,"serwr");

	}

	private void rank() {
		init();
		int topN = 3;
		int count = 1;
		for (Map.Entry<Long, Long> entry : datas.entrySet()) {
			System.out.println(count + ":" + topNList);
			if (topNList.size() == 0) {
				topNList.add(entry.getValue());
			} else {
				for (int i = 0, lastIndex = topNList.size(); i < lastIndex; i++) {
					if (entry.getValue() > topNList.get(i)) {
						if (lastIndex < topN) {
							topNList.add(topNList.get(i));
						} else {
							for (int j = i; j < lastIndex - 1; j++) {
								topNList.set(j + 1, topNList.get(j));
							}
						}

						topNList.set(i, entry.getValue());
					}
				}
			}
			count++;
		}

		System.out.println("最终List：" + topNList);
	}

	private void rank2() {
		init();
		int topN = 3;
		int count = 1;
		for (Map.Entry<Long, Long> entry : datas.entrySet()) {
			System.out.println(count + ":" + topNList);
			if (topNList.size() == 0) {
				topNList.add(entry.getValue());
			} else {
				boolean isBigger = false;
				for (int i = 0; i < topNList.size() ; i++) {
					if (entry.getValue() > topNList.get(i)) {

						int lastIndex = topNList.size() < topN ? topNList.size() - 1 : topN - 2;

						for (int j = lastIndex; j >= i; j--) {
							topNList.set(j + 1, topNList.get(j));
						}
						topNList.set(i, entry.getValue());
						isBigger = true;
						break;
					}
				}

				if (isBigger) {
					if (topNList.size() < topN) {
						topNList.add(entry.getValue());
					}
				}
			}
			count++;
		}

		System.out.println("最终List：" + topNList);
	}

	private void init() {
		datas = new HashMap<>();
		topNList = new ArrayList<>();
		Long[] longs = new Long[]{8L, 2L, 7L, 6L, 2L, 9L, 4L, 7L, 9L, 8L, 9L};
		long i = 0;
		for (Long data : longs) {
			datas.put(i++, data);
		}
		System.out.println(datas);
	}

}
