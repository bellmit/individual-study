package cn.gyw.platform.plugin.mbg

import cn.gyw.platform.plugin.mbg.engine.FreemarkerTemplateEngine
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.config.xml.ConfigurationParser
import org.mybatis.generator.internal.DefaultShellCallback
import org.mybatis.generator.internal.util.StringUtility

import cn.gyw.platform.plugin.mbg.config.ConfigBuilder

class MbgTask extends DefaultTask {

	ConfigBuilder configBuilder = new ConfigBuilder()

	@TaskAction
	void generator() {
		println "Start mbg for project [$project.name]"
		if (project.mbg.skip) {
			println ("MyBatis generator is skipped.")
			return
		}
		// 验证参数
		validationParam(project)
		// 执行数据库脚本
		runSqlScript(project.mbg.sqlScript, project.mbg.jdbc.url, project.mbg.jdbc.username, project.mbg.jdbc.password,
				project.mbg.allTable)
		// tableNames 变量处理
		setTableNamesValues(project)
		// contexts 变量处理
		setContextsValues(project)
		// MBG 生成entity、mapper、xml 文件
		configBuilder.tables.each { entry ->
			doMybatisGenerate(entry)
		}
		// 模板引擎生成通用的controller、service 代码文件
		def templateEngine = new FreemarkerTemplateEngine()
		templateEngine.init(configBuilder).mkdirs().batchOutput()
	}

