package cn.gyw.thirdpart.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyw.thirdpart.utils.ClassUtil;
import cn.gyw.thirdpart.utils.CsvUtil;

/**
 * Pivot Table （透视表）生成测试
 *
 * HSSF － 提供读写Microsoft Excel XLS格式档案的功能。
 * XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
 * HWPF － 提供读写Microsoft Word DOC97格式档案的功能。
 * XWPF － 提供读写Microsoft Word DOC2003格式档案的功能。
 * HSLF － 提供读写Microsoft PowerPoint格式档案的功能。
 * HDGF － 提供读Microsoft Visio格式档案的功能。
 * HPBF － 提供读Microsoft Publisher格式档案的功能。
 * HSMF － 提供读Microsoft Outlook格式档案的功能。
 *
 */
public class PivotTableTest {

	private static final Logger logger = LoggerFactory.getLogger(PivotTableTest.class);

	private final static String TMP_DIR = "D:\\Tmp\\";
	private final static String SAVE_FILE_NAME = "Pivot_table";
	private final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private final static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	private final static String SOURCE_SHEET_NAME = "sheet1";
    private static final String TEST_XLS_FILE_NAME = "xls_data.xls";
    private static final String TEST_XLSX_FILE_NAME = "xlsx_data.xlsx";

    private PoiExcelReader poiExcelReader = new PoiExcelReader();

	// 标题
    private List<String> colNames;
	// 字段-索引映射关系
	private Map<String, Integer> columnIndexMap = new HashMap<>();
	private String suffix = ".xlsx";

    /**
     * Poi 3.16 生成透视表
     *
     * 问题：
     * 1）新sheet 页生成透视表问题
     * 参考：https://stackoverflow.com/questions/37401570/apache-poi-xssfcreating-pivot-table-in-new-sheetjava
     */
    @Test
    public void shouldCreateInSameSheet() {
        Workbook workbook = createSourceSheet("datas.csv");
        createPivotTable(workbook, SOURCE_SHEET_NAME);
        saveFileFromWorkbook(workbook);
    }

    /**
     * Poi 4.0.1
     */
	@Test
	public void shouldCreateInOtherSheet() {
        Workbook workbook = createSourceSheet("datas.csv");
        createPivotTable(workbook);
        saveFileFromWorkbook(workbook);
	}

