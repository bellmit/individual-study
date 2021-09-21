package cn.gyw.thirdpart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.gyw.thirdpart.utils.ClassUtil;
import cn.gyw.thirdpart.utils.CsvUtil;

/**
 * CSV 工具类测试
 * 
 */
public class CsvUtilTest {

	@Test
	public void shouldReadCsvFile() {
		File csvFile = new File(ClassUtil.getFilePath(CsvUtil.class, "datas.csv"));
		List<List<String>> rows = new ArrayList<>();
		try (InputStream in = new FileInputStream(csvFile);) {
			CsvUtil csv = new CsvUtil(true, ',', in);
			List<String> colNames = null;
			if (csv.hasNext())
				colNames = new ArrayList<String>(csv.next());
			while (csv.hasNext()) {
				List<String> fields = new ArrayList<String>(csv.next());
				rows.add(fields);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (List<String> row : rows) {
			System.out.println(">>" + row);
		}
	}
	
	@Test
	public void shouldReadCsvString() {
		
	}

	/**
	 * 测试数据文件位置
	 */
	@Test
	public void shouldPrintDatasCsvPath() {
		String fileName = CsvUtilTest.class.getClassLoader().getResource("datas.csv").getFile();
		System.out.println(fileName);
	}

}
