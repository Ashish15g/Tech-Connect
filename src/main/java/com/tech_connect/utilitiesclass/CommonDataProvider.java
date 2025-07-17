package com.tech_connect.utilitiesclass;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

public class CommonDataProvider {

    @DataProvider(name = "excelDataProvider")
    public static Object[][] getExcelData(Method method) throws Exception {
        SheetName sheetNameAnnotation = method.getAnnotation(SheetName.class);
        if (sheetNameAnnotation == null) {
            throw new RuntimeException("Missing @SheetName annotation on test method: " + method.getName());
        }

        String sheetName = sheetNameAnnotation.value();
        int rows = GetExcelData.getRows(sheetName);
        int cols = GetExcelData.getCells(sheetName);

        Object[][] data = new Object[rows - 1][cols];

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = GetExcelData.excelData(sheetName, i, j);
            }
        }

        return data;
    }
}
