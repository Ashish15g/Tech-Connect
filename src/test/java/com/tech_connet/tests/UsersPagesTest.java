package com.tech_connet.tests;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.tech_connect.baseclass.BaseClass;
import com.tech_connect.pagesclass.UsersPage;
import com.tech_connect.utilitiesclass.GetExcelData;
import com.tech_connect.actiondriverclass.ActionDriver;

public class UsersPagesTest extends BaseClass {

@Test(priority = 1)
	public void validateRequiredFields() throws InterruptedException {
		UsersPage usersPage = new UsersPage(driver);
		ActionDriver.click(usersPage.usersSection);
		ActionDriver.click(usersPage.addNewUser);
		ActionDriver.click(usersPage.submitButton);
		Assert.assertTrue(usersPage.name_is_required.isDisplayed(), "Name validation message is missing");
		Assert.assertTrue(usersPage.email_is_required.isDisplayed(), "Email validation message is missing");
		Assert.assertTrue(usersPage.compzny_is_required.isDisplayed(), "Company validation message is missing");
		Assert.assertTrue(ActionDriver.isDisplayed(usersPage.job_is_required),
				"Job Title validation message is missing");

		ActionDriver.click(usersPage.cancelButton);

	}

@Test(priority = 2)
	public void addUserWithValidData() throws Exception {

		UsersPage usersPage = new UsersPage(driver);
		ActionDriver.click(usersPage.usersSection);
		ActionDriver.click(usersPage.addNewUser);

		// String uniqueEmail = "john.doe" + System.currentTimeMillis() +
		// "@example.com";
		String userName = GetExcelData.excelData("UserDetails", 1, 0);
		System.out.println("User Name: " + userName);
		ActionDriver.enterText(usersPage.name, userName);
		String uniqueEmail = GetExcelData.excelData("UserDetails", 1, 1);
		ActionDriver.enterText(usersPage.email, uniqueEmail);
		String companyName = GetExcelData.excelData("UserDetails", 1, 2);
		ActionDriver.enterText(usersPage.company, companyName);
		String jobTitle = GetExcelData.excelData("UserDetails", 1, 3);
		ActionDriver.enterText(usersPage.jobTitle, jobTitle);

		ActionDriver.click(usersPage.submitButton);
		SoftAssert ass = new SoftAssert();
		ass.assertTrue(ActionDriver.isDisplayed(usersPage.user_Add_Sufull), "Success message not displayed");
		// ActionDriver.click(usersPage.cancelButton);
		// ass.assertAll();
	}

//Import Users (Click to select or drag and drop an Excel file to upload.).
@Test
	public void importUsersWithExcleFile() throws AWTException, InterruptedException {
		UsersPage usersPage = new UsersPage(driver);
		ActionDriver.click(usersPage.usersSection);
		ActionDriver.click(usersPage.importUsersButton);
		ActionDriver.click(usersPage.importInputFile);

		StringSelection filepathSection = new StringSelection(
				"C:\\EclipseJava\\Tech-Connect\\src\\test\\resources\\testData\\TC_Import_User_Data.xlsx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filepathSection, null);
		Thread.sleep(5000);
		ActionDriver.pressEnter1();
		Thread.sleep(5000);
		ActionDriver.click(usersPage.submitButton);

		// Wait for success message or confirmation element (adjust as needed)
//    ActionDriver.waitForElementVisible(usersPage.user_Add_Sufull, 10);
//    Assert.assertTrue(ActionDriver.isDisplayed(usersPage.user_Add_Sufull), "User import success message not displayed.");
	}

	@Test(priority = 3)
	public void addUserWithDuplicateEmail() throws Exception {
		UsersPage usersPage = new UsersPage(driver);
		ActionDriver.click(usersPage.usersSection);

		ActionDriver.click(usersPage.addNewUser);
		String userName = GetExcelData.excelData("UserDetails", 1, 0);
		ActionDriver.enterText(usersPage.name, userName);
		String uniqueEmail = GetExcelData.excelData("UserDetails", 1, 1);
		ActionDriver.enterText(usersPage.email, uniqueEmail); // existing email
		String companyName = GetExcelData.excelData("UserDetails", 1, 2);
		ActionDriver.enterText(usersPage.company, companyName);
		String jobTitle = GetExcelData.excelData("UserDetails", 1, 3);
		ActionDriver.enterText(usersPage.jobTitle, jobTitle);

		ActionDriver.click(usersPage.submitButton);
		SoftAssert ass = new SoftAssert();
		ass.assertTrue(ActionDriver.isDisplayed(usersPage.email_al_registered),
				"Duplicate email error message not displayed");
		Thread.sleep(2000);
		ActionDriver.click(usersPage.cancelButton);
		ass.assertAll();
	}

@Test
	public void updateUserDetails() throws Exception {
		UsersPage usersPage = new UsersPage(driver);

		// Navigate to Users section
		ActionDriver.click(usersPage.usersSection);

		// Click first user from the list
		ActionDriver.click(usersPage.userList.get(0));

		// Update mobile number
		String userMobile = GetExcelData.excelData("UserDetails", 1, 4);
		ActionDriver.enterText(usersPage.userMobileField, userMobile);

		// Scroll and select Country
		ActionDriver.scrollToElement(usersPage.uCountryDropdown.get(0));
		ActionDriver.click(usersPage.uCountryDropdown.get(0));
		ActionDriver.pressEnter();

		// Select State
//    ActionDriver.scrollToElement(usersPage.StateDropdown.get(0));
//    ActionDriver.click(usersPage.StateDropdown.get(0)); // Adjust logic if dropdown is dynamic
//    ActionDriver.pressEnter();

		// Before accessing index 1, check the size
		if (usersPage.uStateDropdown.size() > 1) {
			ActionDriver.scrollToElement(usersPage.uStateDropdown.get(1));
			ActionDriver.click(usersPage.uStateDropdown.get(1));
		} else if (usersPage.uStateDropdown.size() > 0) {
			ActionDriver.scrollToElement(usersPage.uStateDropdown.get(0));
			ActionDriver.click(usersPage.uStateDropdown.get(0));
		} else {
			throw new RuntimeException("No state dropdown options available");
		}
		ActionDriver.pressEnter();

		// Select City
		ActionDriver.scrollToElement(usersPage.uCityDropdown.get(0));
		ActionDriver.click(usersPage.uCityDropdown.get(0)); // Adjust logic if dropdown is dynamic
		ActionDriver.pressEnter();

		// Enter Postal Code
		String userPostalCode = GetExcelData.excelData("UserDetails", 1, 8);
		ActionDriver.enterText(usersPage.uPostalCodeField, userPostalCode);

		// Click Update
		ActionDriver.click(usersPage.updateButton);

		// Optional: Verify update message or element state
		// Assert.assertTrue(usersPage.successMessage.isDisplayed(), "Update success
		// message not displayed.");
	}

@Test
	public void deleteMultipleUsersTest() {
		UsersPage usersPage = new UsersPage(driver);
		ActionDriver.click(usersPage.usersSection);

		int usersToDelete = usersPage.deleteIcon.size();
		for (int i = 0; i < usersToDelete; i++) {
			// Always delete the first user in the list as the list updates after each
			// deletion
			ActionDriver.click(usersPage.deleteIcon.get(0));
			ActionDriver.click(usersPage.deleteConfirmButton);
			ActionDriver.waitForElementVisible(usersPage.userDelSufullyMsg, 10);
			Assert.assertTrue(ActionDriver.isDisplayed(usersPage.userDelSufullyMsg),
					"User deletion success message not displayed.");
			// Optionally, add a small wait if needed for UI to update
			// Thread.sleep(1000);
		}
	}
@Test
	public void deleteOne1UserTest() {
		UsersPage usersPage = new UsersPage(driver);
		ActionDriver.click(usersPage.usersSection);
		ActionDriver.click(usersPage.deleteIcon.get(0));
		ActionDriver.click(usersPage.deleteConfirmButton);
		ActionDriver.waitForElementVisible(usersPage.userDelSufullyMsg, 10);
		Assert.assertTrue(ActionDriver.isDisplayed(usersPage.userDelSufullyMsg),
				"User deletion success message not displayed.");
	}
}