package com.dws.utils;

import static com.dws.managers.PropertiesManager.getThisProperties;
import static com.dws.utils.ProperitesConstant.*;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.exec.OS;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class rmvExcelWorker {
    private static final Logger LOGGER = LoggerFactory.getLogger(rmvExcelWorker.class);
    
    private static HSSFSheet excelSheet;
    private static HSSFWorkbook excelBook;
    private static HSSFCell cell;
    private static HSSFRow row;

    public static void openExcelFile() {
        String file = "";
        
        if (OS.isFamilyWindows()) {
            file = getThisProperties().getProperty(PATH_WINDOWS) +
                getThisProperties().getProperty(DATA_ENGINE_PATH_FOLDER);
        } else if (OS.isFamilyUnix()) {
            file = getThisProperties().getProperty(PATH_UNIX) +
                getThisProperties().getProperty(DATA_ENGINE_PATH_FOLDER);
        } else {
            String message = "Error opening test file for this OS";
                LOGGER.error(message);
                Assertions.fail(message);
        }

        try {
            FileInputStream ExcelFile = new FileInputStream(file);
            excelBook = new HSSFWorkbook(ExcelFile);
        } catch (IOException ignore) {}
    }

    public static String getCellData(int rowNum, int colNum, String sheetName) {
        String result = "";
        
        try {
            excelSheet = excelBook.getSheet(sheetName);
            cell = excelSheet.getRow(rowNum).getCell(colNum);
            cell.setCellType(CellType.STRING);
            result = cell.getStringCellValue();
        } catch (Exception ignore) {}
        
        return result;
    }
    public static int getRowCount(String SheetName){
        excelSheet = excelBook.getSheet(SheetName);
        int num = excelSheet.getLastRowNum() + 1;
        
        return num;
    }

    public static int getRowContains(String testCaseName, int colNum, String sheetName) {
        int i = 0;
        
        try {
            excelSheet = excelBook.getSheet(sheetName);
            int rowCount = getRowCount(sheetName);
            for (i = 0 ; i < rowCount; i++){
                    if  (getCellData(i, colNum, sheetName).equalsIgnoreCase(testCaseName)) {
                    break;
                }
            }
        } catch(Exception ignore) {}
        
        return i;
    }
    
    public static int getTestStepsCount(String sheetName, String testCaseID,
            int numTestCaseStart) {
        try {
            int test_case_id = Integer.parseInt(getThisProperties()
                    .getProperty(DATA_ENGINE_COL_TEST_CASE_ID));
            
            for(int i = numTestCaseStart; i <= getRowCount(sheetName); i++) {
                if(!testCaseID.equals(getCellData(i, test_case_id, sheetName))) {
                    int number = i;
                    return number;
                }
            }
            excelSheet = excelBook.getSheet(sheetName);
        } catch(Exception ignore) {}
        int num = excelSheet.getLastRowNum()+1;
        
        return num;
    }
}
