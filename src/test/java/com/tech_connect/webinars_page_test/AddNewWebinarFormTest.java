package com.tech_connect.webinars_page_test;
import java.awt.AWTException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.WebinarsPage;
import com.tech_connect.utilitiesclass.GetDates;

public class AddNewWebinarFormTest extends BaseClass{
	
@Test 
	public void verifyAddWebinarFrom() throws InterruptedException, AWTException {
		WebinarsPage webPage = new WebinarsPage(driver);
		ActionDriver.click(webPage.eventsSection);
		ActionDriver.click(webPage.webinarsSection);
		ActionDriver.scrollToElement(webPage.addNewButton);
        ActionDriver.waitForElementClickable(webPage.addNewButton, 10);
        ActionDriver.safeClick(webPage.addNewButton);
		ActionDriver.selectDropdownByVisibleText(webPage.eventCategory.get(0), "External");
		ActionDriver.enterText(webPage.eventName, "Gen AI 2.O");
		ActionDriver.selectDropdownByVisibleText(webPage.eventCategory.get(1), "Tech Connect");
		ActionDriver.scrollToElement(webPage.description);
        ActionDriver.waitForElementClickable(webPage.description, 10);
		ActionDriver.enterText(webPage.description, "Tech Connect_ Gen AI 2.O Webinar");
		//ActionDriver.scrollToElement(webPage.industryTags);
		//ActionDriver.waitForElementClickable(webPage.industryTags, 30);
		ActionDriver.typeUsingActions(webPage.industryTags, "CyberSecurity");
		ActionDriver.pressEnter();
		
		
		ActionDriver.scrollToElement(webPage.location);
		//ActionDriver.waitForElementClickable(webPage.location, 10);
		ActionDriver.enterText(webPage.location, "Online");
		
		// Select start date
		ActionDriver.scrollToElement(webPage.start_date);
		// Before clicking date fields or submit button
		ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
		ActionDriver.safeClick(webPage.start_date);
		GetDates.selectDate(webPage.start_monthElem, webPage.start_nextButton, webPage.dateElements, "June 2025", "29");
		// Select end date
		ActionDriver.scrollToElement(webPage.end_date);		
		//ActionDriver.waitForElementClickable(webPage.end_date, 100);
        ActionDriver.safeClick(webPage.end_date);
        GetDates.selectDate(webPage.end_monthElem, webPage.end_nextButton, webPage.dateElements, "July 2025", "30");
		ActionDriver.safeClick(webPage.outside_Click);
        // Upload webinar image
        ActionDriver.scrollToElement(webPage.webinarImage);
        ActionDriver.waitForElementClickable(webPage.webinarImage, 50);
        ActionDriver.safeClick(webPage.webinarImage);
        ActionDriver.uploadFile("C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\images\\AI1.png");
        //Event scope Private/Public
        ActionDriver.waitForPageLoad(1000);
        ActionDriver.selectDropdownByVisibleText(webPage.eventCategory.get(2), "Public");
        //Event URL
        ActionDriver.scrollToElement(webPage.eventUrl);
        ActionDriver.enterText(webPage.eventUrl, "https://www.tech-connect.com/webinar/gen-ai-2.0");
        //Zoom Link
        ActionDriver.scrollToElement(webPage.zoomLink);
        ActionDriver.enterText(webPage.zoomLink, "https://zoom.us/j/1234567890");
        //Submit the form
        Thread.sleep(2000);
        ActionDriver.waitForPageLoad(1000);
        ActionDriver.safeClick(webPage.submitButton);
        // Verify success message
        ActionDriver.waitForElementVisible(webPage.webinar_Add_Sufull, 10);
		// Assert that the success message is displayed
		Assert.assertTrue(ActionDriver.isDisplayed(webPage.webinar_Add_Sufull),
				"Webinar added successfully message is not displayed");
		// Reset the form
//		ActionDriver.scrollToElement(webPage.resetButton);
//		ActionDriver.waitForElementClickable(webPage.resetButton, 10);
//		ActionDriver.safeClick(webPage.resetButton);
	}

@Test	
//Verify delete webinar functionality
public void verifyDeleteWebinar() {
		WebinarsPage webPage = new WebinarsPage(driver);
		ActionDriver.click(webPage.eventsSection);
		ActionDriver.click(webPage.webinarsSection);
		ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
		ActionDriver.scrollToElement(webPage.web_Det_Del_But_secn);
		ActionDriver.safeClick(webPage.web_Det_Del_But_secn);
		ActionDriver.jsClick(webPage.deleteWebinar);
		ActionDriver.jsClick(webPage.confirmDeleteButton);
		// Wait for the success message
		ActionDriver.waitForElementVisible(webPage.webinar_Delete_Sufull, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(webPage.webinar_Delete_Sufull),
				"Webinar deleted successfully message is not displayed");
		Reporter.log("Webinar deleted successfully", true);
		}
}
