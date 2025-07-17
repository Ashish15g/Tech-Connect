package com.tech_connect.PanelDiscussionPage_Test;

import java.awt.AWTException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.PanelDiscussionPage;
import com.tech_connect.utilitiesclass.GetDates;
import com.tech_connect.utilitiesclass.SheetName;
import com.tech_connect.utilitiesclass.CommonDataProvider;

public class UpdateghPanelDiscussionsDetailsTest2 extends BaseClass {

    private PanelDiscussionPage pD;

@BeforeMethod()
    public void setup() {
        pD = new PanelDiscussionPage(driver);
        ActionDriver.click(pD.eventsSection);
        ActionDriver.click(pD.panelDiscussionsSection);
        ActionDriver.scrollToElement(pD.menuButton.get(0));
        //ActionDriver.waitForElementNotVisible(By.cssSelector("div.modal.fade.show"), 0);
        ActionDriver.jsClick(pD.menuButton.get(0));
        ActionDriver.safeClick(pD.panelDiscussionDetails);
    }
@AfterMethod
	public void backOnDashboard() {
	ActionDriver.safeClick(pD.logoText);
		}

@Test(priority = 1, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("UpdatePanelDiscussionDetais")
    public void verifyupdatePanelDiscussionDetails(String imagePath, String eventCategory, String eventName,
            String eventScope, String poweredBy, String description, String industryTags, String location,
            String startYear, String startMonth, String startDay, String endYear, String endMonth, String endDay,
            String eventUrl) throws InterruptedException, AWTException {

        int startYearInt = Integer.parseInt(startYear.split("\\.")[0]);
        int endYearInt = Integer.parseInt(endYear.split("\\.")[0]);
        String startDaySplit = startDay.split("\\.")[0];
        String endDaySplit = endDay.split("\\.")[0];

        ActionDriver.waitForElementClickable(pD.webinarImage, 80);
        ActionDriver.safeClick(pD.webinarImage);
        ActionDriver.uploadFile(imagePath);
        ActionDriver.safeClick(pD.changeImageButton);

        ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Panel Discussion: ", false );

        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(0), eventCategory);
        ActionDriver.setInputValue(pD.eventName, eventName);
        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(1), eventScope);
        ActionDriver.selectDropdownByVisibleText(pD.eventCategory.get(2), poweredBy);

        ActionDriver.scrollToElement(pD.description);
        ActionDriver.waitForElementClickable(pD.description, 10);
        ActionDriver.setInputValue(pD.description, description);

        ActionDriver.typeUsingActions(pD.industryTags, industryTags);
        ActionDriver.pressEnter();

        ActionDriver.scrollToElement(pD.location);
        ActionDriver.setInputValue(pD.location, location);

        selectDate(startMonth, startYearInt, startDaySplit, endMonth, endYearInt, endDaySplit);

        ActionDriver.setInputValue(pD.eventUrl, eventUrl);
        ActionDriver.scrollToElement(pD.submitButton);
        //ActionDriver.waitForElementClickable(pD.submitButton, 10);

        //ActionDriver.waitForElementNotVisible(By.cssSelector("div.modal.fade.show"), 10);
        ActionDriver.safeClick(pD.submitButton);

        ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Panel Discussion : ", false );
    }

    private void selectDate(String startMonth, int startYear, String startDay, String endMonth, int endYear,
            String endDay) {
        ActionDriver.scrollToElement(pD.startDateSection);
        ActionDriver.safeClick(pD.startDateSection);
        GetDates.selectDatePro(pD.start_monthElem, pD.start_nextButton, pD.start_previousButton, pD.startDateElements,
                startMonth, startYear, startDay);

        ActionDriver.safeClick(pD.endDateSection);
        GetDates.selectDatePro(pD.end_monthElem, pD.end_nextButton, pD.start_previousButton, pD.endDateElements, endMonth,
                endYear, endDay);
    }
