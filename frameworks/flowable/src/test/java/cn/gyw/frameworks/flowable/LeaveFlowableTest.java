package cn.gyw.frameworks.flowable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.common.api.delegate.event.FlowableEventListener;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gyw.frameworks.flowable.leave.event.MyEventListener;

public class LeaveFlowableTest {

	private Scanner scanner = new Scanner(System.in);
	private String employee;
	private Integer nrOfHolidays;
	private String description;

	@Test
	public void leaveTest() {
		
		List<FlowableEventListener> eventListeners = new ArrayList<>();
		eventListeners.add(new MyEventListener());
		
		ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
				.setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=1000").setJdbcUsername("sa").setJdbcPassword("")
				.setJdbcDriver("org.h2.Driver")
				/* DatabaseSchemaUpdate 设置策略来处理流程引擎启动和关闭时的数据库模式。
				 * false （默认值）：在创建流程引擎时检查数据库模式的版本，并在版本不匹配时引发异常。
				   true：在构建流程引擎时，将执行检查，并在必要时执行架构的更新。如果架构不存在，则会创建该架构。
				   create-drop：在创建流程引擎时创建模式，并在流程引擎关闭时删除模式。
				 */
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		// 注册事件监听器
		cfg.setEventListeners(eventListeners);
		// 发送某些类型的事件时收到通知，请使用typedEventListeners期望地图的属性。地图条目的关键是逗号分隔的事件名称（或单个事件名称）列表
//		cfg.setTypedEventListeners(typedEventListeners)

		ProcessEngine processEngine = cfg.buildProcessEngine();

		// 将流程定义部署到Flowable引擎，使用了RepositoryService
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deployment = repositoryService.createDeployment().addClasspathResource("flows/leave.bpmn").deploy();

		// 查询流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId()).singleResult();
		System.out.println("Found process definition : " + processDefinition.getName());

		// 启动流程
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Map<String, Object> variables = new HashMap<>();
		variables.put("employee", employee);
		variables.put("nrOfHolidays", nrOfHolidays);
		variables.put("description", description);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);

		// 查询任务列表
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
		System.out.println("You have " + tasks.size() + " tasks:");
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println((i + 1) + ") " + tasks.get(i).getName());
		}
		
		// 使用任务标识符，我们现在可以获得特定的流程实例变量
		System.out.println("Which task would you like to complete?");
		int taskIndex = 1;
//		taskIndex = Integer.valueOf(scanner.nextLine());
		Task task = tasks.get(taskIndex - 1);
		Map<String, Object> processVariables = taskService.getVariables(task.getId());
		System.out.println(processVariables.get("employee") + " wants " +
		processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");
		
		// 完成任务
		Integer approved = 1;
//		approved = scanner.nextInt();
		variables = new HashMap<>();
		variables.put("approved", approved);
		taskService.complete(task.getId(), variables);
		
		// 历史活动查询
		doSearchHistory(processEngine, processInstance.getId());
		
	}

	private void doSearchHistory(ProcessEngine processEngine, String processId) {
		HistoryService historyService = processEngine.getHistoryService();
		List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processId).finished()
				.orderByHistoricActivityInstanceEndTime().asc()
				.list();
		
		for (HistoricActivityInstance instance : activityInstances) {
			System.out.println(instance.getActivityId() + " took "
					+ instance.getDurationInMillis() + " milliseconds");
		}
	}
	
	
	@Before
	public void before() {
		mockData();
	}

	private void mockScanner() {
		// 模拟数据
		System.out.println("Who are you?");
		employee = scanner.nextLine();
		System.out.println("How many holidays do you want to request?");
		nrOfHolidays = Integer.valueOf(scanner.nextLine());
		System.out.println("Why do you need them?");
		description = scanner.nextLine();
	}

	private void mockData() {
		employee = "ggg";
		nrOfHolidays = 3;
		description = "test reason";
	}

	@After
	public void after() {
		scanner.close();
	}
}
