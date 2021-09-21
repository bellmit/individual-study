package cn.gyw.corejava.concurrent.design.master;

import java.util.Map;
import java.util.Set;

public class Client {
	public static void main(String[] args) {
		Master master = new Master();
		for (int i = 1 ; i < 100 ; i++) {
			Task task = new Task();
			task.setId(i);
			master.submit(task);
		}
		master.allotWorker();
		master.execute();

		int result = 1;
		Map<String, Object> resultMap = master.getResultMap();

		while (resultMap.size() > 0 || !master.isComplete()) {
			Set<String> keys = resultMap.keySet();
			String key = null;
			for (String k : keys) {
				key = k;
				break;
			}
			Task t = null;
			if (key != null) {
				t = (Task) resultMap.get(key);
			}

			if (t != null) {
				System.out.println(t);
				result += 1; // 并行计算结果集
			}

			if (key != null) {
				resultMap.remove(key); // 将计算完成的结果移除
			}
		}
		System.out.println("最终结果集行数：" + result);
	}

}
