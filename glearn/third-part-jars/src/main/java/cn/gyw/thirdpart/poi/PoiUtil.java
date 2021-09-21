package cn.gyw.thirdpart.poi;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Poi Excel 工具类
 * @author guanyw
 *
 */
public class PoiUtil {

    private final static Logger log = LoggerFactory.getLogger(PoiUtil.class);

    private static final SimpleDateFormat dateFormatEN = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 读取 Excel文件流 封装 List
     * @param is
     * @param clazz
     * @param propMapper
     * @param <T>
     * @return
     */
    public static <T> List<T> readExcelData(InputStream is, Class<T> clazz, Map<String, String> propMapper) {
        List<T> beans = new ArrayList<>();
        try {
            Workbook wbs = WorkbookFactory.create(is);
            Sheet sheet0 = wbs.getSheetAt(0);
            int rowCount = sheet0.getLastRowNum();

            if (rowCount > 0) {
                Row row0 = sheet0.getRow(0);
                Map<Integer, String> colMapper = createColMapper(row0, propMapper);
                for (int j = 1; j <= rowCount; j++) {
                    Row row = sheet0.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    T bean = rowToBean(row, colMapper, clazz);
                    if (bean != null) {
                        beans.add(bean);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }

    private static <T> T rowToBean(Row row, Map<Integer, String> colMapper, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            for (int i : colMapper.keySet()) {
                Cell cell = row.getCell(i);
                Object value = getCellValue(cell);
                BeanUtils.setProperty(bean, colMapper.get(i), value);
            }
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getCellValue(Cell cell) {
        try {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    return cell.getRichStringCellValue().getString();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return dateFormatEN.format(cell.getDateCellValue());
                    }

                    return cell.getNumericCellValue();
                case BOOLEAN:
                    return cell.getBooleanCellValue();
                case BLANK:
                    return "";
                case ERROR:
                    return "";
                default:
                    return  "";
            }
        } catch (Exception e) {
            log.error("The error may involve in [cell type judge]");
        }
        return "";
    }

    /**
     * 分析第一行，确定对应关系
     *
     * @param row0       第一行，  保存对应列的意义
     * @param propMapper 列的意义到bean属性的映射
     * @return
     */
    private static Map<Integer, String> createColMapper(Row row0, Map<String, String> propMapper) {
        Map<Integer, String> colMapper = new HashMap<Integer, String>();
        int colCountR0 = row0.getLastCellNum();
        for (int i = 0; i < colCountR0; i++) {
            Cell cell = row0.getCell(i);
            if (propMapper.containsKey(cell.getStringCellValue())) {
                colMapper.put(cell.getColumnIndex(), propMapper.get(cell.getStringCellValue()));
            }
        }
        return colMapper;
    }

}
