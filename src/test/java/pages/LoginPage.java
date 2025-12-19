package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;
import utils.WaitUtils;

/**
 * Page Object for the SauceDemo login page.
 * Provides actions to open the page, perform login and read error messages.
 *
 * @author NiteshJainQaTestology
 */
public class LoginPage {

    private WebDriver driver;

    // Locators for login form elements and error banner
    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    /**
     * Constructs a new instance bound to the provided WebDriver.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Opens the SauceDemo login page and waits for document readiness.
     */
    public void open() {
        driver.get("https://www.saucedemo.com/");
        WaitUtils.waitForPageLoad(driver);
    }

    /**
     * Attempts to log in with provided credentials.
     *
     * @param user username value
     * @param pass password value
     */
    public void login(String user, String pass) {
        ElementActions.sendKeys(driver, username, user);
        ElementActions.sendKeys(driver, password, pass);
        ElementActions.click(driver, loginBtn);
    }

    /**
     * Returns the text of the error banner (if present) after a failed login attempt.
     */
    public String getErrorMessage() {
        return ElementActions.getText(driver, errorMessage);
    }
}
