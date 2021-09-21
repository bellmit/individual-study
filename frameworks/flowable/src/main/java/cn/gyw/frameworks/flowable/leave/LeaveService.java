package cn.gyw.frameworks.flowable.leave;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyw.frameworks.flowable.leave.model.Person;

@Service
public class LeaveService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;
	
	private List<Person> datas = new ArrayList<>();

	public String startProcess(String assignee) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("assignee", assignee);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
		System.out.println("process instance:" + processInstance);
		return processInstance.getId();
	}
	
	public List<String> getTasks(String assignee) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
		System.out.println(">>> tasks size :" + tasks.size());
		List<String> list = new ArrayList<>();
		tasks.forEach(item -> {
			list.add(item.getName());
		});
		return list;
	}
	
	// 声称任务
	public void claim(String targetUser) {
		Task task = taskService.createTaskQuery().active().singleResult();
		System.out.println("task :" + task);
		taskService.claim(task.getId(), targetUser);
	}
	
	public void completeTask() {
		Task task = taskService.createTaskQuery().active().singleResult();
		System.out.println("task :" + task);
		Map<String, Object> variables = new HashMap<>();
		variables.put("approved", 4);
		taskService.complete(task.getId(), variables);
	}
	
	public void listHistory(String processId) {
		HistoricProcessInstance historicProcessInstance = 
		historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
		System.out.println("historicProcessInstance :" + historicProcessInstance);
	}

	public void createDemoUsers() {
		datas.add(new Person("jbarrez", "Joram", "Barrez", new Date()));
		datas.add(new Person("trademakers", "Tijs", "Rademakers", new Date()));
	}
}
