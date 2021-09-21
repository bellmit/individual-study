package cn.gyw.frameworks.flowable.leave;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
public class LeaveController {
	
	private final Logger log = LoggerFactory.getLogger(LeaveController.class);

	@Autowired
	private LeaveService leaveService;
	
	@PostMapping(path = "/process")
	public LeaveResult startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation) {
		log.info("start process :" + startProcessRepresentation.getAssignee());
		String processId = leaveService.startProcess(startProcessRepresentation.getAssignee());
		LeaveResult result = new LeaveResult();
		result.setProcessId(processId);
		return result;
	}
	
	@GetMapping(path = "/tasks")
	public List<String> queryTasks(String assignee) {
		log.info("tasks assignee :" + assignee);
		return leaveService.getTasks(assignee);
	}
	
	@GetMapping(path = "/task/assigning")
	public boolean assigningTask(String targetUser) {
		leaveService.claim(targetUser);
		return true;
	}
	
	@GetMapping(path = "/task/complete")
	public boolean completeCurrentTask() {
		leaveService.completeTask();
		return true;
	}
	
	@GetMapping(path = "/task/his")
	public void queryHistory(String processId) {
		leaveService.listHistory(processId);
	}
	
	static class StartProcessRepresentation {
		private String assignee;
		public String getAssignee() {
			return assignee;
		}
		public void setAssignee(String assignee) {
			this.assignee = assignee;
		}
	}
	
	static class LeaveResult {
		private String processId;
		private String assignee;
		public String getProcessId() {
			return processId;
		}
		public void setProcessId(String processId) {
			this.processId = processId;
		}
		public String getAssignee() {
			return assignee;
		}
		public void setAssignee(String assignee) {
			this.assignee = assignee;
		}
	}
}
