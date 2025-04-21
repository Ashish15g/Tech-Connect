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
	
	public @FindBy(xpath = "//input[@name='email']")
	WebElement user_id;
	public @FindBy(xpath = "//input[@name='password']")
	WebElement user_password;
	public @FindBy(xpath = "//button[text()='Login']")
	WebElement loginButton;
	
	public void loginValidation(String id, String pass) {
		ActionDriver.highlightElement(user_id, "yellow");
		ActionDriver.enterText(user_id, id);
		ActionDriver.enterText(user_password, pass);
		ActionDriver.click(loginButton);
	}
}
