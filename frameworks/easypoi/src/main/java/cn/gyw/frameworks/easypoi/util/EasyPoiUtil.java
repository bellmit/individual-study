package cn.gyw.frameworks.easypoi.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.List;
import java.util.Map;

public class EasyPoiUtil {

    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名。创建表头
     *
     * @param list           导出的实体类
     * @param title          表头名称
     * @param sheetName      sheet表名
     * @param pojoClass      映射的实体类
     * @param isCreateHeader 是否创建表头
     * @param fileName
     * @return
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   boolean isCreateHeader) {
        ExportParams exportParams = new ExportParams(title, sheetName);

        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, exportParams);
    }

    /**
     * 功能描述：默认导出方法
     *
     * @param list         导出的实体集合
     * @param fileName     导出的文件名
     * @param pojoClass    pojo实体
     * @param exportParams ExportParams封装实体
     * @return
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            writeToLocalFile(fileName, workbook);
        }
    }

    /**
     * 功能描述：默认导出方法
     *
     * @param list     导出的实体集合
     * @param fileName 导出的文件名
     * @return
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            writeToLocalFile(fileName, workbook);
        }
    }

    private static void writeToLocalFile(String fileName, Workbook workbook) {
        try (OutputStream out = new FileOutputStream(fileName)) {
            workbook.write(out);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> List<T> importExcel(String file, Class<T> pojoClass) {
        return importExcel(file, 0, 1, pojoClass);
    }

    /**
     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
     *
     * @param file       导入文件
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(String file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try (InputStream in = new FileInputStream(new File(file))) {
            list = ExcelImportUtil.importExcel(in, pojoClass, params);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

}
