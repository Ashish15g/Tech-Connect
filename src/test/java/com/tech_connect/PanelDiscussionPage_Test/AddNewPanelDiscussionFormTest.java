package com.tech_connect.PanelDiscussionPage_Test;
import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.PanelDiscussionPage;
import com.tech_connect.utilitiesclass.GetDates;
public class AddNewPanelDiscussionFormTest extends BaseClass {
	
	private PanelDiscussionPage pD;
	
	@BeforeMethod
	public void setup() {
		pD = new PanelDiscussionPage(driver);
		ActionDriver.safeClick(pD.eventsSection);
		ActionDriver.safeClick(pD.panelDiscussionsSection);
	}
	private void selectDate(boolean isSession, String startMonth, String startDay, String endMonth, String endDay) {
		var startElement = isSession ? pD.startDateSection : pD.startDateSection;
		var endElement = isSession ? pD.endDateSection : pD.endDateSection;
		
		ActionDriver.scrollToElement(startElement);
		ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
		ActionDriver.safeClick(startElement);
		GetDates.selectDate(pD.start_monthElem, pD.start_nextButton, pD.startDateElements, startMonth, startDay);
		
		ActionDriver.scrollToElement(endElement);
		ActionDriver.safeClick(endElement);
		GetDates.selectDate(pD.end_monthElem, pD.end_nextButton, pD.endDateElements, endMonth, endDay);
	}
	private void fillPanelDiscussionForm(String eventName, String poweredBy, String description, String industryTag,
			String location, String startMonth, String startDay, String endMonth, String endDay) throws InterruptedException, AWTException {
		ActionDriver.scrollToElement(pD.addNewButton);
		ActionDriver.waitForElementClickable(pD.addNewButton, 10);
		ActionDriver.safeClick(pD.addNewButton);
		
		ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(0), "External");
		ActionDriver.enterText(pD.eventName, eventName);
		ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(1), poweredBy);
		
		ActionDriver.scrollToElement(pD.description);
		ActionDriver.waitForElementClickable(pD.description, 10);
		ActionDriver.enterText(pD.description, description);
		
		ActionDriver.typeUsingActions(pD.industryTags, industryTag);
		ActionDriver.pressEnter();
		
		ActionDriver.scrollToElement(pD.location);
		ActionDriver.enterText(pD.location, location);
		
		selectDate(false, startMonth, startDay, endMonth, endDay);
		ActionDriver.safeClick(pD.outside_Click);
		
		ActionDriver.scrollToElement(pD.eventImage);
		ActionDriver.waitForElementClickable(pD.eventImage, 50);
		ActionDriver.safeClick(pD.eventImage);
		ActionDriver.uploadFile("C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\images\\AI1.png");
		
		ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(2), "Public");
		ActionDriver.enterText(pD.eventUrl, "https://www.tech-connect.com/webinar/gen-ai-2.0");
		//ActionDriver.enterText(pD.zoomLink, "https://zoom.us/j/1234567890");
		
		
	}
//	private void selectDropdownSafely(org.openqa.selenium.WebElement dropdown, String text) {
//		ActionDriver.waitForElementClickable(dropdown, 10);
//		ActionDriver.scrollToElement(dropdown);
//		try {
//			ActionDriver.selectDropdownByVisibleText(dropdown, text);
//		} catch (Exception e) {
//			ActionDriver.jsClick(dropdown);
//			ActionDriver.waitForPageLoad(500);
//			ActionDriver.selectDropdownByVisibleText(dropdown, text);
//		}
//	}
	private void submitFormSafely() {
		ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
		ActionDriver.scrollToElement(pD.submitButton);
		ActionDriver.waitForElementClickable(pD.submitButton, 10);
		try {
			ActionDriver.safeClick(pD.submitButton);
		} catch (Exception e) {
			ActionDriver.jsClick(pD.submitButton);
		}
	}
	private void verifySuccess(WebElement element, String message) {
		ActionDriver.waitForElementVisible(element, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(element), message + " message not displayed");
		Reporter.log("TEST PASSED: " + message, true);
	}
		
	
	@Test(priority = 1)
	public void verifyAddPanelDiscussionForm() {
		try {
			fillPanelDiscussionForm(
					"Gen AI 2.O", "Tech Connect",
				"Tech Connect_ Gen AI 2.O Webinar",
				"CyberSecurity",
				"Online", "June 2027", "29", "July 2027", "30");
			
			submitFormSafely();
			verifySuccess(pD.panelDiscussionAddSuccessMessage, "Panel Discussion added successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
	 }
	@Test(priority = 2,invocationCount = 10, threadPoolSize = 2)
	public void verifyDeletePanelDiscussion() throws InterruptedException {
		
		//ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
		ActionDriver.scrollToElement(pD.menuButton.get(0));
		ActionDriver.safeClick(pD.menuButton.get(0));
		ActionDriver.safeClick(pD.deletePanelDiscussion);
		ActionDriver.safeClick(pD.confirmDeleteButton);
		verifySuccess(pD.panelDiscussionDeleteSuccessMessage, "Panel discussion deleted successfully");
	}
	@Test(priority = 3, invocationCount = 4, threadPoolSize = 1)
	public void verifyDeletePanelDiscussionAll() throws InterruptedException {
	    // Wait for any overlays/loaders to disappear
	    ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);

	    // Make sure there is at least one menu button
	    if (pD.menuButton.isEmpty()) {
	        Assert.fail("No menu buttons found. There might be no panel discussions to delete.");
	    }

	    // Click the menu of the first panel discussion
	    ActionDriver.scrollToElement(pD.menuButton.get(0));
	    ActionDriver.safeClick(pD.menuButton.get(0));
	    ActionDriver.safeClick(pD.deletePanelDiscussion);
	    ActionDriver.safeClick(pD.confirmDeleteButton);

	    // Verify success message
	    verifySuccess(pD.panelDiscussionDeleteSuccessMessage, "Panel discussion deleted successfully");

	    // Wait before the next invocation
	    Thread.sleep(2000);
	}

}
