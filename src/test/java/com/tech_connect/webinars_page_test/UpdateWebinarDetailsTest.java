package com.tech_connect.webinars_page_test;
import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.WebinarsPage;
import com.tech_connect.utilitiesclass.GetDates;
@Listeners(com.tech_connect.listenersclass.ListenersClass.class)
public class UpdateWebinarDetailsTest extends BaseClass {
	private WebinarsPage webPage;
	
	@BeforeMethod
	public void setup() {
		webPage = new WebinarsPage(driver);
		ActionDriver.click(webPage.eventsSection);
		ActionDriver.click(webPage.webinarsSection);
		ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 20);
		ActionDriver.scrollToElement(webPage.menuButton.get(0));
		ActionDriver.safeClick(webPage.menuButton.get(0));
		ActionDriver.jsClick(webPage.webinarDetails);
	}
	@AfterMethod
	public void backOnDashboard() {
	ActionDriver.safeClick(webPage.logoText);
		}
	
	private void selectDate(boolean isSession, String startMonth, String startDay, String endMonth, String endDay) {
		var startElement = isSession ? webPage.sessionStartDate : webPage.start_date;
		var endElement = isSession ? webPage.sessionEndDate : webPage.end_date;
		
		ActionDriver.scrollToElement(startElement);
		ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
		ActionDriver.safeClick(startElement);
		GetDates.selectDate(webPage.start_monthElem, webPage.start_nextButton, webPage.dateElements, startMonth, startDay);
		
		ActionDriver.scrollToElement(endElement);
		ActionDriver.safeClick(endElement);
		GetDates.selectDate(webPage.end_monthElem, webPage.end_nextButton, webPage.dateElements, endMonth, endDay);
	}
	
	private void fillSpeakerForm(String name, String email, String position, String about, String linkedin, boolean isUpdate) throws InterruptedException, AWTException {
		if (isUpdate) webPage.speakerName.clear();
		ActionDriver.enterText(webPage.speakerName, name);
		if (!isUpdate) ActionDriver.enterText(webPage.speakerEmail, email);
		ActionDriver.safeClick(isUpdate ? webPage.speakerImageChenge : webPage.speakerImage);
		ActionDriver.uploadFile("C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\photos\\Ats.png");
		ActionDriver.enterText(webPage.speakerPosition, position);
		ActionDriver.enterText(webPage.speakerAbout, about);
		ActionDriver.enterText(webPage.speakerLinkedInUrl, linkedin);
		selectDropdownSafely(webPage.speakerCategory, "Moderator");
		ActionDriver.safeClick(isUpdate ? webPage.updateButton : webPage.submitButton);
	}
	
	private void selectDropdownSafely(org.openqa.selenium.WebElement dropdown, String text) {
		ActionDriver.waitForElementClickable(dropdown, 10);
		ActionDriver.scrollToElement(dropdown);
		try {
			ActionDriver.selectDropdownByVisibleText(dropdown, text);
		} catch (Exception e) {
			ActionDriver.jsClick(dropdown);
			ActionDriver.waitForPageLoad(500);
			ActionDriver.selectDropdownByVisibleText(dropdown, text);
		}
	}
	
	private void submitFormSafely() {
		ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
		ActionDriver.scrollToElement(webPage.submitButton);
		ActionDriver.waitForElementClickable(webPage.submitButton, 10);
		try {
			ActionDriver.safeClick(webPage.submitButton);
		} catch (Exception e) {
			ActionDriver.jsClick(webPage.submitButton);
		}
	}
	
	private void verifySuccess(WebElement element, String message) {
		ActionDriver.waitForElementVisible(element, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(element), message + " message not displayed");
		Reporter.log("TEST PASSED: " + message, true);
	}
	
	@Test(priority = 1)
	public void updateWebinarDetails() throws AWTException, InterruptedException {
		// Upload image
		ActionDriver.scrollToElement(webPage.webinarDetailsImage);
		ActionDriver.safeClick(webPage.webinarDetailsImage);
		ActionDriver.uploadFile("C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\images\\gen1.png");
		ActionDriver.safeClick(webPage.changeImageButton);
		ActionDriver.waitForElementVisible(webPage.webinarImageChangeSuccess, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(webPage.webinarImageChangeSuccess));
		
		// Update details
		selectDropdownSafely(webPage.eventCategory.get(0), "Internal");
		ActionDriver.enterText(webPage.eventName, "Gen AI 2.O");
		selectDropdownSafely(webPage.eventCategory.get(1), "Tech Connect");
		ActionDriver.enterText(webPage.description, "Tech Connect_ Gen AI 2.O Webinar");
		ActionDriver.typeUsingActions(webPage.industryTags, "CyberSecurity");
		ActionDriver.pressEnter();
		ActionDriver.enterText(webPage.location, "Online");
		
		selectDate(false, "June 2027", "20", "July 2027", "25");
		
		selectDropdownSafely(webPage.eventCategory.get(2), "Private");
		ActionDriver.enterText(webPage.eventUrl, "https://www.tech-connect.com/webinar/gen-ai-2.0");
		ActionDriver.enterText(webPage.zoomLink, "https://zoom.us/j/1234567890");
		submitFormSafely();
	}
	
	@Test(priority = 2)
	public void createWebinarSession() {
		ActionDriver.jsClick(webPage.createSessionButton);
		ActionDriver.safeClick(webPage.addNewButton);
		ActionDriver.enterText(webPage.sessionTitle, "Day1: Introduction to Gen AI");
		selectDropdownSafely(webPage.sessionSpeakerDropdown, "Ashish Gupta");
		selectDate(true, "June 2027", "21", "June 2027", "22");
		ActionDriver.enterText(webPage.description, "Tech Connect_ Gen AI 2.O Webinar Session 1");
		submitFormSafely();
	}
	
	@Test(priority = 3)
	public void updateWebinarSession() {
		ActionDriver.jsClick(webPage.createSessionButton);
		ActionDriver.safeClick(webPage.editSessionButton);
		ActionDriver.enterText(webPage.sessionTitle, "Day2: Introduction to Gen AI");
		selectDropdownSafely(webPage.sessionSpeakerDropdown, "Ashish Gupta");
		selectDate(true, "June 2027", "21", "June 2027", "22");
		ActionDriver.enterText(webPage.description, "Tech Connect_ Gen AI 2.O Webinar Session 2");
		submitFormSafely();
	}
	
	@Test(priority = 13)
	public void deleteWebinarSession() {
		ActionDriver.jsClick(webPage.createSessionButton);
		ActionDriver.safeClick(webPage.deleteSessionButton);
		ActionDriver.waitForElementVisible(webPage.confirmDeleteWebinarsection, 20);
		ActionDriver.click(webPage.confirmDeleteWebinarsection);
		ActionDriver.waitForElementVisible(webPage.sessionDeleteSuccessMessage, 50);
		Assert.assertTrue(ActionDriver.isDisplayed(webPage.sessionDeleteSuccessMessage));
		Reporter.log("TEST PASSED: Session deleted successfully", true);
	}
	
