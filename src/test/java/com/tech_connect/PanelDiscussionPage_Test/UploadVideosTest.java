package com.tech_connect.PanelDiscussionPage_Test;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.tech_connect.actiondriverclass.ActionDriver;
import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.PanelDiscussionPage;
import com.tech_connect.utilitiesclass.CommonDataProvider;
import com.tech_connect.utilitiesclass.SheetName;

public class UploadVideosTest extends BaseClass{
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
@Test(dataProvider = "excelDataProvider", dataProviderClass = CommonDataProvider.class)
@SheetName("videosPath")
public void uploadVideos(String videosPath) throws InterruptedException, AWTException {
	ActionDriver.safeClick(pD.videosSection);
	ActionDriver.safeClick(pD.addVideoButton);
	ActionDriver.uploadFile(videosPath);
	ActionDriver.jsClick(pD.uploadVideoButton);
	ActionDriver.waitForElementVisible(pD.confirmUploadVideoButton, 10);
	ActionDriver.jsClick(pD.confirmUploadVideoButton);
	ActionDriver.waitForElementVisible(pD.videoUploadSuccessMessage, 20);
	Assert.assertTrue(ActionDriver.isDisplayed(pD.videoDeleteSuccessMessage),
			"❌ 🔴Video delete success message is not displayed");
	Reporter.log("✅ 🟢Video uploaded successfully", true);
	}
@Test(priority = 2)
public void deleteWebinarVideo() {
	ActionDriver.jsClick(pD.videosSection);
	ActionDriver.waitForElementVisible(pD.deleteVideoButton, 10);
	ActionDriver.safeClick(pD.deleteVideoButton);
	ActionDriver.waitForElementVisible(pD.confirmDeleteVideoButton, 10);
	ActionDriver.safeClick(pD.confirmDeleteVideoButton);
	Assert.assertTrue(ActionDriver.isDisplayed(pD.videoDeleteSuccessMessage),
			"❌ 🔴Video delete success message is not displayed");
	Reporter.log("✅ 🟢Video deleted successfully", true);
	}
}
