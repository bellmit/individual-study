package cn.gyw.corejava.concurrent.design.master;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable {

	// 任务列表
	private Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();

	// 结果集
	private Map<String, Object> resultMap = new ConcurrentHashMap<>();

	public Task handle(Task task){
		task.setTaskName("task"+task.getId());
		return task;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " 的 woker 开始执行 ...");
		while(true) {
			Task task = taskQueue.poll();
			if(null == task ) break;

			//处理子任务
			Task result = handle(task);
			resultMap.put(Integer.toString(task.hashCode()), result);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+" 的 worker 结束执行。");
	}

	public void setTaskQueue(Queue<Task> taskQueue){
		this.taskQueue = taskQueue;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
}
