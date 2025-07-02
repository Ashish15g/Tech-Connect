package com.tech_connet.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.UsersPage;
import com.tech_connect.utilitiesclass.GetExcelData;

public class UserPageTest extends BaseClass{

	@DataProvider(name = "excelData")
	public Object[][] getExcelData() throws Exception {
	    String sheetName = "Sheet1"; // Replace with your actual sheet name
	    
	    int rowCount = GetExcelData.getRows(sheetName);
	    int colCount = GetExcelData.getCells(sheetName);
	    
	    System.out.println("Reading Excel Data - Rows: " + rowCount + ", Columns: " + colCount);
	    
	    Object[][] data = new Object[rowCount][colCount];

	    for (int i = 0; i < rowCount; i++) {
	        for (int j = 0; j < colCount; j++) {
	            data[i][j] = GetExcelData.excelData(sheetName, i, j);
	        }
	    }

	    return data;
	}
	@Test(priority = 2, dataProvider = "excelData")
    public void addUserWithValidData(String uName, String uEmail, String uCompany, String uJobTitle) throws Exception {
	
    	UsersPage usersPage = new UsersPage(driver);
        ActionDriver.click(usersPage.usersSection);
        ActionDriver.click(usersPage.addNewUser);

        //String uniqueEmail = "john.doe" + System.currentTimeMillis() + "@example.com";
       
	    
        ActionDriver.enterText(usersPage.name, uName);
       
        ActionDriver.enterText(usersPage.email, uEmail);
       
        ActionDriver.enterText(usersPage.company, uCompany);
      
        ActionDriver.enterText(usersPage.jobTitle, uJobTitle);
       
        ActionDriver.click(usersPage.submitButton);
        SoftAssert ass = new SoftAssert();
        ass.assertTrue(ActionDriver.isDisplayed(usersPage.user_Add_Sufull), "Success message not displayed");
        ActionDriver.click(usersPage.cancelButton);
        ass.assertAll();
    }

}
