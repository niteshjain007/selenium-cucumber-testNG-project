package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;
import utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://www.saucedemo.com/");
        WaitUtils.waitForPageLoad(driver);
    }

    public void login(String user, String pass) {
        ElementActions.sendKeys(driver, username, user);
        ElementActions.sendKeys(driver, password, pass);
        ElementActions.click(driver, loginBtn);
    }

    public String getErrorMessage() {
        return ElementActions.getText(driver, errorMessage);
    }
}
