package com.tech_connect.actiondriverclass;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;

import com.tech_connect.baseclass.BaseDriver;
import com.tech_connect.utilitiesclass.GetpropData;

public class ActionDriver extends BaseDriver {

    private static final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(getExplicitWait()));
    private static final JavascriptExecutor js = (JavascriptExecutor) driver;
    private static final Actions actions = new Actions(driver);

    // Fetch explicit wait from config
    private static int getExplicitWait() {
        try {
            return Integer.parseInt(GetpropData.propData("explicitWait"));
        } catch (Exception e) {
            System.out.println("⚠️ Unable to fetch explicit wait: " + e.getMessage());
            return 2000;
        }
    }

    // Fetch implicit wait from config
    private static int getImplicitWait() {
        try {
            return Integer.parseInt(GetpropData.propData("implicitWait"));
        } catch (Exception e) {
            System.out.println("⚠️ Unable to fetch implicit wait: " + e.getMessage());
            return 2000;
        }
    }

    // Wait for element to be visible
    public static boolean waitForElementVisible(WebElement element, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            System.out.println("❌ Element not visible within timeout: " + e.getMessage());
            return false;
        }
    }

    public static WebElement waitForElementClickable(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println("❌ Element not clickable: " + e.getMessage());
            return null;
        }
    }

    public static WebElement waitForElementPresent(By locator, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("❌ Element not present in DOM: " + e.getMessage());
            return null;
        }
    }

    public static boolean waitForElementInvisible(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            System.out.println("❌ Element not invisible: " + e.getMessage());
            return false;
        }
    }

    public static void waitForElementNotVisible(By locator, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("❌ Overlay element not gone: " + e.getMessage());
        }
    }

    public static void waitForAnyElementVisible(List<WebElement> elements, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(driver -> {
                for (WebElement element : elements) {
                    try {
                        if (element != null && element.isDisplayed()) return true;
                    } catch (Exception ignored) {}
                }
                return false;
            });
        } catch (Exception e) {
            System.out.println("❌ Waiting for any element visible failed: " + e.getMessage());
        }
    }

    public static void waitForPageLoad(int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            System.out.println("❌ Page did not load completely: " + e.getMessage());
        }
    }

    // Click Methods
    public static void click(WebElement element) {
        try {
            waitForElementClickable(element, getExplicitWait());
            highlightElement(element, "green");
            element.click();
        } catch (Exception e) {
            System.out.println("❌ Element click failed: " + e.getMessage());
        }
    }

    public static void safeClick(WebElement element) {
        try {
            waitForElementClickable(element, getExplicitWait());
            highlightElement(element, "green");
            element.click();
            System.out.println("✅ Element clicked successfully using WebDriver.");
        } catch (Exception e1) {
            System.out.println("⚠️ WebDriver click failed: " + e1.getMessage());
            try {
                waitForElementVisible(element, getExplicitWait());
                highlightElement(element, "orange");
                js.executeScript("arguments[0].click();", element);
                System.out.println("✅ Element clicked successfully using JavaScript.");
            } catch (Exception e2) {
                System.out.println("❌ JS fallback click also failed: " + e2.getMessage());
                throw new RuntimeException("Both WebDriver and JS clicks failed.", e2);
            }
        }
    }


    public static void jsClick(WebElement element) {
        try {
            waitForElementClickable(element, getExplicitWait());
            highlightElement(element, "green");
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println("❌ JavaScript click failed: " + e.getMessage());
        }
    }

    // Input Methods
    public static void enterText(WebElement element, String value) {
        try {
            waitForElementVisible(element, getExplicitWait());
            implicitWait(); 
            highlightElement(element, "blue");
            element.click();
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            System.out.println("❌ Failed to enter text: " + e.getMessage());
        }
    }

    public static void setInputValue(WebElement element, String value) {
        try {
            waitForElementVisible(element, getExplicitWait());
            highlightElement(element, "blue");

            try {
                element.click();
                element.clear(); // Clear the input field using WebDriver first
                System.out.println("✅ Field cleared using WebDriver.");
            } catch (Exception e) {
                System.out.println("⚠️ WebDriver clear() failed, falling back to JS: " + e.getMessage());
                js.executeScript("arguments[0].value = ''", element);
            }

            // Set value using JavaScript and trigger input event
            js.executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));",
                element, value
            );
            System.out.println("✅ Input value set using JavaScript: " + value);
        } catch (Exception e) {
            System.out.println("❌ Setting input value failed: " + e.getMessage());
        }
    }

    public static String getText(WebElement element) {
        try {
            waitForElementVisible(element, getExplicitWait());
            return element.getText();
        } catch (Exception e) {
            System.out.println("❌ Fetching element text failed: " + e.getMessage());
            return "";
        }
    }

    public static void highlightElement(WebElement element, String color) {
        try {
            js.executeScript("arguments[0].style.border='3px solid " + color + "'", element);
        } catch (Exception e) {
            System.out.println("❌ Element highlighting failed: " + e.getMessage());
        }
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            System.out.println("❌ isDisplayed check failed: " + e.getMessage());
            return false;
        }
    }

    // Scrolling
    public static void scrollToElement(WebElement element) {
        try {
            highlightElement(element, "green");
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println("❌ Scroll to element failed: " + e.getMessage());
        }
    }

    public static void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    public static void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    // Mouse & Keyboard Actions
    public static void hoverOverElement(WebElement element) {
        try {
            waitForElementVisible(element, getExplicitWait());
            highlightElement(element, "green");
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            System.out.println("❌ Mouse hover failed: " + e.getMessage());
        }
    }

    public static void doubleClick(WebElement element) {
        try {
            waitForElementVisible(element, getExplicitWait());
            highlightElement(element, "green");
            actions.doubleClick(element).perform();
        } catch (Exception e) {
            System.out.println("❌ Double click failed: " + e.getMessage());
        }
    }

    public static void rightClick(WebElement element) {
        try {
            waitForElementVisible(element, getExplicitWait());
            highlightElement(element, "green");
            actions.contextClick(element).perform();
        } catch (Exception e) {
            System.out.println("❌ Right click failed: " + e.getMessage());
        }
    }

    public static void dragAndDrop(WebElement source, WebElement target) {
        try {
            highlightElement(source, "orange");
            actions.dragAndDrop(source, target).perform();
        } catch (Exception e) {
            System.out.println("❌ Drag and drop failed: " + e.getMessage());
        }
    }

    public static void typeUsingActions(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightElement(element, "blue");
            actions.moveToElement(element).click().sendKeys(text).build().perform();
        } catch (Exception e) {
            System.out.println("❌ Typing using actions failed: " + e.getMessage());
        }
    }

    public static void pressEnter() {
        try {
            Robot rb = new Robot();
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            System.out.println("❌ Enter key press failed: " + e.getMessage());
        }
    }

    public static void pressCtrlVAndEnter() {
        try {
            Robot rb = new Robot();
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            System.out.println("❌ Ctrl+V and Enter failed: " + e.getMessage());
        }
    }

    public static void uploadFile(String filePath) {
        try {
            StringSelection selection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            Thread.sleep(1000);
            pressCtrlVAndEnter();
        } catch (Exception e) {
            System.out.println("❌ File upload failed: " + e.getMessage());
        }
    }

    // Dropdown methods
    public static void selectDropdownByVisibleText(WebElement element, String value) {
        try {
            waitForElementVisible(element, getExplicitWait());
            highlightElement(element, "orange");
            new Select(element).selectByVisibleText(value);
        } catch (Exception e) {
            System.out.println("❌ Dropdown selection by visible text failed: " + e.getMessage());
        }
    }

    public static void selectDropdownByValue(WebElement element, String value) {
        try {
            waitForElementVisible(element, getExplicitWait());
            highlightElement(element, "orange");
            new Select(element).selectByValue(value);
        } catch (Exception e) {
            System.out.println("❌ Dropdown selection by value failed: " + e.getMessage());
        }
    }

    public static void selectDropdownByIndex(WebElement element, int index) {
        try {
            waitForElementVisible(element, getExplicitWait());
            highlightElement(element, "orange");
            new Select(element).selectByIndex(index);
        } catch (Exception e) {
            System.out.println("❌ Dropdown selection by index failed: " + e.getMessage());
        }
    }

    public static String getSelectedOption(WebElement element) {
        try {
            return new Select(element).getFirstSelectedOption().getText();
        } catch (Exception e) {
            System.out.println("❌ Getting selected option failed: " + e.getMessage());
            return "";
        }
    }

    public static void deselectAllOptions(WebElement element) {
        try {
            Select select = new Select(element);
            if (select.isMultiple()) {
                select.deselectAll();
            }
        } catch (Exception e) {
            System.out.println("❌ Deselect all options failed: " + e.getMessage());
        }
    }

    public static void implicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getImplicitWait()));
    }
    public static void verifyToastMessage(WebElement toastMessage, WebElement cancelButton, String entityName, boolean hasCancelButton) throws InterruptedException {
        try {
            waitForElementVisible(toastMessage, getExplicitWait());
            highlightElement(toastMessage, "green");
            String message = toastMessage.getText().toLowerCase().trim();

            if (message.contains("success")) {
                Reporter.log("💯 " + entityName + " " + message, true);
            } else if (message.contains("duplicate") || message.contains("already exists")) {
                Reporter.log("⚠️ Duplicate " + entityName + " " + message, true);
                if (hasCancelButton) {
                    try {
                        waitForElementVisible(cancelButton, getExplicitWait());
                        highlightElement(cancelButton, "orange");
                        cancelButton.click();
                        Reporter.log("🛑 Form closed after duplicate toast.", true);
                    } catch (Exception e) {
                        Reporter.log("⚠️ Cancel button was expected but not clickable: " + e.getMessage(), true);
                    }
                } else {
                    Reporter.log("ℹ️ Cancel button not available. Skipping form close.", true);
                }
            } else {
                Reporter.log("❌ Unexpected toast message for " + entityName + ": " + message, true);
                throw new AssertionError("Unexpected toast message: " + message);
            }
        } catch (Exception e) {
            Reporter.log("❌ Toast verification failed for " + entityName + ": " + e.getMessage(), true);
            throw e;
        }
    }


}
