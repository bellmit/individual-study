package cn.gyw.platform.plugin.mbg.config

class ConfigBuilder {

	boolean overwrite = false
	boolean consoleable = false
	
	// 源码目录
	File javaProjectDir
	// 资源目录
	File resourcesProjectDir
	// mbg 配置文件
	File generatorFile
	// mbg 配置文件所需参数
	Map<String, String> xmlParams = [:]
	// 待处理表： 库表名-Entity名
	Map<String, String> tables = [:]
	//
	Set<String> fullyQualifiedTables = []
	// contexts 变量处理
	Set<String> contextsToRun = []

	boolean restControllerStyle = false
	
	String entityPackage

	String entityDtoPackage

	String controllerPackage

	String servicePackage

	String superControllerClass = null

	String superServiceClass

	Collection<String> moduleList = []

	/**
	 * 路径配置信息
	 */
	Map<OutputFile, String> pathInfo = new HashMap<>();

}
