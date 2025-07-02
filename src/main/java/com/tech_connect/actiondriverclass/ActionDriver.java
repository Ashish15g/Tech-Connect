package com.tech_connect.actiondriverclass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tech_connect.baseclass.BaseDriver;
import com.tech_connect.utilitiesclass.GetpropData;

public class ActionDriver extends BaseDriver {

	public static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	public static JavascriptExecutor js = (JavascriptExecutor) driver;
	public static Actions actions = new Actions(driver);

	// Waits for the specified element to be visible within the given timeout
	public static boolean waitForElementVisible(WebElement element, int timeoutInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.visibilityOf(element)) != null;
	}

	// Waits for the specified element to be clickable within the given timeout
	public static WebElement waitForElementClickable(WebElement element, int timeoutInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	// Waits for an element located by the locator to be present in the DOM
	public static WebElement waitForElementPresent(By locator, int timeoutInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	// Waits until the specified element is not visible
	public static void waitForElementNotVisible(By locator, int timeoutInSeconds) {
	    try {
	        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
	            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
	    } catch (Exception e) {
	        System.out.println("Overlay not gone: " + e.getMessage());
	    }
	}

	// Waits until the specified element becomes invisible
	public static boolean waitForElementInvisible(WebElement element, int timeoutInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.invisibilityOf(element));
	}

	// Highlights the element by adding a border with the specified color
	public static void highlightElement(WebElement element, String color) {
		js.executeScript("arguments[0].style.border='3px solid " + color + "'", element);
	}

	// Clicks on a web element, with visual highlight
	public static void click(WebElement element) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementClickable(element, seconds);
			highlightElement(element, "green");
			element.click();
		} catch (Exception e) {
			System.out.println("Unable to click element: " + e.getMessage());
		}
	}

	// Sends text to an input field after clearing its content
	public static void enterText(WebElement element, String value) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			element.clear();
			highlightElement(element, "blue");
			element.sendKeys(value);
		} catch (Exception e) {
			System.out.println("Unable to enter text: " + e.getMessage());
		}
	}

	// Retrieves and returns visible text from the element
	public static String getText(WebElement element) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			return element.getText();
		} catch (Exception e) {
			System.out.println("Unable to get text: " + e.getMessage());
			return "";
		}
	}

	// Waits for page load completion
	public static void waitForPageLoad(int timeoutInSeconds) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
					.until(driver -> js.executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			System.out.println("Page did not load in time: " + e.getMessage());
		}
	}

	// Scrolls to a specific web element
	public static void scrollToElement(WebElement element) {
		try {
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			System.out.println("Unable to scroll to element: " + e.getMessage());
		}
	}

	// Returns true if the element is displayed
	public static boolean isDisplayed(WebElement element) {
		try {
			waitForElementVisible(element,200);
			return element.isDisplayed();
		} catch (Exception e) {
			System.out.println("Element is not displayed: " + e.getMessage());
			return false;
		}
	}

	// Applies implicit wait based on config
	public static void implicitWait() {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("implicitWait"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
		} catch (Exception e) {
			System.out.println("Unable to apply implicit wait: " + e.getMessage());
		}
	}

	// JavaScript-based click
	public static void jsClick(WebElement element) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementClickable(element, seconds);
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			System.out.println("JS click failed: " + e.getMessage());
		}
	}

	// Presses Ctrl+V and Enter using Robot
	public static void pressEnter() {
		try {
			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			System.err.println("Unable to press Enter: " + e.getMessage());
		}
	}

	public static void pressEnter1() {
		try {
			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			System.out.println("Robot action failed: " + e.getMessage());
		}
	}
	// Uploads a file using system clipboard and Robot
	public static void uploadFile(String filePath) throws InterruptedException, AWTException {
	    try {
	        StringSelection filePathSelection = new StringSelection(filePath);
	        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePathSelection, null);
	        Thread.sleep(1000);

	        Robot rb = new Robot();
	        rb.keyPress(KeyEvent.VK_CONTROL);
	        rb.keyPress(KeyEvent.VK_V);
	        rb.keyRelease(KeyEvent.VK_V);
	        rb.keyRelease(KeyEvent.VK_CONTROL);

	        Thread.sleep(500);
	        rb.keyPress(KeyEvent.VK_ENTER);
	        rb.keyRelease(KeyEvent.VK_ENTER);
	    } catch (Exception e) {
	        System.out.println("File upload failed: " + e.getMessage());
	    }
	}

	// Type text into an element using Actions class
	public static void typeUsingActions(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			actions.moveToElement(element).click().sendKeys(text).build().perform();
		} catch (Exception e) {
			System.out.println("Unable to type using Actions: " + e.getMessage());
		}
	}
	
	// Double-clicks on a web element
	public static void doubleClick(WebElement element) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			actions.doubleClick(element).perform();
		} catch (Exception e) {
			System.out.println("Double click failed: " + e.getMessage());
		}
	}

	// Right-click (context click) on element
	public static void rightClick(WebElement element) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			actions.contextClick(element).perform();
		} catch (Exception e) {
			System.out.println("Right click failed: " + e.getMessage());
		}
	}

	// Drag element from source to target
	public static void dragAndDrop(WebElement source, WebElement target) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(source, seconds);
			actions.dragAndDrop(source, target).perform();
		} catch (Exception e) {
			System.out.println("Drag and drop failed: " + e.getMessage());
		}
	}

	// Hover mouse over an element
	public static void hoverOverElement(WebElement element) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			actions.moveToElement(element).perform();
		} catch (Exception e) {
			System.out.println("Hover failed: " + e.getMessage());
		}
	}

	// Scrolls to bottom of page
	public static void scrollToBottom() {
		try {
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		} catch (Exception e) {
			System.out.println("Scroll to bottom failed: " + e.getMessage());
		}
	}

	// Scrolls to top of page
	public static void scrollToTop() {
		try {
			js.executeScript("window.scrollTo(0, 0);");
		} catch (Exception e) {
			System.out.println("Scroll to top failed: " + e.getMessage());
		}
	}

	// Refreshes the current page
	public static void refreshPage() {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			System.out.println("Page refresh failed: " + e.getMessage());
		}
	}

	// Sets value of input field using JavaScript
	public static void setInputValue(WebElement element, String value) throws NumberFormatException, IOException {
		int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
		try {

		} catch (NumberFormatException e) {
			System.out.println("Invalid explicit wait time, using default: " + e.getMessage());
			seconds = 30; // Default fallback value
		}
		waitForElementVisible(element, seconds);
		js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", element,
				value);
	}

	// Combines regular and JS click as fallback
	public static void safeClick(WebElement element) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementClickable(element, seconds);
			highlightElement(element, "green");
			element.click();
		} catch (Exception e1) {
			System.out.println("Standard click failed: " + e1.getMessage());
			try {
				js.executeScript("arguments[0].click();", element);
			} catch (Exception e2) {
				System.out.println("JS click also failed: " + e2.getMessage());
			}
		}
	}

	// Select dropdown by visible text
	public static void selectDropdownByVisibleText(WebElement element, String value) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			highlightElement(element, "orange");
			Select select = new Select(element);
			select.selectByVisibleText(value);
		} catch (Exception e) {
			System.out.println("Unable to select by visible text: " + e.getMessage());
		}
	}

	// Select dropdown by value attribute
	public static void selectDropdownByValue(WebElement element, String value) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			highlightElement(element, "orange");
			Select select = new Select(element);
			select.selectByValue(value);
		} catch (Exception e) {
			System.out.println("Unable to select by value: " + e.getMessage());
		}
	}

	// Select dropdown by index
	public static void selectDropdownByIndex(WebElement element, int index) {
		try {
			int seconds = Integer.parseInt(GetpropData.propData("explicitWait"));
			waitForElementVisible(element, seconds);
			highlightElement(element, "orange");
			Select select = new Select(element);
			select.selectByIndex(index);
		} catch (Exception e) {
			System.out.println("Unable to select by index: " + e.getMessage());
		}
	}

	// Get the currently selected option (for validation or debugging)
	public static String getSelectedOption(WebElement element) {
		try {
			Select select = new Select(element);
			return select.getFirstSelectedOption().getText();
		} catch (Exception e) {
			System.out.println("Unable to get selected option: " + e.getMessage());
			return "";
		}
	}

	// Deselect all options (only applicable for multi-select dropdowns)
	public static void deselectAllOptions(WebElement element) {
		try {
			Select select = new Select(element);
			if (select.isMultiple()) {
				select.deselectAll();
			}
		} catch (Exception e) {
			System.out.println("Unable to deselect options: " + e.getMessage());
		}
	}
}
