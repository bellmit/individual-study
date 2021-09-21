package cn.gyw.thirdpart.poi;


import cn.gyw.thirdpart.AbstractTest;
import cn.gyw.thirdpart.utils.ClassUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * POI 读取 Excel 文件
 *
 * @See PoiExcelReader
 */
public class PoiExcelReaderTest extends AbstractTest {

    private static final String TEST_XLS_FILE_NAME = "xls_data.xls";
    private static final String TEST_XLSX_FILE_NAME = "xls-_data.xlsx";

    private PoiExcelReader poiExcelReader = new PoiExcelReader();

    @Test
    public void shouldMergeCell() throws IOException {
        Workbook workbook = poiExcelReader.readExcel(ClassUtil.getFilePath(PoiExcelReader.class, TEST_XLS_FILE_NAME), false);
        System.out.println(workbook);
    }

    /**
     * 获取数据方法 getAllData
     */
    @Test
    public void shouldPrintXlsFileAllData() {
        String filePath = this.getClass().getClassLoader().getResource(TEST_XLS_FILE_NAME).getFile();
        Map<String, List<String[]>> datas = poiExcelReader.getAllData(filePath);
        System.out.println(datas);
    }
}