	synchronized void doMybatisGenerate(Map.Entry<String, String> entry) {
		println "MBG for table >>${entry.getKey()}"
		List<String> warnings = new ArrayList<String>()
		try {
			def extra = new Properties()
			extra.putAll(configBuilder.xmlParams)
			extra.setProperty("xml.tableName", entry.getKey())
			extra.setProperty("xml.objectName", entry.getValue())
			extra.setProperty("xml.mapperName", entry.getValue() + extra.getProperty("xml.mapperSuffix", "Mapper"))
			def config = new ConfigurationParser(extra, warnings)
					.parseConfiguration(configBuilder.generatorFile)
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
					new DefaultShellCallback(configBuilder.overwrite), warnings)
			myBatisGenerator.generate(new GradleProgressCallback(configBuilder.consoleable),
					configBuilder.contextsToRun, configBuilder.fullyQualifiedTables)
		} catch (Exception e) {
			e.printStackTrace()
			throw new GradleException(e.getMessage(), e)
		}
		for (String error : warnings) {
			println "MBG warnings :$error";
		}
	}

	/**
	 * 设置tableNames的额外信息
	 * @param fullyqualifiedTables 参数 mbg.tableNames
	 */
	private void setTableNamesValues(Project project) {
		Set<String> fullyQualifiedTables = []
		if (StringUtility.stringHasValue(project.mbg.tableNames)) {
			StringTokenizer st = new StringTokenizer(project.mbg.tableNames, ",");
			while (st.hasMoreTokens()) {
				String s = st.nextToken().trim();
				if (s.length() > 0) {
					fullyQualifiedTables.add(s);
				}
			}
		}
		configBuilder.fullyQualifiedTables = fullyQualifiedTables
	}

	/**
	 * 设置contexts的额外信息
	 * @param contextsToRun 参数 mbg.contexts
	 */
	private void setContextsValues(Project project) {
		Set<String> contextsToRun = []
		if (StringUtility.stringHasValue(project.mbg.contexts)) {
			StringTokenizer st = new StringTokenizer(project.mbg.contexts, ",");
			while (st.hasMoreTokens()) {
				String s = st.nextToken().trim();
				if (s.length() > 0) {
					contextsToRun.add(s);
				}
			}
		}
		configBuilder.contextsToRun = contextsToRun
	}

	/**
	 * 执行数据库脚本
	 * @param sql
	 * @param jdbcUrl
	 * @param username
	 * @param password
	 * @param isAll 是否查询所有表名
	 */
	void runSqlScript(String sql, String jdbcUrl, String username, String password, boolean isAll) {
		//sqlScript callback
		def sqlScriptRunner = new SqlScriptRunner(sql, jdbcUrl, username, password)
		sqlScriptRunner.execSqlScript()
		def tables = [:]
		if (isAll) {
			sqlScriptRunner.getAllTableName().each { key ->
				tables[key] = tableNameToBigHump(key)
			}
		} else { // 否，使用设置的表名填充
			tables[configBuilder.xmlParams['xml.tableName']] = configBuilder.xmlParams['xml.objectName']
		}
		configBuilder.tables = tables
		// 需要处理的模块
		configBuilder.moduleList = tables.values()
		println "Need to mbg tables :$tables"
	}

	/**
	 * 基础验证并且设置内容到xmlParam中去
	 */
	private void validationParam(Project project) {
		configBuilder.javaProjectDir = project.file(project.mbg.xml.javaProject)
		configBuilder.resourcesProjectDir = project.file(project.mbg.xml.resourcesProject)
		configBuilder.generatorFile = project.file(project.mbg.generatorFile) // 设置generatorConfig.xml的位置
		if (!configBuilder.generatorFile.exists()) {
			println("generatorConfig.xml not exists:${configBuilder.generatorFile.getAbsolutePath()}");
			throw new FileNotFoundException(configBuilder.generatorFile.getAbsolutePath())
		}
		if (!configBuilder.javaProjectDir.exists()) {
			println("javaProject 目录不存在:${configBuilder.javaProjectDir.getAbsolutePath()}");
			throw new FileNotFoundException(configBuilder.javaProjectDir.getAbsolutePath())
		}
		if (!configBuilder.resourcesProjectDir.exists()) {
			println("resourcesProject 目录不存在:${configBuilder.resourcesProjectDir.getAbsolutePath()}");
			throw new FileNotFoundException(configBuilder.resourcesProjectDir.getAbsolutePath())
		}

		def errorMsg;
		//driver
		if (!StringUtility.stringHasValue(project.mbg.jdbc.driver)) {
			errorMsg = "变量mbg.jdbc.driver<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		//url
		if (!StringUtility.stringHasValue(project.mbg.jdbc.url)) {
			errorMsg = "变量mbg.jdbc.url<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		//username
		if (!StringUtility.stringHasValue(project.mbg.jdbc.username)) {
			errorMsg = "变量mbg.jdbc.username<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		//password
		if (!StringUtility.stringHasValue(project.mbg.jdbc.password)) {
			errorMsg = "变量mbg.jdbc.password<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		Class clazzPlugin = project.mbg.xml.mapperPlugin
		if (clazzPlugin == null) {
			errorMsg = "变量mbg.xml.mapperPlugin<Class<? extends PluginAdapter>>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		if (!StringUtility.stringHasValue(project.mbg.xml.mapperMapper)) {
			errorMsg = "变量mbg.xml.mapperMapper<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		if (!StringUtility.stringHasValue(project.mbg.xml.mapperPackage)) {
			errorMsg = "变量mbg.xml.mapperPackage<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		if (!StringUtility.stringHasValue(project.mbg.xml.modelPackage)) {
			errorMsg = "变量mbg.xml.modelPackage<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		if (!StringUtility.stringHasValue(project.mbg.xml.xmlPackage)) {
			errorMsg = "变量mbg.xml.xmlPackage<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		if (!project.mbg.allTable && !StringUtility.stringHasValue(project.mbg.xml.tableName)) {
			errorMsg = "变量mbg.xml.tableName<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}
		if (!project.mbg.allTable && !StringUtility.stringHasValue(project.mbg.xml.objectName)) {
			errorMsg = "变量mbg.xml.objectName<String>未设置,请设置后重试."
			println(errorMsg)
			throw new GradleException(errorMsg)
		}

		configBuilder.consoleable = project.mbg.consoleable
		configBuilder.overwrite = project.mbg.overwrite
		// 参数赋值
		configBuilder.xmlParams["jdbc.driver"] = project.mbg.jdbc.driver
		configBuilder.xmlParams["jdbc.url"] = project.mbg.jdbc.url
		configBuilder.xmlParams["jdbc.username"] = project.mbg.jdbc.username
		configBuilder.xmlParams["jdbc.password"] = project.mbg.jdbc.password
		configBuilder.xmlParams["xml.javaProject"] = project.mbg.xml.javaProject
		configBuilder.xmlParams["xml.resourcesProject"] = project.mbg.xml.resourcesProject
		configBuilder.xmlParams["xml.mapperPlugin"] = clazzPlugin.getName()
		configBuilder.xmlParams["xml.mapperMapper"] = project.mbg.xml.mapperMapper
		configBuilder.xmlParams["xml.mapperPackage"] = project.mbg.xml.mapperPackage
		configBuilder.xmlParams["xml.modelPackage"] = project.mbg.xml.modelPackage
		configBuilder.xmlParams["xml.xmlPackage"] = project.mbg.xml.xmlPackage
		if (StringUtility.stringHasValue(project.mbg.xml.tableName)) {
			configBuilder.xmlParams["xml.tableName"] = project.mbg.xml.tableName
		}
		if (StringUtility.stringHasValue(project.mbg.xml.objectName)) {
			configBuilder.xmlParams["xml.objectName"] = project.mbg.xml.objectName
			configBuilder.xmlParams["xml.mapperName"] = project.mbg.xml.objectName + project.mbg.xml.mapperSuffix
		}
		// ftl模板参数
		configBuilder.entityPackage = project.mbg.ftl.entityPackage
		configBuilder.entityDtoPackage = project.mbg.ftl.entityDtoPackage
		configBuilder.restControllerStyle = project.mbg.ftl.restControllerStyle
		configBuilder.controllerPackage = project.mbg.ftl.controllerPackage
		configBuilder.servicePackage = project.mbg.ftl.servicePackage
		configBuilder.superControllerClass = project.mbg.ftl.superControllerClass
		configBuilder.superServiceClass = project.mbg.ftl.superServiceClass

		for (def param : configBuilder.xmlParams) {
			println "env param:[${param.key}:${param.value}]"
		}
	}

	/***
	 * 下划线命名转为驼峰命名
	 *
	 * @param para
	 *        下划线命名的字符串
	 */
	String tableNameToBigHump(String name) {
		if (name == null) {
			return ''
		}
		StringBuilder sb = new StringBuilder()
		String[] strs = name.toLowerCase().split("_")
		for (String s : strs) {
			sb.append(firstToUpper(s))
		}
		return sb.toString();
	}

	String firstToUpper(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
