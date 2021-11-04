package cn.gyw.spring.scheduler.scheduler;

import cn.gyw.spring.scheduler.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 设置调度器
 * Created by guanyw on 2018/7/30.
 */
@EnableScheduling
@Configuration
public class MySpringTaskScheduler implements SchedulingConfigurer {

	//维护一个自增长的任务id
	private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

	//调度器
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	//维护一个CronTask和ScheduledFuture的map
	private Map<CronTask, ScheduledFuture> cronTaskScheduledFutureMap;

	//维护一个供用户查看的任务列表
	private List<Task> taskList;

	/**
	 * 通过ScheduledTaskRegistrar，即可进行调度器等配置。
	 * @param taskRegistrar
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// taskScheduler = createThreadPoolTaskScheduler();
		System.out.println("taskScheduler 初始化为：" + taskScheduler);
		taskRegistrar.setTaskScheduler(taskScheduler);
		cronTaskScheduledFutureMap = new HashMap<>();
		taskList = new ArrayList<>();
		//设置任务注册器的调度器
		// 拿到spring托管的所有任务
		List<CronTask> cronTasks = taskRegistrar.getCronTaskList();
		for (CronTask cronTask : cronTasks) {
			//手动使用线程池调度器调度这些任务，并保存每一个任务调度的scheduledFuture，在对Future的实现类scheduledFuture中可以实现对任务的取消
			ScheduledFuture scheduledFuture = taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
			//添加到维护的map中
			cronTaskScheduledFutureMap.put(cronTask, scheduledFuture);
			//拿到Runnable信息，提供给用户查看，同时用Runnable的MethodName作为每个任务的唯一标识
			ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) cronTask.getRunnable();

			taskList.add(new Task(ATOMIC_INTEGER.getAndIncrement(),
					scheduledMethodRunnable.getMethod().toGenericString(),
					cronTask.getExpression(),
					Task.State.WAITTING_NEXT,
					cronTask.getTrigger().nextExecutionTime(new SimpleTriggerContext()))
			);
		}
		//设置spring托管的任务列表为空
		taskRegistrar.setCronTasksList(null);
	}

	/**
	 * @return 返回当前的任务列表。
	 */
	public List<Task> getTaskList() {
		return taskList;
	}

	// public ThreadPoolTaskScheduler createThreadPoolTaskScheduler() {
	// 	//创建一个线程池调度器
	// 	ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
	// 	//设置线程池容量
	// 	scheduler.setPoolSize(20);
	// 	//线程名前缀
	// 	scheduler.setThreadNamePrefix("task-");
	// 	//等待时长
	// 	scheduler.setAwaitTerminationSeconds(60);
	// 	//当调度器shutdown被调用时等待当前被调度的任务完成
	// 	scheduler.setWaitForTasksToCompleteOnShutdown(true);
	// 	//设置当任务被取消的同时从当前调度器移除的策略
	// 	scheduler.setRemoveOnCancelPolicy(true);
	// 	return scheduler;
	// }

	//以传入任务的triggerName作为唯一标识编辑已经存在的任务
	public void editTask(Task task) {
		if (taskScheduler == null || cronTaskScheduledFutureMap == null) {
			return;
		}
		//对每一个当前已经参与调度的任务进行判断，取消triggerName与传入Task的triggerName相同的任务
		for (Map.Entry<CronTask, ScheduledFuture> entry : cronTaskScheduledFutureMap.entrySet()) {
			CronTask cronTask = entry.getKey();
			ScheduledFuture scheduledFuture = entry.getValue();

			if (cronTask.getRunnable() instanceof ScheduledMethodRunnable) {
				ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) cronTask.getRunnable();
				String methodName = scheduledMethodRunnable.getMethod().toGenericString();
				if (task.getTriggerName().equals(methodName) && scheduledFuture.cancel(true)) {
					switch (task.getState()) {
						case RUN:
							//当前传入的任务要求处于调度状态时，立即启动调度
							cronTaskScheduledFutureMap.put(cronTask,
									taskScheduler.schedule(scheduledMethodRunnable,
											new CronTrigger(task.getCron())));
							break;
					}
				}
			}
		}
	}
}