@Test(priority = 4)
public void addNewSpeaker() throws AWTException, InterruptedException {
   ActionDriver.jsClick(webPage.speakersSection);
   ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
   ActionDriver.scrollToElement(webPage.addNewSpeakerButton);
   ActionDriver.safeClick(webPage.addNewSpeakerButton);
   fillSpeakerForm("John Doe", "agshish@metapercept.com", "AI Specialist",
       "Good speaker with expertise in AI", "https://www.linkedin.com/in/johndoe/", false);
	verifySuccess(webPage.speakerAddedSuccessMessage,"Speaker added successfully.");
}
	
	@Test(priority = 5)
	public void updateSpeaker() throws AWTException, InterruptedException {
		ActionDriver.jsClick(webPage.speakersSection);
		ActionDriver.jsClick(webPage.sessionSpeakersList.get(0));
		fillSpeakerForm("Doe John", null, "GEN AI Specialist",
			"Good speaker with expertise in GEN AI", "https://www.linkedin.com/in/johndoe/", true);
		verifySuccess(webPage.speakerUpdateSuccessMessage, "Speaker updated successfully.");
	}
	
	@Test(priority = 6)
	public void addExistingSpeaker() {
		ActionDriver.jsClick(webPage.speakersSection);
		ActionDriver.jsClick(webPage.addExistingSpeakersButton);
		ActionDriver.typeUsingActions(webPage.existingSpeakersDropdown, "Ashish Gupta");
		ActionDriver.pressEnter();
		submitFormSafely();
		verifySuccess(webPage.speakerEAddedSuccessMessage, "Existing speaker added successfully");
	}
	
	@Test(priority = 7)
	public void deleteSpeaker() {
		ActionDriver.jsClick(webPage.speakersSection);
		ActionDriver.waitForElementVisible(webPage.deleteSpeakerIcon.get(0), 10);
		ActionDriver.scrollToElement(webPage.deleteSpeakerIcon.get(0));
		ActionDriver.jsClick(webPage.deleteSpeakerIcon.get(0));
		ActionDriver.waitForElementVisible(webPage.confirmDeleteSessionButton, 10);
		ActionDriver.jsClick(webPage.confirmDeleteSessionButton);
		verifySuccess(webPage.speakerDeleteSuccessMessage, "Speaker deleted successfully");
	}
