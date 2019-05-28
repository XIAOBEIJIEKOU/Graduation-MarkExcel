package com.example.mark.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {
    /**
     * 获取 excel
     * @param readFilePath
     * @return
     * @throws IOException
     */
    public static Workbook getWorkBook(String readFilePath) throws IOException {
            FileInputStream readFileInputStream = new FileInputStream(readFilePath);
            // 获得工作簿对象
            Workbook workbook = null;
            // 判断excel版本，HSSF类，只支持2007以前的excel（文件扩展名为xls），而XSSH支持07以后的
            if (isExcel2003(readFilePath)){
                workbook = new HSSFWorkbook(readFileInputStream);
            } else if (isExcel2007(readFilePath)){
                workbook = new XSSFWorkbook(readFileInputStream);
            } else {
                System.out.println("您输入的文件有误");
            }
            return workbook;
    }

    /**
     * 根据cell的格式获取cell值
     * @param cell
     * @return
     */
    public static String getCellFormatValue(Cell cell) {
        String cellValue = null;
        if (cell != null) {
            // 判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case STRING: {
                    cellValue = cell.getStringCellValue();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
