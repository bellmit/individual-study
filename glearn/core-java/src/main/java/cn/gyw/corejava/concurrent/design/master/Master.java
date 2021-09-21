package cn.gyw.corejava.concurrent.design.master;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

	// 任务列表
	public Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();
	// worker进程队列
	protected Map<String,Thread> threadMap = new HashMap<String,Thread>();
	// 结果集
	public Map<String, Object> resultMap = new ConcurrentHashMap<>();

	public void allotWorker(){

		int count = 0;
		int threadCount = 0;
		if( (count = taskQueue.size()) > 0){
			threadCount = (count%10 + count)/10;
		}
		for(int i = 0 ; i < threadCount ; i++){
			Worker worker = new Worker();
			worker.setTaskQueue(taskQueue);
			worker.setResultMap(resultMap);
			threadMap.put(Integer.toString(i), new Thread(worker,"Worker线程 " + i));
		}
	}

	//是否所有的子任务都结束
	public boolean isComplete(){
		for(Map.Entry<String, Thread> entry : threadMap.entrySet()){
			if(entry.getValue().getState() != Thread.State.TERMINATED){
				return false;
			}
		}
		return true;
	}

	Master(){}

	public Master(Worker worker , int countWorker) {
		worker.setResultMap(resultMap);
		worker.setTaskQueue(taskQueue);
		for ( int i = 0 ; i < countWorker ; i++){
			threadMap.put(Integer.toString(i), new Thread(worker,Integer.toString(i)));
		}
	}

	//提交任务
	public void submit(Task task) {
		taskQueue.add(task);
	}

	//返回子任务结果集
	public Map<String,Object> getResultMap() {
		return resultMap;
	}

	//开始运行所有的worker进程
	public void execute(){
		for(Map.Entry<String, Thread> entry : threadMap.entrySet()) {
			entry.getValue().start();
		}
	}
}