    /**
     * 创建 工作簿
     * @param fileName
     * @return
     */
	private Workbook createSourceSheet(String fileName) {
        // 源数据
        File csvFile = new File(ClassUtil.getFilePath(PivotTableTest.class, fileName));

        Workbook wb = new XSSFWorkbook();
        CreationHelper ch = wb.getCreationHelper();
        String safeName = WorkbookUtil.createSafeSheetName(SOURCE_SHEET_NAME);
        XSSFSheet sheet = (XSSFSheet) wb.createSheet(safeName);

        int rowNum = 0;
        try (InputStream in = new FileInputStream(csvFile)) {
            CsvUtil csv = new CsvUtil(true, ',', in);
            if (csv.hasNext()) {
                colNames = new ArrayList<String>(csv.next());
                Row row = sheet.createRow((short) 0);
                for (int i = 0; i < colNames.size(); i++) {
                    String name = colNames.get(i);
                    row.createCell(i).setCellValue(name);
                    // 维护映射关系
                    columnIndexMap.put(name, i);
                }
            }

            while (csv.hasNext()) {
                List<String> fields = csv.next();
                rowNum++;
                Row row = sheet.createRow((short) rowNum);
                for (int i = 0; i < fields.size(); i++) {
                    /*
                     * Attempt to set as double. If that fails, set as text.
                     */
                    try {
                        double value = Double.parseDouble(fields.get(i));
                        row.createCell(i).setCellValue(value);
                    } catch (NumberFormatException ex) {
                        String value = fields.get(i);
                        row.createCell(i).setCellValue(value);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 当前sheet页，创建透视表
     * @param workbook
     * @param sourceSheetName
     */
    private void createPivotTable(Workbook workbook, String sourceSheetName) {
        XSSFSheet sourceSheet = (XSSFSheet) workbook.getSheet(sourceSheetName);
        // create pivot table
        // 第一行
        int firstRow = sourceSheet.getFirstRowNum();
        // 最后一行
        int lastRow = sourceSheet.getLastRowNum();
        // 第一列
        int firstCol = sourceSheet.getRow(0).getFirstCellNum();
        // 最后一列
        int lastCol = sourceSheet.getRow(0).getLastCellNum();

        // The cell references specify the top left and the bottom right of the table data.
        CellReference topLeft = new CellReference(sourceSheetName, firstRow, firstCol, false, false);
        CellReference botRight = new CellReference(sourceSheetName, lastRow, lastCol - 1, false, false);

        // And the area reference which marks out the data table.
        AreaReference aref = new AreaReference(topLeft, botRight, null);
        System.out.println(">>" + aref.formatAsString());

        // We insert the pivot table at this location;
        // a couple of rows offset from the top of the sheet and to the right of the table.
        CellReference pos = new CellReference(lastRow + 2, 0);
//			CellReference pos = new CellReference("Sheet2", 0 , 0 , false, false);

//			XSSFSheet pivotSheet = (XSSFSheet) wb.createSheet("Pivot");
        // Finally we create the pivot table from the area reference and the cell position.
//			XSSFPivotTable pivotTable = sheet2.createPivotTable(aref, pos);
//			CellReference position = new CellReference(pivotSheet.getSheetName(), 0, 0, false, false);
        XSSFPivotTable pivotTable = sourceSheet.createPivotTable(aref, pos, sourceSheet);

        // refresh

        // Configure the pivot table to add Row Labels. Use the summarizing functions as needed for your application.
        // In this example, we are grouping data by yearID and teamID. These are columns 0 and 1 respectively. And for the summary, we choose to sum the salaries over these columns.
        // 报表筛选，筛选功能
//        pivotTable.addReportFilter(0);
        // 行标签（指定分组字段，例如时间）
        pivotTable.addRowLabel(columnIndexMap.get("时间"));
        pivotTable.addRowLabel(columnIndexMap.get("销售人员"));
//        pivotTable.addRowLabel(1);
        // 列标签（指定列分组条件，比如地区）
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, columnIndexMap.get("销售数量"), "销售总数");
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, columnIndexMap.get("销售金额（元）"), "销售总金额（元）");
        // 数值
//        pivotTable.addDataColumn(columnIndexMap.get("销售人员"), false);

    }

    private void createPivotTable(Workbook workbook) {
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
        // create pivot table
        // 第一行
        int firstRow = sheet.getFirstRowNum();
        // 最后一行
        int lastRow = sheet.getLastRowNum();
        // 第一列
        int firstCol = sheet.getRow(0).getFirstCellNum();
        // 最后一列
        int lastCol = sheet.getRow(0).getLastCellNum();

        // The cell references specify the top left and the bottom right of the table data.
        CellReference topLeft = new CellReference(firstRow, firstCol);
        CellReference botRight = new CellReference(lastRow, lastCol - 1);

        // And the area reference which marks out the data table.
        AreaReference aref = new AreaReference(topLeft, botRight, null);

        // success
//        aref = new AreaReference("sheet1!A1:D5", SpreadsheetVersion.EXCEL2007);

        XSSFSheet povitTableSheet = (XSSFSheet) workbook.createSheet("PivotTable");
        // We insert the pivot table at this location;
        // a couple of rows offset from the top of the sheet and to the right of the table.
        CellReference pos = new CellReference(0, 0);
        // 成功
//        pos = new CellReference("A1");
        // 指定父类sheet名，生成失败
//        pos = new CellReference("PivotTable", 0 , 0 , false, false);

        // Finally we create the pivot table from the area reference and the cell position.
        // 失败
        XSSFPivotTable pivotTable = povitTableSheet.createPivotTable(aref, pos, sheet);

        // refresh

        // Configure the pivot table to add Row Labels. Use the summarizing functions as needed for your application.
        // In this example, we are grouping data by yearID and teamID. These are columns 0 and 1 respectively. And for the summary, we choose to sum the salaries over these columns.
        pivotTable.addRowLabel(columnIndexMap.get("时间"));
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, columnIndexMap.get("销售数量"), "销售总数");
    }

    private void saveFileFromWorkbook(Workbook wb) {
        saveFileFromWorkbook(wb, SAVE_FILE_NAME);
    }

    /**
     * 保存文件
     * @param wb
     */
    private void saveFileFromWorkbook(Workbook wb, String outFileName) {
        // 保存文件
        StringBuilder builder = new StringBuilder(TMP_DIR);
        builder.append(outFileName)
                .append("_")
                .append(dateTimeFormat.format(new Date()))
                .append(suffix);
        String saveFilePath = builder.toString();
        logger.info("output file :{}", saveFilePath);
        try (FileOutputStream fos = new FileOutputStream(saveFilePath)) {
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Before
    public void before() {
        currentLongMill = System.currentTimeMillis();
        logger.info("start time : {}!", timeFormat.format(new Date()));
    }

	@After
	public void after() {
        logger.info("end time : {}! cost time : {} seconds", timeFormat.format(new Date()),
                (System.currentTimeMillis() - currentLongMill) / 1000);
    }

    private long currentLongMill = 0;
}
