package com.tech_connect.pagesclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.tech_connect.actiondriverclass.ActionDriver;

public class Login {
	public Login(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Login WebElements
	public @FindBy(xpath = "//input[@name='email']")
	WebElement user_id;
	public @FindBy(xpath = "//input[@name='password']")
	WebElement user_password;
	public @FindBy(xpath = "//button[text()='Login']")
	WebElement loginButton;
	//LogOut WebElement
	public @FindBy(xpath = "//span[text()='T']")
	WebElement nvigetSinOut;
	public @FindBy(xpath = "//button[text()='Logout']")
	WebElement logOutButton;
	
	
	public void loginValidation(String id, String pass) {
		ActionDriver.highlightElement(user_id, "Blue");
		ActionDriver.enterText(user_id, id);
		ActionDriver.enterText(user_password, pass);
		ActionDriver.click(loginButton);
	}
	
	public void logOutValidation(){
		ActionDriver.highlightElement(nvigetSinOut, "Blue");
		ActionDriver.safeClick(nvigetSinOut);
		ActionDriver.safeClick(logOutButton);
	}
}
