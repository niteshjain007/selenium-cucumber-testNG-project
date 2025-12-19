package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverFactory manages a single {@link WebDriver} instance per thread using {@link ThreadLocal}.
 * <p>
 * Configuration is controlled via system properties:
 * - browser: chrome | firefox (default: firefox)
 * - headless: true | false (default: false)
 * <p>
 * Usage pattern:
 * - Call {@link #initDriver()} in a test/setup hook
 * - Retrieve with {@link #getDriver()}
 * - Call {@link #quitDriver()} in a teardown hook
 *
 * @author NiteshJainQaTestology
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initializes the {@link WebDriver} for the current thread based on configuration.
     * Applies headless options when requested and attempts to maximize the window.
     */
    public static void initDriver() {
        String browser = System.getProperty("browser", "firefox").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                driver.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":
            default:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
        }

        try {
            getDriver().manage().window().maximize();
        } catch (Exception ignored) {
        }
    }

    /**
     * Gets the {@link WebDriver} instance bound to the current thread.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quits and removes the {@link WebDriver} instance for the current thread.
     * Always call this in teardown to avoid driver leaks between tests.
     */
    public static void quitDriver() {
        getDriver().quit();
        driver.remove();
    }
}
