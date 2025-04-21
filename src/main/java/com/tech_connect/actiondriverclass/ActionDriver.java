package com.tech_connect.actiondriverclass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tech_connect.baseclass.BaseDriver;

public class ActionDriver extends BaseDriver  {
	
	public static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	public static JavascriptExecutor js = (JavascriptExecutor) driver;
	public static Actions actions = new Actions(driver);

//Method to highlight an element with a specific border color
	public static void highlightElement(WebElement element, String color) {
		js.executeScript("arguments[0].style.border='3px solid " + color + "'", element);
	}

//Method to click an element
	public static void click(WebElement element) {
		try {
			waitForElementToBeClickable(element);
			element.click();
		} catch (Exception e) {
			System.out.println("Unable to click element: " + e.getMessage());
		}
	}

//Method to enter text into input field
	public static void enterText(WebElement element, String value) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			System.out.println("Unable to enter the value in input box: " + e.getMessage());
		}
	}

//WaitForElement to be clickable
	public static void waitForElementToBeClickable(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			System.out.println("Element is not clickable: " + e.getMessage());
		}
	}

//Method to get text from an input field
	public static String getText(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return element.getText();
		} catch (Exception e) {
			System.out.println("Unable to getText: " + e.getMessage());
			return "";
		}
	}

//Wait for the Page to Load
	public static void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(webDriver -> ((JavascriptExecutor) webDriver)
					.executeScript("return document.readyState").equals("complete"));
			System.out.println("Page loaded successfully.");
		} catch (Exception e) {
			System.out.println("Page did not load within " + timeOutInSec + " seconds. Exception: " + e.getMessage());
		}
	}

//Wait for element to be Visible
	public static void waitForElementToBeVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			System.out.println("Element is not visible: " + e.getMessage());
		}
	}

//Scroll to an element
	public static void scrollToElement(WebElement element) {
		try {
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			System.out.println("Unale to locate element: " + e.getMessage());
		}
	}

//Check if an element is Displayed
	public static boolean isDisplayed(WebElement element) {
		try {
			waitForElementToBeVisible(element);
			return element.isDisplayed();
		} catch (Exception e) {
			System.out.println("Element is not displayed: " + e.getMessage());
			return false;
		}
	}

//Implicit wait
	public static void implicitWait() {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		} catch (Exception e) {
			System.out.println("Unable to wait for element: " + e.getMessage());
		}
	}

//JavaScript click Method
	public static void jsClick(WebElement element) {
		try {
			waitForElementToBeClickable(element);
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			System.out.println("Unable to jsclick element: " + e.getMessage());
		}
	}

//Robot class to press enter
	public static void pressEnter() {
		try {
			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			System.out.println("Unable to Press enter error: " + e.getMessage());
		}
	}

//Double click an elemnt
	public static void douvleClick(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			actions.doubleClick(element).perform();
		} catch (Exception e) {
			System.out.println("Unable to double click: " + e.getMessage());
		}
	}

//Right click (context click) an element
	public static void rightClick(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			actions.contextClick(element).perform();
		} catch (Exception e) {
			System.out.println("Unable to right click: " + e.getMessage());
		}
	}

//Drag and Drop from source to target
	public static void dragAndDrop(WebElement source, WebElement target) {
		try {
			wait.until(ExpectedConditions.visibilityOf(source));
			actions.dragAndDrop(source, target).perform();
		} catch (Exception e) {
			System.out.println("Unable to drag and drop: " + e.getMessage());
		}
	}

//Hover over an element
	public static void hoverOverElement(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			actions.moveToElement(element).perform();
		} catch (Exception e) {
			System.out.println("Unable to hover over element: " + e.getMessage());
		}
	}

//Scroll to bottom of the page
	public static void scrollToBottom() {
		try {
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		} catch (Exception e) {
			System.out.println("Unable to scroll to bottom: " + e.getMessage());
		}
	}

//Scroll to the top of the page
	public static void scrollToTop() {
		try {
			js.executeScript("window.scrollTo(0, 0);");
		} catch (Exception e) {
			System.out.println("Unable to scroll to top: " + e.getMessage());
		}
	}

//Refresh the page
	public static void refreshPage() {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			System.out.println("Unable to refresh page: " + e.getMessage());
		}
	}

//Enter Text value used js
	public static void setInputValue(WebElement element, String value) {
		wait.until(ExpectedConditions.visibilityOf(element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", element,
				value);
	}


}
