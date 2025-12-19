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

/**
 * WaitUtils provides consistent explicit wait helpers that wrap Selenium's
 * {@link WebDriverWait}, {@link FluentWait}, and expected conditions.
 * <p>
 * Defaults can be controlled via system properties:
 * - wait.timeout.seconds (default 10)
 * - wait.poll.millis (default 200)
 *
 * @author NiteshJainQaTestology
 */
public final class WaitUtils {

    private static final long DEFAULT_TIMEOUT_SECONDS =
            Long.parseLong(System.getProperty("wait.timeout.seconds", "10"));

    private static final long DEFAULT_POLL_MILLIS =
            Long.parseLong(System.getProperty("wait.poll.millis", "200"));

    private WaitUtils() {}

    /**
     * Waits for the page readyState to be "complete" using default timeout.
     */
    public static void waitForPageLoad(WebDriver driver) {
        waitForPageLoad(driver, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Waits for the page readyState to be "complete".
     *
     * @param timeoutSeconds maximum seconds to wait
     */
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

    /**
     * Waits for the element to be visible using default timeout.
     */
    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return waitForVisible(driver, locator, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Waits for the element to be visible.
     *
     * @param timeoutSeconds maximum seconds to wait
     * @return visible {@link WebElement}
     */
    public static WebElement waitForVisible(WebDriver driver, By locator, long timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for the element to be clickable using default timeout.
     */
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return waitForClickable(driver, locator, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Waits for the element to be clickable.
     *
     * @param timeoutSeconds maximum seconds to wait
     * @return clickable {@link WebElement}
     */
    public static WebElement waitForClickable(WebDriver driver, By locator, long timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for the element located by the locator to become invisible using default timeout.
     */
    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        return waitForInvisibility(driver, locator, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Waits for the element located by the locator to become invisible.
     *
     * @param timeoutSeconds maximum seconds to wait
     * @return true if invisible before timeout, false otherwise
     */
    public static boolean waitForInvisibility(WebDriver driver, By locator, long timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Creates a FluentWait with defaults for timeout, polling, and ignored exceptions.
     * Use this when you need a custom expected condition or polling strategy.
     */
    public static FluentWait<WebDriver> fluentWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofMillis(DEFAULT_POLL_MILLIS))
                .ignoring(NoSuchElementException.class);
    }
}


