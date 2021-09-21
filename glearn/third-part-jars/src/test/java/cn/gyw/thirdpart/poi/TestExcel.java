package cn.gyw.thirdpart.poi;

/**
 * 表头画斜线来表示多个展示指标
 */

import cn.gyw.thirdpart.AbstractTest;
import cn.gyw.thirdpart.utils.ClassUtil;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestExcel extends AbstractTest {

    public static final int PERCENT_WIDTH = 50;
    public static final int PERCENT_HEIGHT = 20;

    public static final float PXTOPT = 0.75f;

    private static SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static final String TEMP_XLSX_FILE_NAME = "temp.xlsx";

    private PoiExcelReader poiExcelReader = new PoiExcelReader();

    @Test
    public void shouldMergeCell() throws IOException {
        Workbook wb = new XSSFWorkbook();
        CreationHelper ch = wb.getCreationHelper();
        String safeName = WorkbookUtil.createSafeSheetName("sheet1");
        XSSFSheet sheet = (XSSFSheet) wb.createSheet(safeName);

        CellStyle titleStyle = wb.createCellStyle();
//        titleStyle.setFont(titleFont);
        configTitleStyle(titleStyle);

        Row row0 = sheet.createRow(0);
        Cell cell = null;
        cell = row0.createCell(0);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("姓名");

        Row row1 = sheet.createRow(1);
        cell = row1.createCell(0);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("姓名");

        int rowNums = sheet.getPhysicalNumberOfRows();
        int firstColIndex = sheet.getRow(0).getFirstCellNum();
        int lastColIndex = sheet.getRow(0).getLastCellNum();
        log.info("rownums:{};first col:{};last col:{}", rowNums, firstColIndex, lastColIndex);

        CellRangeAddress cra = new CellRangeAddress(0,1,0,0);
        sheet.addMergedRegion(cra);
//        cra = new CellRangeAddress(0,1,2,02);
//        sheet.addMergedRegion(cra);

        saveFileFromWorkbook(wb, "mergeCell2");
    }

    @Test
    public void shouldCreateInOtherSheetByFile() throws IOException {
        Workbook workbook =
                poiExcelReader.readExcel(ClassUtil.getFilePath(PivotTableTest.class, TEMP_XLSX_FILE_NAME), true);

        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        int rowNums = sheet.getPhysicalNumberOfRows();
        int firstColIndex = sheet.getRow(0).getFirstCellNum();
        int lastColIndex = sheet.getRow(0).getLastCellNum();
        log.info("rownums:{};first col:{};last col:{}", rowNums, firstColIndex, lastColIndex);
        Row row0 = sheet.getRow(0);
        Cell cell = row0.getCell(0);
        System.out.println(cell);
        cell = row0.createCell(0);
        cell.setCellValue("");
        System.out.println(row0.getCell(0));

        CellRangeAddress cra = new CellRangeAddress(0,1,0,0);
        sheet.addMergedRegion(cra);
        cra = new CellRangeAddress(0,1,2,02);
        sheet.addMergedRegion(cra);

        saveFileFromWorkbook(workbook, "mergeCell");
    }

    private void configTitleStyle(CellStyle titleStyle) {
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);

        titleStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());// 填暗红色
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setWrapText(true);
    }

    public static void main(String[] args) throws Exception {
        String fileName = "D:\\Template\\" + longDateFormat.format(new Date()) + ".xls";

        FileOutputStream fos = new FileOutputStream(fileName);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("line");

        HSSFRow row = sheet.createRow(0);
//        row.setHeightInPoints(77 * PXTOPT);
//
        final String text = "                       AB\n\n\n CD";
        HSSFCell cell = row.createCell(3);
        HSSFCellStyle cellStyle = getCellFormat(wb);
//        int x1 = 61, y1 = 77;
//        int x2 = 132, y2 = 76;
//        int x3 = 144, y3 = 31;
//        int[] xys = { x1, y1, x2, y2, x3, y3 };
//        drawLine(sheet, row, 0, 0, 144, 77, xys);

        /* ********************* 画对角线 start ******************* */

/*// 在A1左上角:A1右下角之间cell（单位 分类）加入一条对角线
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 12));
        HSSFClientAnchor anchor = new HSSFClientAnchor();
//试着改变第二个参数和第六个参数来改变线的位置
        anchor.setAnchor((short) 0, 0, 0, 0, (short) 1, 1, 0, 0);
        HSSFSimpleShape line = patriarch.createSimpleShape(anchor);
        line.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
        line.setLineStyle(HSSFShape.LINESTYLE_SOLID);
// 在NPOI中线的宽度12700表示1pt,所以这里是0.5pt粗的线条。
        line.setLineWidth(6350);*/

        /* ********************* 画对角线 end ******************* */

        HSSFPatriarch patriarch =  sheet.createDrawingPatriarch();
        HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short)0, 0, (short)0, 1);
        HSSFSimpleShape shape1 = patriarch.createSimpleShape(a);
        shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
        shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID) ;

        cell.setCellValue(text);
        //cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);

        row = sheet.createRow(1);
        row.setHeightInPoints(83 * PXTOPT);
        cell = row.createCell(0);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);

        int[] xys1 = { 112, 83 };
        drawLine(sheet, row, 1, 3, 110, 83, xys1);

        wb.write(fos);
    }

    // draw cell line
    private static void drawLine(HSSFSheet sheet, HSSFRow row, int i, int j, int width, int height,
                                 int[] xys) {
        int cellWidth = (int) (PERCENT_WIDTH * PXTOPT * width);
        short cellHeight = (short) (PERCENT_HEIGHT * PXTOPT * height);
        sheet.setColumnWidth(j, cellWidth);
        row.setHeight(cellHeight);

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short) j, i, (short) (j), i);
        HSSFShapeGroup group = patriarch.createGroup(a);
        float verticalPointsPerPixel = a.getAnchorHeightInPoints(sheet);
        EscherGraphics g = new EscherGraphics(group, sheet.getWorkbook(), Color.black,
                verticalPointsPerPixel);
        EscherGraphics2d g2d = new EscherGraphics2d(g);

        for (int l = 0; l < xys.length; l += 2) {
            int x = (int) ((PERCENT_WIDTH * 0.75 * xys[l] / cellWidth) * 1023);
            int y = (int) ((PERCENT_HEIGHT * 0.75 * xys[l + 1] / cellHeight) * 255);
            g2d.drawLine(0, 0, x, y);
        }
    }

    public static HSSFCellStyle getCellFormat(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        /*if (cellStyle.getBorderBottom() != HSSFCellStyle.BORDER_THIN) {
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        }
        if (cellStyle.getBorderLeft() != HSSFCellStyle.BORDER_THIN) {
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        }
        if (cellStyle.getBorderTop() != HSSFCellStyle.BORDER_THIN) {
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        }
        if (cellStyle.getBorderRight() != HSSFCellStyle.BORDER_THIN) {
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        }*/
        cellStyle.setBottomBorderColor(createPette(wb));
        cellStyle.setLeftBorderColor(createPette(wb));
        cellStyle.setRightBorderColor(createPette(wb));
        cellStyle.setTopBorderColor(createPette(wb));
        return cellStyle;
    }

    public static short createPette(HSSFWorkbook wb) {
        short petteIndex = 0;
        Color rgb = new Color(0x00, 0x00, 0x00);
        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex(petteIndex, (byte) rgb.getRed(), (byte) rgb.getGreen(), (byte) rgb
                .getBlue());
        return petteIndex;
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
                .append(longDateFormat.format(new Date()))
                .append(".xlsx");
        String saveFilePath = builder.toString();
        log.info("output file :{}", saveFilePath);
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
}
