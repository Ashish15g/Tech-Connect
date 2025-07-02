package com.tech_connect.utilitiesclass;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class GetExcelData {
	
	public static String excelData1(String s, int row, int column) throws Exception {
		try (FileInputStream fis = new FileInputStream("src\\test\\resources\\testData\\TC_Admin.xlsx")) {
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(s);
			String data = sheet.getRow(row).getCell(column).toString();
			return data;
		} catch (IOException e) {
			throw new Exception("Error reading Excel file: " + e.getMessage());
		}
	}
	 public static int getRows(String s) throws EncryptedDocumentException, IOException
	    {
	        FileInputStream fis = new FileInputStream("src\\test\\resources\\testData\\TC_Admin.xlsx");
	        Workbook wb = WorkbookFactory.create(fis);
	        Sheet sheet = wb.getSheet(s);
	    
	        int rows = sheet.getPhysicalNumberOfRows();
	        return rows;
	        
	    }

		/*
		 * public static int getCells(String s) throws EncryptedDocumentException,
		 * IOException { FileInputStream fis = new
		 * FileInputStream("src\\test\\resources\\testData\\TC_Admin.xlsx"); Workbook wb
		 * = WorkbookFactory.create(fis); Sheet sheet = wb.getSheet(s);
		 * 
		 * int rows = sheet.getPhysicalNumberOfRows();
		 * 
		 * if (rows == 0) { wb.close(); return 0; // Return 0 if no rows exist }
		 * 
		 * Row row = sheet.getRow(rows - 1); // Get the last row if (row == null) {
		 * wb.close(); return 0; // Return 0 if the last row is empty }
		 * 
		 * int cells = row.getPhysicalNumberOfCells(); wb.close(); return cells; }
		 */
	 public static int getCells(String s) throws IOException {
		    FileInputStream fis = new FileInputStream("src\\test\\resources\\testData\\TC_Admin.xlsx");
		    Workbook wb = WorkbookFactory.create(fis);
		    Sheet sheet = wb.getSheet(s);

		    // Use first row (usually contains headers or valid structure)
		    Row row = sheet.getRow(0);
		    if (row == null) {
		        wb.close();
		        return 0;
		    }

		    int cells = row.getPhysicalNumberOfCells();
		    wb.close();
		    return cells;
		}
	 public static String excelData(String s, int row, int column) throws Exception {
		    try (FileInputStream fis = new FileInputStream("src\\test\\resources\\testData\\TC_Admin.xlsx")) {
		        Workbook wb = WorkbookFactory.create(fis);
		        Sheet sheet = wb.getSheet(s);
		        Row sheetRow = sheet.getRow(row);
		        if (sheetRow == null) {
		            return ""; // or throw new Exception("Row " + row + " is empty");
		        }
		        if (sheetRow.getCell(column) == null) {
		            return ""; // or throw new Exception("Cell " + column + " in row " + row + " is empty");
		        }
		        String data = sheetRow.getCell(column).toString();
		        return data;
		    } catch (IOException e) {
		        throw new Exception("Error reading Excel file: " + e.getMessage());
		    }
		}

	}


