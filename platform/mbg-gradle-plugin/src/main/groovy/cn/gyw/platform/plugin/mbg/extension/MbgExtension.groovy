package cn.gyw.platform.plugin.mbg.extension

class MbgExtension {

	/**
	 * 是否覆盖已经生成的xml或者代码<br>
	 * 如果指定了该参数，如果生成的java文件存在已经同名的文件，新生成的文件会覆盖原有的文件。<br>
	 * 默认为 true
	 */
	boolean overwrite = true

	/**
	 * mbg的配置文件<br>
	 * 默认名称为 generatorConfig.xml
	 */
	def generatorFile = "generatorConfig.xml"

	/**
	 * 要在生成代码之前运行的 SQL 脚本文件的位置。 如果空，不会执行任何脚本。<br>
	 * 如果不是空，jdbcDriver, jdbcURL 参数必须提供。 另外如果连接数据库需要认证也需要提供 jdbcUserId 和 jdbcPassword 参数。<br>
	 * 值可以使一个文件系统的绝对路径或者是一个使用"classpath:"开头放在构建的类路径下的路径。
	 */
	String sqlScript = null

	/**
	 * 如果指定该参数，执行过程会输出到控制台。<br>
	 * 默认为 false
	 */
	boolean consoleable = false

	/**
	 * 是否跳过生成代码的mbg的任务,若为true则中断任务.<br>
	 * 默认值为 false
	 */
	boolean skip = false

	/**
	 * 如果指定了该参数，逗号隔开的这些context会被执行。<br>
	 * 这些指定的context必须和配置文件中 <context> 元素的 id 属性一致。<br>
	 * 只有指定的这些contextid会被激活执行。如果没有指定该参数，所有的context都会被激活执行。<br>
	 */
	String contexts = null

	/**
	 * 如果指定了该参数，逗号隔开的这个表会被运行， 这些表名必须和 <table> 配置中的表面完全一致。<br>
	 * 只有指定的这些表会被执行。 如果没有指定该参数，所有的表都会被执行。 按如下方式指定表明:<br>
	 * <p>table</p>
	 * <p>schema.table</p>
	 * <p>catalog..table</p>
	 */
	String tableNames = null
	
	/**
	 * 是否为当前库里所有表code generation
	 */
	boolean allTable = false
}
