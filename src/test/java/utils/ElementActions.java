package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ElementActions centralizes robust, higher-level WebElement interactions.
 * <p>
 * Each action:
 * - Waits for a safe precondition (visible/clickable) via {@link WaitUtils}
 * - Wraps Selenium calls with consistent error handling
 * - Attaches diagnostic details (screenshot + message) to Extent on failure
 * <p>
 * All methods are static for ease of use inside Page Objects and Steps.
 *
 * @author NiteshJainQaTestology
 */
public final class ElementActions {

    private ElementActions() {}

    /**
     * Clicks the element located by the given locator after waiting until it is clickable.
     *
     * @param driver  current {@link WebDriver}
     * @param locator element locator
     * @throws RuntimeException when the click fails (also attaches evidence to the report)
     */
    public static void click(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForClickable(driver, locator);
            element.click();
        } catch (Exception e) {
            attachFailure("click", locator, e);
            throw new RuntimeException("Failed to click element: " + locator, e);
        }
    }

    /**
     * Clears the element and types the provided text.
     *
     * @param driver  current {@link WebDriver}
     * @param locator element locator
     * @param text    text to type
     */
    public static void sendKeys(WebDriver driver, By locator, CharSequence text) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            clearWithSelectAll(element);
            element.sendKeys(text);
        } catch (Exception e) {
            attachFailure("sendKeys", locator, e);
            throw new RuntimeException("Failed to sendKeys to element: " + locator, e);
        }
    }

    /**
     * Gets the visible text of the element.
     *
     * @param driver  current {@link WebDriver}
     * @param locator element locator
     * @return text value
     */
    public static String getText(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            return element.getText();
        } catch (Exception e) {
            attachFailure("getText", locator, e);
            throw new RuntimeException("Failed to getText from element: " + locator, e);
        }
    }

    /**
     * Gets the attribute value from the element.
     *
     * @param driver    current {@link WebDriver}
     * @param locator   element locator
     * @param attribute attribute name
     * @return attribute value
     */
    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            return element.getAttribute(attribute);
        } catch (Exception e) {
            attachFailure("getAttribute", locator, e);
            throw new RuntimeException("Failed to getAttribute from element: " + locator, e);
        }
    }

    /**
     * Gets a CSS property value from the element.
     *
     * @param driver       current {@link WebDriver}
     * @param locator      element locator
     * @param propertyName CSS property name
     * @return CSS property value
     */
    public static String getCssValue(WebDriver driver, By locator, String propertyName) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            return element.getCssValue(propertyName);
        } catch (Exception e) {
            attachFailure("getCssValue", locator, e);
            throw new RuntimeException("Failed to getCssValue from element: " + locator, e);
        }
    }

    /**
     * Returns true if the element is visible and displayed; false if not found or not visible.
     */
    public static boolean isDisplayed(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            return element.isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Returns true if the element is enabled; false if not found or not visible.
     */
    public static boolean isEnabled(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            return element.isEnabled();
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Returns true if the element is selected.
     */
    public static boolean isSelected(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            return element.isSelected();
        } catch (Exception e) {
            attachFailure("isSelected", locator, e);
            throw new RuntimeException("Failed to determine selected state for element: " + locator, e);
        }
    }

    /**
     * Clears the element content.
     */
    public static void clear(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            element.clear();
        } catch (Exception e) {
            attachFailure("clear", locator, e);
            throw new RuntimeException("Failed to clear element: " + locator, e);
        }
    }

    /**
     * Submits the element/form.
     */
    public static void submit(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            element.submit();
        } catch (Exception e) {
            attachFailure("submit", locator, e);
            throw new RuntimeException("Failed to submit element/form: " + locator, e);
        }
    }

    /**
     * Types the text and then presses ENTER.
     */
    public static void typeAndEnter(WebDriver driver, By locator, CharSequence text) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            clearWithSelectAll(element);
            element.sendKeys(text);
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            attachFailure("typeAndEnter", locator, e);
            throw new RuntimeException("Failed to type and press Enter on element: " + locator, e);
        }
    }

    /**
     * Selects an option from a drop-down by its visible text.
     */
    public static void selectByVisibleText(WebDriver driver, By locator, String visibleText) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            Select select = new Select(element);
            select.selectByVisibleText(visibleText);
        } catch (Exception e) {
            attachFailure("selectByVisibleText", locator, e);
            throw new RuntimeException("Failed to selectByVisibleText on element: " + locator, e);
        }
    }

    /**
     * Selects an option from a drop-down by its value.
     */
    public static void selectByValue(WebDriver driver, By locator, String value) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            attachFailure("selectByValue", locator, e);
            throw new RuntimeException("Failed to selectByValue on element: " + locator, e);
        }
    }

    /**
     * Selects an option from a drop-down by its index.
     */
    public static void selectByIndex(WebDriver driver, By locator, int index) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            Select select = new Select(element);
            select.selectByIndex(index);
        } catch (Exception e) {
            attachFailure("selectByIndex", locator, e);
            throw new RuntimeException("Failed to selectByIndex on element: " + locator, e);
        }
    }

    /**
     * Toggles a checkbox to the desired selected state.
     *
     * @param shouldBeSelected desired selected state
     */
    public static void setCheckbox(WebDriver driver, By locator, boolean shouldBeSelected) {
        try {
            WebElement element = WaitUtils.waitForClickable(driver, locator);
            boolean selected = element.isSelected();
            if (shouldBeSelected && !selected) {
                element.click();
            } else if (!shouldBeSelected && selected) {
                element.click();
            }
        } catch (Exception e) {
            attachFailure("setCheckbox", locator, e);
            throw new RuntimeException("Failed to setCheckbox on element: " + locator, e);
        }
    }

    /**
     * Moves the mouse cursor over the element.
     */
    public static void hover(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            attachFailure("hover", locator, e);
            throw new RuntimeException("Failed to hover over element: " + locator, e);
        }
    }

    /**
     * Performs a double click on the element.
     */
    public static void doubleClick(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForClickable(driver, locator);
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
        } catch (Exception e) {
            attachFailure("doubleClick", locator, e);
            throw new RuntimeException("Failed to doubleClick element: " + locator, e);
        }
    }

    /**
     * Performs a context-click (right click) on the element.
     */
    public static void rightClick(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForClickable(driver, locator);
            Actions actions = new Actions(driver);
            actions.contextClick(element).perform();
        } catch (Exception e) {
            attachFailure("rightClick", locator, e);
            throw new RuntimeException("Failed to rightClick element: " + locator, e);
        }
    }

    /**
     * Clicks the element via JavaScript. Useful as a last resort if standard click is blocked.
     */
    public static void clickUsingJs(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            attachFailure("clickUsingJs", locator, e);
            throw new RuntimeException("Failed to clickUsingJs on element: " + locator, e);
        }
    }

    /**
     * Scrolls the element into the viewport center.
     */
    public static void scrollIntoView(WebDriver driver, By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
        } catch (Exception e) {
            attachFailure("scrollIntoView", locator, e);
            throw new RuntimeException("Failed to scrollIntoView for element: " + locator, e);
        }
    }

    /**
     * Sends an absolute file path to a file input element to upload a file.
     */
    public static void uploadFile(WebDriver driver, By locator, String absolutePath) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            element.sendKeys(absolutePath);
        } catch (Exception e) {
            attachFailure("uploadFile", locator, e);
            throw new RuntimeException("Failed to uploadFile for element: " + locator, e);
        }
    }

    /**
     * Attempts both element.clear() and CTRL/Command+A followed by DELETE.
     * Using both strategies helps with shadow DOM or custom inputs that ignore one approach.
     */
    private static void clearWithSelectAll(WebElement element) {
        try {
            element.clear();
        } catch (Exception ignored) {
        }
        try {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.DELETE);
        } catch (Exception ignored) {
        }
    }

    /**
     * Attaches a failure message and screenshot to the Extent report for easier debugging.
     */
    private static void attachFailure(String action, By locator, Exception e) {
        try {
            // Log failure in Extent
            ExtentCucumberAdapter.addTestStepLog("Action failed: " + action + " on " + locator + " - " + e.getMessage());

            // Save screenshot file for Extent and link it
            WebDriver driver = DriverFactory.getDriver();
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safeName = action + "_" + sanitize(locator.toString());
            String timestamp = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS").format(LocalDateTime.now());
            String fileName = safeName + "_" + timestamp + ".png";

            Path screenshotsDir = Paths.get("extent-reports", "screenshots");
            Files.createDirectories(screenshotsDir);
            Path dest = screenshotsDir.resolve(fileName);
            Files.copy(src.toPath(), dest);

            String relativePath = "./screenshots/" + fileName;
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(relativePath);
        } catch (Exception ignored) {
        }
    }

    /**
     * Replaces characters that may be unsafe in filenames with underscores.
     */
    private static String sanitize(String text) {
        return text.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
}


