package cn.gyw.thirdpart.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel 读取工具类
 * 读取 Excel 数据到数组
 */
public class PoiExcelReader {

    private Logger log = LoggerFactory.getLogger(PoiExcelReader.class);

    private DecimalFormat format = new DecimalFormat("0");

    // 总行数
    private int rowNum;

    // 总列数
    private int columNum;

    // sheet数量
    private int sheetNum;

    // sheet名称;
    private String sheetName;

    // 单元格内容
    private String value;

    // 读取excel保存的内容
    private List<String[]> data;

    //最终保存的数据
    private Map<String, List<String[]>> allData;

    // 每一行内容
    private String[] rowData;

    public Workbook readExcel(String filePath, boolean isExcel07) throws IOException {
        try (FileInputStream in = new FileInputStream(filePath)) {
            if (isExcel07) {
                return new XSSFWorkbook(in);
            }
            return new HSSFWorkbook(in);
        }
    }

    /**
     * 获取全部数据
     * @param filePath
     * @return
     */
    public Map<String, List<String[]>> getAllData(String filePath) {
        log.info("文档初始化");
        long time = System.currentTimeMillis();
        if(filePath.endsWith(".xls")) {
            try {
                readXlsExcel(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (filePath.endsWith(".xlsx")) {
            try {
                readXlsxExcel(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("文档格式不正确") ;
        }
		log.info("文档读取完成  共计耗时: {} ", (System.currentTimeMillis() - time) + " 毫秒");

        return allData;
    }

    /**
     * 读取 office 2003 excel
     */
    private void readXlsExcel(String filePath) throws IOException {
		log.info("读取excel 2003版本");
        allData = new HashMap<String, List<String[]>>();
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));
        sheetNum = workbook.getNumberOfSheets();
        HSSFSheet sheet = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        if (sheetNum > 0) {
            for(int x = 0; x < sheetNum; x++) {
                data = new ArrayList<String[]>();
                sheet = workbook.getSheetAt(x);
                sheetName = sheet.getSheetName();
                if (sheet.getRow(0) != null) {
                    rowNum = sheet.getPhysicalNumberOfRows();
                    columNum = sheet.getRow(0).getLastCellNum();
                    for (int i = 0; i <= rowNum; i++) {
                        row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }
                        rowData = new String[columNum];
                        for (int j = 0; j < columNum; j++) {

                            cell = row.getCell(j);
                            if (cell == null) {
                                continue;
                            }

                            switch (cell.getCellTypeEnum()) {
                                case STRING:
                                    value = String.valueOf(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    value = String.valueOf(format.format(cell.getNumericCellValue()));
                                    break;
                                case BOOLEAN:
                                    value = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                    value = "";
                                    break;
                                default:
                                    value = cell.toString();
                            }

                            rowData[j] = value;
                        }

                        data.add(rowData);
                    }
                }
                allData.put(sheetName, data);
				log.info("单个sheet数据读取完成");
            }
        }
		log.info("excel数据读取完成");
    }

    /**
     * 读取Office 2007 excel
     */
    private void readXlsxExcel(String filePath) throws IOException {
		log.info("读取excel 2007版本");
        allData = new HashMap<String, List<String[]>>();
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(filePath));
        XSSFSheet sheet = null;
        sheetNum = xwb.getNumberOfSheets();
        XSSFRow row = null;
        XSSFCell cell = null;
        if(sheetNum > 0) {
            for(int x = 0; x < sheetNum; x++) {
                data = new ArrayList<String[]>();
                sheet = xwb.getSheetAt(x);
                sheetName = sheet.getSheetName();
                if (sheet.getRow(0) != null) {
                    rowNum = sheet.getPhysicalNumberOfRows();
                    columNum = sheet.getRow(0).getLastCellNum();
                    for (int i = 0; i <= rowNum; i++) {
                        row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }
                        rowData = new String[columNum];
                        for (int j = 0; j < columNum; j++) {
                            cell = row.getCell(j);
                            if (cell == null) {
                                continue;
                            }
                            switch (cell.getCellTypeEnum()) {
                                case STRING:
                                    value = String.valueOf(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    value = String.valueOf(format.format(cell.getNumericCellValue()));
                                    break;
                                case BOOLEAN:
                                    value = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                    value = "";
                                    break;
                                default:
                                    value = cell.toString();
                            }
                            rowData[j] = value;
                        }
                        data.add(rowData);
                    }
                }
                allData.put(sheetName, data);

				log.info("单个sheet数据读取完成");
				log.info("sheet名称: {}", sheetName);
				log.info("sheet页中的数据长度: {}", data.size());
            }
        }
		log.info("excel数据读取完成");
    }

}
