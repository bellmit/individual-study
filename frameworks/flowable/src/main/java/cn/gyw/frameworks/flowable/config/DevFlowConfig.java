package cn.gyw.frameworks.flowable.config;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import cn.gyw.frameworks.flowable.config.condition.DevFlowableCondition;

@Configuration
@Conditional(DevFlowableCondition.class)
public class DevFlowConfig {

	@Value("#{systemProperties['h2.server.tcp.port']?:9092}")
	private String h2TCPPort;

	@Value("#{systemProperties['h2.web.server.web.port']?:8082}")
	private String h2WebPort;

	@Bean(name = "h2Server", initMethod = "start", destroyMethod = "stop")
	@DependsOn(value = "h2WebServer")
	public Server produceH2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TCPPort);
	}

	@Bean(name = "h2WebServer", initMethod = "start", destroyMethod = "stop")
	public Server produceH2WebServer() throws SQLException {
		return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2WebPort);
	}

	@Bean
	@DependsOn(value = "h2Server")
	public DataSource driverDataSource() {
		SimpleDriverDataSource driverDataSource = new SimpleDriverDataSource();
		driverDataSource.setDriverClass(org.h2.Driver.class);
		driverDataSource.setUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=1000");
		driverDataSource.setUsername("sa");
		driverDataSource.setPassword("");
		return driverDataSource;
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(driverDataSource());
	}

	@Bean
	public SpringProcessEngineConfiguration springProcessEngineConfiguration() throws IOException {
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:/flows/**/*.bpmn,/flows/**/*.bpmn20.xml");
		SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
		springProcessEngineConfiguration.setDataSource(driverDataSource());
		springProcessEngineConfiguration.setTransactionManager(dataSourceTransactionManager());
		springProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
		springProcessEngineConfiguration.setJpaHandleTransaction(true);
		springProcessEngineConfiguration.setJpaCloseEntityManager(true);
		springProcessEngineConfiguration.setAsyncExecutorActivate(true);
		springProcessEngineConfiguration.setHistory("none");
		springProcessEngineConfiguration.setDeploymentResources(resources);
		return springProcessEngineConfiguration;
	}

	@Bean
	public ProcessEngineFactoryBean processEngineFactoryBean() throws IOException {
		ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
		return processEngineFactoryBean;
	}

	@Bean
	public RepositoryService repositoryService(ProcessEngineFactoryBean pefb) throws Exception {
		return pefb.getObject().getRepositoryService();
	}

	@Bean
	public RuntimeService runtimeService(ProcessEngineFactoryBean pefb) throws Exception {
		return pefb.getObject().getRuntimeService();
	}

	@Bean
	public HistoryService historyService(ProcessEngineFactoryBean pefb) throws Exception {
		return pefb.getObject().getHistoryService();
	}

	@Bean
	public ManagementService managementService(ProcessEngineFactoryBean pefb) throws Exception {
		return pefb.getObject().getManagementService();
	}

	@Bean
	public IdentityService identityService(ProcessEngineFactoryBean pefb) throws Exception {
		return pefb.getObject().getIdentityService();
	}

	@Bean
	public FormService formService(ProcessEngineFactoryBean pefb) throws Exception {
		return pefb.getObject().getFormService();
	}

	@Bean
	public TaskService taskService(ProcessEngineFactoryBean pefb) throws Exception {
		return pefb.getObject().getTaskService();
	}
}