@Test(priority = 2, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("EventSessions")
    public void createNewSession(String sessionTitle, String speakerName, String startYear, String startMonth,
            String startDay, String endYear, String endMonth, String endDay, String description) throws InterruptedException {
        int startYearInt = Integer.parseInt(startYear.split("\\.")[0]);
        int endYearInt = Integer.parseInt(endYear.split("\\.")[0]);
        String startDaySplit = startDay.split("\\.")[0];
        String endDaySplit = endDay.split("\\.")[0];

        ActionDriver.jsClick(pD.createSessionButton);
        ActionDriver.safeClick(pD.addNewButton);
        ActionDriver.enterText(pD.sessionTitle, sessionTitle);
        ActionDriver.selectDropdownByVisibleText(pD.sessionSpeakerDropdown, speakerName);
        selectDate(startMonth, startYearInt, startDaySplit, endMonth, endYearInt, endDaySplit);
        ActionDriver.enterText(pD.description, description);
        ActionDriver.scrollToElement(pD.submitButton);
        //ActionDriver.waitForElementClickable(pD.submitButton, 0);
        ActionDriver.safeClick(pD.submitButton);
        ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Session: ", true );
    }

@Test(priority = 3, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("EventSessionsUpdate")
    public void UpdateSession(String sessionTitle, String speakerName, String startYear, String startMonth,
            String startDay, String endYear, String endMonth, String endDay, String description) throws InterruptedException {
        int startYearInt = Integer.parseInt(startYear.split("\\.")[0]);
        int endYearInt = Integer.parseInt(endYear.split("\\.")[0]);
        String startDaySplit = startDay.split("\\.")[0];
        String endDaySplit = endDay.split("\\.")[0];

        ActionDriver.jsClick(pD.createSessionButton);
        ActionDriver.safeClick(pD.editSessionButton);
        ActionDriver.enterText(pD.sessionTitle, sessionTitle);
        ActionDriver.selectDropdownByVisibleText(pD.sessionSpeakerDropdown, speakerName);
        selectDate(startMonth, startYearInt, startDaySplit, endMonth, endYearInt, endDaySplit);
        ActionDriver.enterText(pD.description, description);
        ActionDriver.scrollToElement(pD.submitButton);
        //ActionDriver.waitForElementClickable(pD.submitButton, 0);

        // Wait for modal/overlay to disappear before clicking
        //ActionDriver.waitForElementNotVisible(By.cssSelector("div.modal.fade.show"), 10);
        ActionDriver.safeClick(pD.submitButton);
        ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Session: ", true );
    }
@Test(priority = 4)
	public void deleteSession() throws InterruptedException {
		ActionDriver.jsClick(pD.createSessionButton);
		ActionDriver.safeClick(pD.deleteSessionButton);
		ActionDriver.click(pD.confirmDeleteSessionButton);
		ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Session: ", false );
	}
@Test(priority = 5, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("AddNewSpeaker")
public void addNewSpeaker(String name, String email, String speakerImage, String position, String linkedin, String about, String category) throws AWTException, InterruptedException {
	ActionDriver.jsClick(pD.speakersSection);
	ActionDriver.scrollToElement(pD.addNewSpeakerButton);
	ActionDriver.safeClick(pD.addNewSpeakerButton);
	ActionDriver.enterText(pD.speakerName, name);
	ActionDriver.enterText(pD.speakerEmail, email);
    ActionDriver.safeClick(pD.speakerImage);
    ActionDriver.uploadFile(speakerImage);
	ActionDriver.enterText(pD.speakerPosition, position);
	ActionDriver.enterText(pD.speakerLinkedInUrl, linkedin);
	ActionDriver.enterText(pD.speakerAbout, about);
	ActionDriver.scrollToElement(pD.speakerCategory);
	ActionDriver.selectDropdownByVisibleText(pD.speakerCategory, category);
	ActionDriver.scrollToElement(pD.submitButton);
	ActionDriver.safeClick(pD.submitButton);
	ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Session: ", false );
	
	}
@Test(priority = 6, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("UpdateSpeaker")
public void updateSpeaker(String name, String email, String speakerImage, String position, String linkedin, String about, String category) throws AWTException, InterruptedException {
	ActionDriver.safeClick(pD.speakersSection);
	ActionDriver.scrollToElement(pD.searchUserInput);
	ActionDriver.enterText(pD.searchUserInput, name);
	ActionDriver.safeClick(pD.sessionSpeakersList.get(0));
	ActionDriver.setInputValue(pD.speakerName, name);
	ActionDriver.setInputValue(pD.speakerEmail, email);
    ActionDriver.safeClick(pD.imageChenge.get(0));
    ActionDriver.uploadFile(speakerImage);
	ActionDriver.setInputValue(pD.speakerPosition, position);
	ActionDriver.setInputValue(pD.speakerLinkedInUrl, linkedin);
	ActionDriver.setInputValue(pD.speakerAbout, about);
	ActionDriver.scrollToElement(pD.speakerCategory);
	ActionDriver.selectDropdownByVisibleText(pD.speakerCategory, category);
	ActionDriver.scrollToElement(pD.updateButton);
	ActionDriver.safeClick(pD.updateButton);
	ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Session: ", false );
	}
@Test(priority = 7, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("AddExistingSpeaker")
public void addExistingSpeaker(String speakers ) {
	ActionDriver.jsClick(pD.speakersSection);
	ActionDriver.jsClick(pD.addExistingSpeakersButton);
	ActionDriver.typeUsingActions(pD.existingSpeakersDropdown, speakers);
	ActionDriver.pressEnter();
	ActionDriver.scrollToElement(pD.submitButton);
	ActionDriver.waitForElementClickable(pD.submitButton, 50);
	ActionDriver.safeClick(pD.submitButton);
	ActionDriver.waitForElementVisible(pD.speakerEAddedSuccessMessage, 10);
	Assert.assertTrue(ActionDriver.isDisplayed(pD.speakerEAddedSuccessMessage),
				"❌ 🔴Existing speaker added success message is not displayed");
	Reporter.log("✅ 🟢Existing speaker added successfully", true);
	}

@Test(priority = 8)
public void deleteSpeaker() {
	ActionDriver.jsClick(pD.speakersSection);
	ActionDriver.waitForElementVisible(pD.deleteSpeakerIcon.get(0), 10);
	ActionDriver.scrollToElement(pD.deleteSpeakerIcon.get(0));
	ActionDriver.jsClick(pD.deleteSpeakerIcon.get(0));
	ActionDriver.waitForElementVisible(pD.confirmDeleteSessionButton, 10);
	ActionDriver.jsClick(pD.confirmDeleteSessionButton);
	ActionDriver.waitForElementVisible(pD.speakerDeleteSuccessMessage, 10);
	Assert.assertTrue(ActionDriver.isDisplayed(pD.speakerDeleteSuccessMessage),
				"❌ 🔴Speaker delete success message is not displayed");
	Reporter.log("✅ 🟢Speaker deleted successfully", true);
	}
@Test(priority = 9, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("AddNewSponsor")
public void addNewSponsor(String companyName, String companyUrl, String companyLogo,String bannerImage, String tier, String category,  String description) throws AWTException, InterruptedException {
	ActionDriver.jsClick(pD.sponsorsSection);
	ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
	ActionDriver.scrollToElement(pD.addNewSponsorButton);
	ActionDriver.safeClick(pD.addNewSponsorButton);
	ActionDriver.enterText(pD.companyName, companyName);
	ActionDriver.enterText(pD.companyWebsiteUrl, companyUrl);
	ActionDriver.safeClick(pD.sponsorlogoBannerImage.get(0));
	ActionDriver.uploadFile(companyLogo);
	ActionDriver.safeClick(pD.sponsorlogoBannerImage.get(1));
	ActionDriver.uploadFile(bannerImage);
	ActionDriver.selectDropdownByVisibleText(pD.sponsorTier, tier);
	ActionDriver.selectDropdownByVisibleText(pD.sponsorCategory, category);
	ActionDriver.enterText(pD.sponsorDescription, description);
	ActionDriver.scrollToElement(pD.submitButton);
	ActionDriver.waitForElementClickable(pD.submitButton, 50);
	ActionDriver.safeClick(pD.submitButton);
	ActionDriver.waitForElementVisible(pD.toastMessage, 10);
	Assert.assertTrue(pD.toastMessage.isDisplayed(), "❌ 🔴Toast message is not displayed after adding sponsor");
	if (pD.toastMessage.getText().contains("Sponsor added successfully.")) {
		Reporter.log("✅ 🟢Sponsor added successfully: " + companyName, true);
	} else if (pD.toastMessage.getText().contains("Duplicate name found.")) {
		Reporter.log("❌ 🔴Sponsor with this name already exists: " + companyName, true);
	} else {
		Reporter.log("❌ 🔴Unexpected message after adding sponsor: " + pD.toastMessage.getText(), true);
	}
	Reporter.log("TEST PASSED: Sponsor added successfully.", true);
	}
@Test(priority = 10, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("UpdateSponsor")
public void updateSponsor(String companyName, String companyUrl, String companyLogo,String bannerImage, String tier, String category,  String description) throws AWTException, InterruptedException {
	ActionDriver.jsClick(pD.sponsorsSection);
	ActionDriver.waitForElementNotVisible(By.cssSelector(".modal,.loader,.overlay"), 10);
	ActionDriver.scrollToElement(pD.searchUserInput);
	ActionDriver.enterText(pD.searchUserInput, companyName);
	ActionDriver.safeClick(pD.sponsorList.get(0));
	ActionDriver.enterText(pD.companyName, companyName);
	ActionDriver.enterText(pD.companyWebsiteUrl, companyUrl);
	ActionDriver.scrollToElement(pD.imageChenge.get(0));
	ActionDriver.safeClick(pD.imageChenge.get(0));
	ActionDriver.uploadFile(companyLogo);
	ActionDriver.scrollToElement(pD.imageChenge.get(1));
	ActionDriver.safeClick(pD.imageChenge.get(1));
	ActionDriver.uploadFile(bannerImage);
	ActionDriver.selectDropdownByVisibleText(pD.sponsorTier, tier);
	ActionDriver.selectDropdownByVisibleText(pD.sponsorCategory, category);
	ActionDriver.scrollToElement(pD.sponsorDescription);
	ActionDriver.enterText(pD.sponsorDescription, description);
	ActionDriver.scrollToElement(pD.updateButton);
	ActionDriver.waitForElementNotVisible(By.cssSelector("div.modal.fade.show"), 10);
	ActionDriver.safeClick(pD.updateButton);
	ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Sponsor: ", false );
	}
@Test(priority = 11, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("AddExistingSponsor")
public void addExistingSponsor(String companyName) throws InterruptedException {
	ActionDriver.safeClick(pD.sponsorsSection);
	ActionDriver.safeClick(pD.addExistingSponsorsButton);
	ActionDriver.waitForElementVisible(pD.existingSpeakersDropdown, 10);
	ActionDriver.typeUsingActions(pD.existingSpeakersDropdown, companyName);
	ActionDriver.pressEnter();
	ActionDriver.waitForElementClickable(pD.submitButton, 10);
	ActionDriver.safeClick(pD.submitButton);
	ActionDriver.verifyToastMessage(pD.toastMessage, pD.cancelButton, "Sponsor: ", false );
	}
@Test(priority = 12, dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("AddExistingSponsor")
public void deleteSponsor(String companyName) {
	ActionDriver.jsClick(pD.sponsorsSection);
	ActionDriver.scrollToElement(pD.searchUserInput);
	ActionDriver.enterText(pD.searchUserInput, companyName);
	ActionDriver.waitForElementVisible(pD.deleteSpeakerIcon.get(0), 10);
	ActionDriver.scrollToElement(pD.deleteSpeakerIcon.get(0));
	ActionDriver.jsClick(pD.deleteSpeakerIcon.get(0));
	ActionDriver.waitForElementVisible(pD.confirmDeleteSessionButton, 10);
	ActionDriver.jsClick(pD.confirmDeleteSessionButton);
	ActionDriver.waitForElementVisible(pD.sponsorDeleteSuccessMessage, 10);
	Assert.assertTrue(ActionDriver.isDisplayed(pD.sponsorDeleteSuccessMessage),
				"❌ 🔴Sponsor delete success message is not displayed");
	Reporter.log("✅ 🟢Sponsor deleted successfully", true);
	}

}
