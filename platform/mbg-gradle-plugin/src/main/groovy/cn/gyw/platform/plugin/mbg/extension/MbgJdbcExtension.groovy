package cn.gyw.platform.plugin.mbg.extension

class MbgJdbcExtension {

	/**
	 * jdbc的驱动类<br>
	 * 默认为null,需要设置,否则执行报错
	 */
	String driver = null

	/**
	 * jdbc的数据库url<br>
	 * 默认为null,需要设置,否则执行报错
	 */
	String url = null

	/**
	 * jdbc的数据库用户名<br>
	 */
	String username

	/**
	 * jdbc的数据库密码<br>
	 * 默认为null,需要设置,否则执行报错
	 */
	String password = null
}
