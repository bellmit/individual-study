package cn.gyw.components.autodb;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cn.gyw.components.autodb.model.ColumnDefinition;
import cn.gyw.components.autodb.model.TableDefinition;

@Component
@ConditionalOnClass(value = { JdbcTemplate.class })
public class AutoCreateTableProcessor {

	private Logger log = LoggerFactory.getLogger(AutoCreateTableProcessor.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${auto-db.schema}")
	private String dbSchema;

	@Value("${auto-db.isNeedCreate:false}")
	private boolean isNeedCreate;
	
	@Value("${auto-db.tables-json-file:ddl/tables.json}")
	private String tablesJsonFile;
	
	// parse tables.json to TableDefinition
	private List<TableDefinition> tableDefinitions;

	// create tables sql list
	private List<String> createTableSqls = new ArrayList<>();

	@PostConstruct
	public void initTables() throws URISyntaxException, IOException {
		if (!isNeedCreate) {
			log.debug("Tables is exists, do not need to create tables.");
			return;
		}
		if (readFromJson() == 1) {
			return;
		}
		buildSqls();
		doCreateTables();
	}

	private int readFromJson() throws IOException, URISyntaxException {
		URI uri = Thread.currentThread().getContextClassLoader().getResource(tablesJsonFile).toURI();
		Path ddlPath = Paths.get(uri);
		List<String> lines = Files.readAllLines(ddlPath);
		if (lines.size() < 1) {
			log.warn("There is no tables need to create, return .");
			return 1;
		}
		StringBuilder jsonBuilder = new StringBuilder();
		lines.stream().forEach(line -> {
			jsonBuilder.append(line.trim());
		});
		Gson gson = new GsonBuilder().create();
		tableDefinitions = gson.fromJson(jsonBuilder.toString(), new TypeToken<List<TableDefinition>>() {
		}.getType());
		System.out.println("=========================================================================");
		System.out.println(tableDefinitions);
		System.out.println("=========================================================================");
		return 0;
	}

	private void buildSqls() {
		tableDefinitions.stream().forEach((table) -> {
			createTableSqls.add("DROP TABLE IF EXISTS `" + table.getName() + "`");
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("create table ").append(table.getName()).append(" ( ");
			for (int i = 0, len = table.getColumns().size(); i < len; i++) {
				buildColumn(sqlBuilder, table.getColumns().get(i));
				if (i != len - 1) {
					sqlBuilder.append(",");
				}
			}
			sqlBuilder.append(" ) ENGINE=").append(table.getEngine()).append(" DEFAULT CHARSET=")
					.append(table.getEncoding());
			log.debug(">> sql :[{}]", sqlBuilder.toString());
			createTableSqls.add(sqlBuilder.toString());
		});
	}

	private void buildColumn(StringBuilder sqlBuilder, ColumnDefinition column) {
		sqlBuilder.append("`").append(column.getName()).append("` ").append(column.getType());
		if (column.getLength() > 0) {
			sqlBuilder.append("(").append(column.getLength()).append(")");
		}
		if (!StringUtils.isEmpty(column.getIdGenerator())) {
			sqlBuilder.append(" AUTO_INCREMENT ");
		}
		if (column.isPrimary()) {
			sqlBuilder.append(" PRIMARY KEY ");
		}
		if (!StringUtils.isEmpty(column.getDefaultValue())) {
			sqlBuilder.append(" DEFAULT " + column.getDefaultValue());
		}
		sqlBuilder.append(" COMMENT '").append(column.getComment()).append("'");
	}

	private void doCreateTables() {
		createTableSqls.forEach((sql) -> {
			jdbcTemplate.execute(sql);
		});
	}

	// 获取需要创建的表
	private List<String> fetchNeedCreateTables() {

		return null;
	}

	/**
	 * 获取当前SCHEMA 的所有表名
	 * 
	 * @return
	 */
	private List<String> getAllTables() {
		String showTablesSql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='" + dbSchema
				+ "';";
		List<String> tables = jdbcTemplate.queryForList(showTablesSql, String.class);
		System.out.println(tables);
		return tables;
	}
}
