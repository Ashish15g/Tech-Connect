package com.tech_connect.PanelDiscussionPage_Test;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.PanelDiscussionPage;
import com.tech_connect.utilitiesclass.GetDates;
import com.tech_connect.utilitiesclass.GetExcelData;

public class UpdateghPanelDiscussionsDetailsTest extends BaseClass {

	private PanelDiscussionPage pD;

	@BeforeMethod
	public void setup() {
		pD = new PanelDiscussionPage(driver);
		ActionDriver.click(pD.eventsSection);
		ActionDriver.click(pD.panelDiscussionsSection);
		ActionDriver.scrollToElement(pD.menuButton.get(0));
		ActionDriver.safeClick(pD.menuButton.get(0));
		ActionDriver.safeClick(pD.panelDiscussionDetails);
	}
	@DataProvider(name = "panelDiscussionData1")
    public Object[][] getExcelData1() throws Exception {
        String sheetName = "UpdatePanelDiscussionDetais";
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
    @DataProvider(name = "panelDiscussionData")
    public Object[][] getExcelData() throws Exception {
        String sheetName = "UpdatePanelDiscussionDetais";
        int rows = GetExcelData.getRows(sheetName);
        int cols = GetExcelData.getCells(sheetName);

        Object[][] data = new Object[rows - 1][cols];

        //System.out.println("===== Reading data from sheet: " + sheetName + " =====");

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String cellValue = GetExcelData.excelData(sheetName, i, j);
                data[i - 1][j] = cellValue;
               // System.out.println("Row " + (i - 1) + " Col " + j + " = " + cellValue);
            }
        }

       // System.out.println("===== DataProvider data matrix =====");
        for (int i = 0; i < data.length; i++) {
           // System.out.print("Row " + i + ": ");
            for (int j = 0; j < data[i].length; j++) {
                //System.out.print(data[i][j] + " | ");
            }
           // System.out.println();
        }

        return data;
    }

    @Test(dataProvider = "panelDiscussionData")
    public void verifyupdatePanelDiscussionDetails(
    		String imagePath,
    		String eventCategory,
            String eventName,
            String eventScope,
            String poweredBy,
            String description,
            String industryTags,
            String location,
            String startYear,
            String startMonth,
            String startDay,
            String endYear,
            String endMonth,
            String endDay,
            String eventUrl) throws InterruptedException, AWTException {

        // Parse years safely
        int startYearInt = Integer.parseInt(startYear.split("\\.")[0]);
        int endYearInt = Integer.parseInt(endYear.split("\\.")[0]);
        String startDaySplit = startDay.split("\\.")[0];
        String endDaySplit = endDay.split("\\.")[0];
        

        
        ActionDriver.waitForElementClickable(pD.webinarImage, 80);
        ActionDriver.safeClick(pD.webinarImage);
        ActionDriver.uploadFile(imagePath);
        ActionDriver.safeClick(pD.changeImageButton);
		ActionDriver.waitForElementVisible(pD.panelDiscussionImageChangeSuccess, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(pD.panelDiscussionImageChangeSuccess),
								"❌ 🔴Panel Discussion image change success message not displayed");
		//Reporter.log("✅ 🟢Panel Discussion image changed successfully", true);
				Reporter.log("✅ 🟢Panel Discussion image changed successfully", true);
        //Thread.sleep(3000);

        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(0), eventCategory);
        ActionDriver.enterText(pD.eventName, eventName);
        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(1), eventScope);
        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(2), poweredBy);

        ActionDriver.scrollToElement(pD.description);
        ActionDriver.waitForElementClickable(pD.description, 10);
        ActionDriver.enterText(pD.description, description);

        ActionDriver.typeUsingActions(pD.industryTags, industryTags);
        ActionDriver.pressEnter();

        ActionDriver.scrollToElement(pD.location);
        ActionDriver.enterText(pD.location, location);
        // Dates
        selectDate(startMonth, startYearInt, startDaySplit, endMonth, endYearInt, endDaySplit);
        ActionDriver.enterText(pD.eventUrl, eventUrl);

        ActionDriver.scrollToElement(pD.submitButton);
        ActionDriver.waitForElementClickable(pD.submitButton, 50);
        ActionDriver.safeClick(pD.submitButton);
        
        ActionDriver.waitForElementVisible(pD.panelDiscussionUpdateSuccessMessage, 10);
        Assert.assertTrue(ActionDriver.isDisplayed(pD.panelDiscussionUpdateSuccessMessage),
                "❌ 🔴Success message not displayed");
        Reporter.log("✅ 🟢 Panel Discussion Updated successfully: " + eventName, true);
    }

    private void selectDate(String startMonth, int startYear, String startDay, String endMonth, int endYear, String endDay) {
        ActionDriver.scrollToElement(pD.startDateSection);
        ActionDriver.safeClick(pD.startDateSection);
        GetDates.selectDatePro(pD.start_monthElem, pD.start_nextButton, pD.start_previousButton, pD.startDateElements, startMonth, startYear, startDay);

        //ActionDriver.scrollToElement(pD.end_date);
        ActionDriver.safeClick(pD.endDateSection);
        GetDates.selectDatePro(pD.end_monthElem, pD.end_nextButton, pD.start_previousButton, pD.endDateElements, endMonth, endYear, endDay);
    }
}