@Test(priority = 8)
	public void addNewSponsor() throws InterruptedException, AWTException {
		ActionDriver.jsClick(webPage.sponsorsSection);
		ActionDriver.jsClick(webPage.addNewSponsorButton);
		ActionDriver.enterText(webPage.companyName, "Tech Info Connect");
		ActionDriver.enterText(webPage.companyWebsiteUrl, "https://www.tech-connect.com");
		ActionDriver.safeClick(webPage.sponsorlogoBannerImage.get(0));
		ActionDriver.uploadFile("C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\Logo\\metaperceptlogo.jpg");
		ActionDriver.safeClick(webPage.sponsorlogoBannerImage.get(1));
		ActionDriver.uploadFile("C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\images\\test-banner-2.png");
		ActionDriver.enterText(webPage.sponsorDescription, "Tech Connect is a leading platform for technology webinars.");
		ActionDriver.scrollToElement(webPage.sponsorCategory);
		selectDropdownSafely(webPage.sponsorCategory, "Technology");
		selectDropdownSafely(webPage.sponsorTier, "Gold");
		submitFormSafely();
		verifySuccess(webPage.sponsorAddSuccessMessage, "Sponsor added successfully.");
		
}
	@Test(priority = 9)
	public void addExistingSponsor() {
		ActionDriver.jsClick(webPage.sponsorsSection);
		ActionDriver.jsClick(webPage.addExistingSponsorsButton);
		ActionDriver.typeUsingActions(webPage.existingSpeakersDropdown, "Tech Connect");
		ActionDriver.pressEnter();
		submitFormSafely();
		verifySuccess(webPage.sponsorsAddSuccessMessage, "Existing sponsor added successfully.");
	}
	@Test(priority = 10)
	public void deleteSponsor() {
		ActionDriver.jsClick(webPage.sponsorsSection);
		ActionDriver.waitForElementVisible(webPage.deleteSpeakerIcon.get(0), 10);
		ActionDriver.scrollToElement(webPage.deleteSpeakerIcon.get(0));
		ActionDriver.jsClick(webPage.deleteSpeakerIcon.get(0));
		ActionDriver.waitForElementVisible(webPage.confirmDeleteSessionButton, 10);
		ActionDriver.jsClick(webPage.confirmDeleteSessionButton);
		verifySuccess(webPage.sponsorDeleteSuccessMessage, "Sponsor deleted successfully");
	}
	@Test(priority = 11)
	public void publishWebinar() {
		ActionDriver.jsClick(webPage.publishButton);
		ActionDriver.jsClick(webPage.confirmPublishButton);
		ActionDriver.waitForElementVisible(webPage.eventPublishedSuccessMessage, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(webPage.eventPublishedSuccessMessage));
		Reporter.log("TEST PASSED: Webinar published successfully", true);
	}
	@Test(priority = 12)
	public void saveAsDraftWebinar() {
		ActionDriver.jsClick(webPage.saveAsDraftButton);
		ActionDriver.jsClick(webPage.confirmSaveAsDraftButton);
		ActionDriver.waitForElementVisible(webPage.eventDraftSuccessMessage, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(webPage.eventDraftSuccessMessage));
		Reporter.log("TEST PASSED: Webinar saved as draft successfully", true);
	}
	@Test(priority = 14)
	public void addWebinarVideo() throws AWTException, InterruptedException {
		ActionDriver.jsClick(webPage.videosSection);
		ActionDriver.safeClick(webPage.addVideoButton);
		ActionDriver.uploadFile("C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\videos\\webinar.mov");
		ActionDriver.jsClick(webPage.uploadVideoButton);
		ActionDriver.jsClick(webPage.confirmUploadVideoButton);
		verifySuccess(webPage.videoUploadSuccessMessage, "Video added successfully.");
	}
	@Test(priority = 15)
	public void deleteWebinarVideo() {
		ActionDriver.jsClick(webPage.videosSection);
		ActionDriver.waitForElementVisible(webPage.deleteVideoButton, 10);
		ActionDriver.safeClick(webPage.deleteVideoButton);
		ActionDriver.waitForElementVisible(webPage.confirmDeleteVideoButton, 10);
		ActionDriver.safeClick(webPage.confirmDeleteVideoButton);
		verifySuccess(webPage.videoDeleteSuccessMessage, "Video deleted successfully.");
	}
	
}
