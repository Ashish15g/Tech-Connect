package com.tech_connect.pagesclass;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UsersPage {
	
	public UsersPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	// Navigation & Actions
	public @FindBy(xpath = "//a[@href='/users']")
	WebElement usersSection;
	public @FindBy(xpath = "//button[text()='Add New User']")
	WebElement addNewUser;
	// Import Users
	public @FindBy(xpath = "//button[text()='Import Users']")
	WebElement importUsersButton;
	public @FindBy(className  = "my-3")
	WebElement importInputFile;
	public @FindBy(xpath = "//div[text()='Please upload a file before submitting.']")
	WebElement SubmitOnlyButton;
	public @FindBy(xpath = "//div[@class='Toastify__toast-body']/div[contains(text(), 'Some fields are missing in the file')]")
	WebElement missingFieldsInFile;
	 // Form Fields
	public @FindBy(name = "name")
	WebElement name;
	public @FindBy(name = "email")
	WebElement email;
	public @FindBy(name = "company")
	WebElement company;
	public @FindBy(name = "jobTitle")
	WebElement jobTitle;
	// Buttons
	public @FindBy(xpath = "//button[@class='btn-close']")
	WebElement btn_Close;
	public @FindBy(xpath = "//button[text()='Submit']")
	WebElement submitButton;
	public @FindBy(xpath = "//button[text()='Cancel']")
	WebElement cancelButton;
    // Validation Messages
	public @FindBy(xpath = "//div[text()='Name is required']")
	WebElement name_is_required;
	public @FindBy(xpath = "//div[text()='Email is required']")
	WebElement email_is_required;
	public @FindBy(xpath = "//div[text()='Company is required']")
	WebElement compzny_is_required;
	public @FindBy(xpath = "//div[text()='Job Title is required']")
	WebElement job_is_required;
	public @FindBy(xpath = "//div[text()='User added successfully.']")
	WebElement user_Add_Sufull;
	public @FindBy(xpath = "//div[text()='Email already registered.']")
	WebElement email_al_registered;
	//User List Add details and Delete 
	public @FindBy(xpath = "//li[@class='w-100 cursor-pointer  nav-item']")
	List<WebElement> userList;
	public @FindBy(xpath = "//i[@class='bi bi-trash']")	
	List<WebElement> deleteIcon;
	public @FindBy(xpath = "//button[text()='Yes, delete it!']")
	WebElement deleteConfirmButton;
	public @FindBy(xpath = "//div[text()='User deleted successfully.']")
	WebElement userDelSufullyMsg;
	public @FindBy(name = "name")
	WebElement userNameField;
	public @FindBy(name = "mobile")
	WebElement userMobileField;
	public @FindBy(xpath = "(//div[text()='Select...'])[1]")
	List<WebElement> uCountryDropdown;
	public @FindBy(xpath = "(//div[text()='Select...'])[1]")
	List<WebElement> uStateDropdown;
	public @FindBy(xpath = "(//div[text()='Select...'])[1]")
	List<WebElement> uCityDropdown;
	public @FindBy(name = "postalCode")
	WebElement uPostalCodeField;
	public @FindBy(xpath = "//button[text()='Update']")
	WebElement updateButton;
	public @FindBy(xpath = "//button[text()='cancel']")
	WebElement cancelButtonUpdate;
	
	

}
