package com.dws.managers;

import com.dws.pages.StartPage;
import com.dws.utils.ExcelWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEngineManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataEngineManager.class);

    public static final int COL_TEST_CASE_ID = 0;
    private static final int COL_RUN_MODE = 1;
    public static final int COL_WIDGET_NAME = 2;
    private static final int COL_ACTION_KEYWORD = 3;
    private static final int COL_DATA = 4;
    private static final String SHEET_TEST_STEPS = "Test Steps";
    private static final String SHEET_TEST_CASES = "Test Cases";

    private static final StartPage controlKVFHook = new StartPage("StartPage");

    public DataEngineManager() {
        try {
            ExcelWorker.openExcelFile();
        } catch (Exception ex) {
            LOGGER.error("Ошибка чтения файла с данными");
        }
    }

    public void Execute() {
        int iTotalTestCases = ExcelWorker.getRowCount(SHEET_TEST_CASES)-1;
        LOGGER.debug("iTotalTestCases={}", iTotalTestCases);
        for (int iTestcase = 1; iTestcase <= iTotalTestCases; iTestcase++) {
            String sTestCaseID = ExcelWorker.getCellData(iTestcase, COL_TEST_CASE_ID, SHEET_TEST_CASES);
            String sRunMode = ExcelWorker.getCellData(iTestcase, COL_RUN_MODE, SHEET_TEST_CASES);
            LOGGER.debug("sTestCaseID={}, sRunMode={}", sTestCaseID, sRunMode);
            if (sRunMode.equals("YES")) {
                int iFirstTestStep = ExcelWorker.getRowContains(sTestCaseID, COL_TEST_CASE_ID, SHEET_TEST_STEPS);
                int iTestLastStep = ExcelWorker.getTestStepsCount(SHEET_TEST_STEPS, sTestCaseID, iFirstTestStep)-1;
                LOGGER.debug("iFirstTestStep={}, iTestLastStep={}", iFirstTestStep, iTestLastStep);
                for (int i = iFirstTestStep;i <= iTestLastStep; i++) {
                    String sWidgetName = ExcelWorker.getCellData(i, COL_WIDGET_NAME, SHEET_TEST_STEPS);
                    String sActionKeyword = ExcelWorker.getCellData(i, COL_ACTION_KEYWORD, SHEET_TEST_STEPS);
                    String sData = ExcelWorker.getCellData(i, COL_DATA, SHEET_TEST_STEPS);
                    if(sData == null) {
                        sData = "";
                        LOGGER.debug("sWidgetName={}, sActionKeyword={}", sWidgetName, sActionKeyword);
                    } else
                        LOGGER.debug("sWidgetName={}, sActionKeyword={}, sData={}", sWidgetName, sActionKeyword, sData);
//                    controlKVFHook.executeWidgetAction(sWidgetName, sActionKeyword, sData);
                }
            }
        }
    }
}
