package com.tech_connect.PanelDiscussionPage_Test;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.PanelDiscussionPage;
import com.tech_connect.utilitiesclass.GetDates;
import com.tech_connect.utilitiesclass.GetExcelData;

public class AddNewPanelDiscussionFormTest3 extends BaseClass {

    private PanelDiscussionPage pD;

    @BeforeClass
    public void setup() {
        pD = new PanelDiscussionPage(driver);
        ActionDriver.click(pD.eventsSection);
        ActionDriver.click(pD.panelDiscussionsSection);
    }

    @DataProvider(name = "panelDiscussionDP")
    public Object[][] getExcelData() throws Exception {
        String sheetName = "PanelDiscussionData";
        int rows = GetExcelData.getRows(sheetName);
        int cols = GetExcelData.getCells(sheetName);

        Object[][] data = new Object[rows - 1][cols];

        System.out.println("===== Reading data from sheet: " + sheetName + " =====");

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String cellValue = GetExcelData.excelData(sheetName, i, j);
                data[i - 1][j] = cellValue;
                System.out.println("Row " + (i - 1) + " Col " + j + " = " + cellValue);
            }
        }

        System.out.println("===== DataProvider data matrix =====");
        for (int i = 0; i < data.length; i++) {
            System.out.print("Row " + i + ": ");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " | ");
            }
            System.out.println();
        }

        return data;
    }

    @Test(dataProvider = "panelDiscussionDP")
    public void verifyAddPanelDiscussionForm(
            String eventCategory,
            String eventName,
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
            String imagePath,
            String eventScope,
            String eventUrl,
            String zoomLink
    ) throws InterruptedException, AWTException {

        Reporter.log("===== Starting test for Event: " + eventName + " =====", true);

        // Parse years safely
        int startYearInt = Integer.parseInt(startYear.split("\\.")[0]);
        int endYearInt = Integer.parseInt(endYear.split("\\.")[0]);

        // Add New Panel Discussion
        ActionDriver.scrollToElement(pD.addNewButton);
        ActionDriver.waitForElementClickable(pD.addNewButton, 10);
        ActionDriver.safeClick(pD.addNewButton);

        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(0), eventCategory);
        ActionDriver.enterText(pD.eventName, eventName);
        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(1), poweredBy);

        ActionDriver.scrollToElement(pD.description);
        ActionDriver.waitForElementClickable(pD.description, 10);
        ActionDriver.enterText(pD.description, description);

        ActionDriver.typeUsingActions(pD.industryTags, industryTags);
        ActionDriver.pressEnter();

        ActionDriver.scrollToElement(pD.location);
        ActionDriver.enterText(pD.location, location);

        // Dates
        selectDate(startMonth, startYearInt, startDay, endMonth, endYearInt, endDay);

        ActionDriver.safeClick(pD.outside_Click);

        ActionDriver.scrollToElement(pD.webinarImage);
        ActionDriver.waitForElementClickable(pD.webinarImage, 30);
        ActionDriver.safeClick(pD.webinarImage);
        ActionDriver.uploadFile(imagePath);
        Thread.sleep(2000); // Wait for image upload
        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(2), eventScope);
        ActionDriver.enterText(pD.eventUrl, eventUrl);
        ActionDriver.enterText(pD.zoomLink, zoomLink);

        ActionDriver.scrollToElement(pD.submitButton);
        ActionDriver.waitForElementClickable(pD.submitButton, 10);
        ActionDriver.safeClick(pD.submitButton);

        ActionDriver.waitForElementVisible(pD.panelDiscussionAddSuccessMessage, 10);
        Assert.assertTrue(ActionDriver.isDisplayed(pD.panelDiscussionAddSuccessMessage),
                "Success message not displayed");
        Reporter.log("Panel Discussion added successfully: " + eventName, true);
    }

    private void selectDate(
            String startMonth, int startYear, String startDay,
            String endMonth, int endYear, String endDay) {

        ActionDriver.scrollToElement(pD.start_date);
        ActionDriver.safeClick(pD.start_date);
        GetDates.selectDatePro(
                pD.start_monthElem,
                pD.start_nextButton,
                pD.start_previousButton,
                pD.dateElements,
                startMonth,
                startYear,
                startDay
        );

        ActionDriver.scrollToElement(pD.end_date);
        ActionDriver.safeClick(pD.end_date);
        GetDates.selectDatePro(
                pD.end_monthElem,
                pD.end_nextButton,
                pD.start_previousButton,
                pD.dateElements,
                endMonth,
                endYear,
                endDay
        );
    }
}
