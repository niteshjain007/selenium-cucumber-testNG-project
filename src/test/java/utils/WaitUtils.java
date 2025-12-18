package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public final class WaitUtils {

    private static final long DEFAULT_TIMEOUT_SECONDS =
            Long.parseLong(System.getProperty("wait.timeout.seconds", "10"));

    private static final long DEFAULT_POLL_MILLIS =
            Long.parseLong(System.getProperty("wait.poll.millis", "200"));

    private WaitUtils() {}

    public static void waitForPageLoad(WebDriver driver) {
        waitForPageLoad(driver, DEFAULT_TIMEOUT_SECONDS);
    }

    public static void waitForPageLoad(WebDriver driver, long timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {
                        try {
                            String state = (String) ((JavascriptExecutor) d).executeScript("return document.readyState");
                            return "complete".equals(state);
                        } catch (Exception ignored) {
                            return false;
                        }
                    }
                });
    }

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return waitForVisible(driver, locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public static WebElement waitForVisible(WebDriver driver, By locator, long timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return waitForClickable(driver, locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public static WebElement waitForClickable(WebDriver driver, By locator, long timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        return waitForInvisibility(driver, locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public static boolean waitForInvisibility(WebDriver driver, By locator, long timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static FluentWait<WebDriver> fluentWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofMillis(DEFAULT_POLL_MILLIS))
                .ignoring(NoSuchElementException.class);
    }
}


