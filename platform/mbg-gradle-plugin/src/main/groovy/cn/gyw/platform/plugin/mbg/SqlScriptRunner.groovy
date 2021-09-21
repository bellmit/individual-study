package cn.gyw.platform.plugin.mbg

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

import org.gradle.api.GradleException
import org.mybatis.generator.internal.util.StringUtility

import com.alibaba.druid.pool.DruidDataSource

class SqlScriptRunner {

	private String url
	private String username
	private String password
	private String sqlScript

	SqlScriptRunner(String sqlScript, String url, String username, String password) {
		this.url = url
		this.username = username
		this.password = password
		this.sqlScript = sqlScript
	}

	def getAllTableName() {
		DruidDataSource dataSource
		Connection con
		ResultSet rs
		Set tableNames = []
		try {
			dataSource = new DruidDataSource()
			dataSource.setUrl(url);
			dataSource.setUsername(username)
			dataSource.setPassword(password)
			con = dataSource.getConnection()

			def meta = con.getMetaData();
			rs = meta.getTables(null, null, '%', null)
			while (rs.next()) {
				String tableName = rs.getString(3)
				tableNames.add(tableName)
			}
			return tableNames
		} catch (Exception e) {
			throw new GradleException(e.getMessage());
		} finally {
			if (rs != null) {
				rs.close()
			}
			if (dataSource != null) {
				dataSource.close()
			}
		}
	}

	void execSqlScript() {
		if (sqlScript == null) {
			println("[sqlScript] is null, skip run custom sql")
			return;
		}
		DruidDataSource dataSource
		Connection con
		try {
			dataSource = new DruidDataSource()
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			con = dataSource.getConnection()
			println("开始执行SQL操作.")
			con.setAutoCommit(false)
			def execSql
			Statement statement = con.createStatement();
			BufferedReader br = getScriptReader()
			while ((execSql = readStatement(br)) != null) {
				statement.executeUpdate(execSql)
			}
			br.close()
			if (statement != null)
				statement.close()
			con.commit()
		} catch (Exception e) {
			if (con != null)
				con.rollback()
			throw new GradleException(e.getMessage());
		} finally {
			if (dataSource != null)
				dataSource.close()
		}
	}

	private String readStatement(BufferedReader br) throws IOException {
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("--")) {
				continue;
			}

			if (!StringUtility.stringHasValue(line)) {
				continue;
			}

			if (line.endsWith(";")) {
				sb.append(' ');
				sb.append(line.substring(0, line.length() - 1));
				break;
			} else {
				sb.append(' ');
				sb.append(line);
			}
		}
		String s = sb.toString().trim();
		return s.length() > 0 ? s : null;
	}


	private BufferedReader getScriptReader() throws GradleException, FileNotFoundException {
		BufferedReader answer;
		if (sqlScript.startsWith("classpath:")) {
			String resource = sqlScript.substring("classpath:".length());
			InputStream is =
					Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if (is == null) {
				throw new GradleException("SQL script file does not exist: " + resource);
			}
			answer = new BufferedReader(new InputStreamReader(is));
		} else {
			File file = new File(sqlScript);
			if (!file.exists()) {
				throw new GradleException("SQL script file does not exist");
			}
			answer = new BufferedReader(new FileReader(file));
		}
		return answer;
	}
}
