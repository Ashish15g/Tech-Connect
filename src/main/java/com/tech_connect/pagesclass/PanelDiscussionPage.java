package com.tech_connect.pagesclass;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PanelDiscussionPage {
	
	public PanelDiscussionPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	// WebElement declarations here, e.g.:
	// Navigation & Actions
	public @FindBy(xpath = "//span[contains(text(),'Events')]")
	WebElement eventsSection;
	public @FindBy(xpath = "//span[contains(text(),'Panel Discussion')]")
	WebElement panelDiscussionsSection;
	public @FindBy(xpath = "//button[text()='Add New']")
	WebElement addNewButton;
	public @FindBy(xpath = "//select[@class='form-control']")
	List<WebElement> eventCategory;
	public @FindBy(name = "name")
	WebElement eventName;
	public @FindBy(name = "poweredBy")
	WebElement poweredBy;
	public @FindBy(xpath = "//span[text()='Create New']")
	WebElement createNewPButton;
	public @FindBy(xpath = "//div[@role='textbox']")
	WebElement description;
	public @FindBy(xpath = "//div[@class=' css-1hwfws3']")
	WebElement industryTags;
	public @FindBy(name = "location")
	WebElement location;
	//Start Date and End Date
	public @FindBy(xpath="(//input[@class='form-control'])[3]")
    WebElement start_date;
	public @FindBy(xpath="(//input[@class='form-control'])[4]")
    WebElement end_date;
	public @FindBy(xpath = "(//th[@class='rdtSwitch'])[1]")
     WebElement start_monthElem;
     //Next and Previous buttons for the start date < and >
     public @FindBy(xpath = "//div[@class='rdt rdtOpen']//span[contains(text(),'›')]")
     WebElement start_nextButton;
     public @FindBy(xpath = "//div[@class='rdt rdtOpen']//span[contains(text(),'‹')]")
     WebElement start_previousButton;
     
     public @FindBy(xpath = "//td[@class='rdtDay']")
     List<WebElement> dateElements;
     
     public @FindBy(xpath = "(//th[@class='rdtSwitch'])[2]")
     WebElement end_monthElem;
     
     public @FindBy(xpath = "(//th[@class='rdtNext'])[2]")
     WebElement end_nextButton;

	public @FindBy(xpath = "//div[@class='fui-avatar-label'][contains(text(),'Select image')]")
	WebElement webinarImage ;
	public @FindBy(xpath = "//div[@class='mb-3'][9]")
    WebElement outside_Click;
	public @FindBy(name = "eventUrl")
	WebElement eventUrl;
	public @FindBy(name = "zoomLink")
	WebElement zoomLink;
	public @FindBy(xpath = "//button[text()='Submit']")
	WebElement submitButton;
	public @FindBy(xpath = "//button[text()='Reset']")
	WebElement resetButton;
	public @FindBy(xpath = "//div[contains(text(),'new panel discussion created successfully.')]")
	WebElement panelDiscussionAddSuccessMessage;
	//Create a session to get started
	public @FindBy(xpath = "//small[text()='Sessions']")
	WebElement createSessionButton;
	public @FindBy(xpath = "//input[@name='title']")
	WebElement sessionTitle;
	public @FindBy(xpath = "//select[@name='speakerId']")
	WebElement sessionSpeakerDropdown;
	//WebElements for the session start and end date
	public @FindBy(xpath = "(//input[@class='form-control'])[2]")
	WebElement sessionStartDate;
	public @FindBy(xpath = "(//input[@class='form-control'])[3]")
	WebElement sessionEndDate;
	public @FindBy(xpath = "//div[text()='Session added successfully.']")
	WebElement sessionAddSuccessMessage;
	public @FindBy(xpath = "//div[text()='Session updated successfully.']")
	WebElement sessionUpdateSuccessMessage;
	//Nevigation to the Webinars details/Delete section
	public @FindBy(xpath = "//button[starts-with(@id, 'dropdownMenuButton-') and @type='button']")
	List<WebElement> menuButton;
	public @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-end show']//a[@title='Details'][normalize-space()='Details']")
	WebElement panelDiscussionDetails;
	public @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-end show']//button[@title='Delete'][normalize-space()='Delete']")
	WebElement deletePanelDiscussion;
	public @FindBy(xpath = "//button[text()='Yes, remove it!']")
	WebElement confirmDeletepanelDiscussionsection;
	public @FindBy(xpath = "//button[text()='Yes, delete it!']")
	WebElement confirmDeleteButton;
	public @FindBy(xpath = "//div[contains(text(),'Panel discussion deleted successfully.')]")
	WebElement panelDiscussionDeleteSuccessMessage;
	public @FindBy(xpath = "//button[text()='Cancel']")
	WebElement cancelButton;

	
}
