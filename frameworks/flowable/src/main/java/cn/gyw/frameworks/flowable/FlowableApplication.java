package cn.gyw.frameworks.flowable;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cn.gyw.frameworks.flowable.leave.LeaveService;

@SpringBootApplication
public class FlowableApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowableApplication.class, args);
	}

	/**
	 * @param repositoryService
	 * @param runtimeService
	 * @param taskService
	 * @return
	 */
	@Bean
	public CommandLineRunner init(final RepositoryService repositoryService, final RuntimeService runtimeService,
			final TaskService taskService, final LeaveService leaveService) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				System.out.println("Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
				System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
				
				leaveService.createDemoUsers();
			}
		};
	}

}
