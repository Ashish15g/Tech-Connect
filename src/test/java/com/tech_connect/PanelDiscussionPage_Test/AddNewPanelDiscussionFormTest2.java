package com.tech_connect.PanelDiscussionPage_Test;

import java.awt.AWTException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.PanelDiscussionPage;
import com.tech_connect.utilitiesclass.CommonDataProvider;
import com.tech_connect.utilitiesclass.GetDates;
import com.tech_connect.utilitiesclass.SheetName;

public class AddNewPanelDiscussionFormTest2 extends BaseClass {

    private PanelDiscussionPage pD;

@BeforeClass
    public void setup() {
        pD = new PanelDiscussionPage(driver);
        ActionDriver.click(pD.eventsSection);
        ActionDriver.click(pD.panelDiscussionsSection);
    }

@Test(priority = 1, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("panelDiscussionData")
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
            String zoomLink) throws InterruptedException, AWTException {

        // Parse years safely
        int startYearInt = Integer.parseInt(startYear.split("\\.")[0]);
        int endYearInt = Integer.parseInt(endYear.split("\\.")[0]);
        String startDaySplit = startDay.split("\\.")[0];
        String endDaySplit = endDay.split("\\.")[0];

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
        selectDate(startMonth, startYearInt, startDaySplit, endMonth, endYearInt, endDaySplit);

        ActionDriver.safeClick(pD.outside_Click);

        ActionDriver.scrollToElement(pD.eventImage);
        ActionDriver.waitForElementClickable(pD.eventImage, 80);
        ActionDriver.safeClick(pD.eventImage);
        ActionDriver.uploadFile(imagePath);
        //ActionDriver.waitForElementVisible(pD.imageThumbnail, 10);
        Thread.sleep(3000);
        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(2), eventScope);
        ActionDriver.waitForElementClickable(pD.eventUrl, 50);
        ActionDriver.enterText(pD.eventUrl, eventUrl);
        //ActionDriver.waitForElementClickable(pD.zoomLink, 50);
        //ActionDriver.enterText(pD.zoomLink, zoomLink);

        ActionDriver.scrollToElement(pD.submitButton);
        ActionDriver.waitForElementClickable(pD.submitButton, 50);
        ActionDriver.safeClick(pD.submitButton);
        
        ActionDriver.waitForElementVisible(pD.panelDiscussionAddSuccessMessage, 10);
        Assert.assertTrue(ActionDriver.isDisplayed(pD.panelDiscussionAddSuccessMessage),
                "Success message not displayed");
        Reporter.log("Test Pass: Panel Discussion added successfully: " + eventName, true);
    }

	private void selectDate(String startMonth, int startYear, String startDay, String endMonth, int endYear,
			String endDay) {
		// Start date
		ActionDriver.scrollToElement(pD.startDateSection);
		ActionDriver.waitForElementClickable(pD.startDateSection, 10);
		ActionDriver.safeClick(pD.startDateSection);
		ActionDriver.waitForElementVisible(pD.start_monthElem, 10);
		// Wait for overlay to disappear if any
		//ActionDriver.waitForNoOverlay(5); // Implement this in ActionDriver if not present
		// Select start date
		GetDates.selectDatePro(pD.start_monthElem, pD.start_nextButton, pD.start_previousButton, pD.startDateElements,
				startMonth, startYear, startDay);
		// End date
		ActionDriver.scrollToElement(pD.endDateSection);
		ActionDriver.waitForElementClickable(pD.endDateSection, 2000);
		ActionDriver.safeClick(pD.endDateSection);
		ActionDriver.waitForElementVisible(pD.end_monthElem, 2000);
		// Wait for overlay to disappear if any
		//ActionDriver.waitForNoOverlay(5);
		// Select end date
		GetDates.selectDatePro(pD.end_monthElem, pD.end_nextButton, pD.start_previousButton, pD.endDateElements, endMonth,
				endYear, endDay);
	}
}
