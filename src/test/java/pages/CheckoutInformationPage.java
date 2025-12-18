package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class CheckoutInformationPage {

    private WebDriver driver;

    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public CheckoutInformationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterInformation(String fName, String lName, String zip) {
        ElementActions.sendKeys(driver, firstName, fName);
        ElementActions.sendKeys(driver, lastName, lName);
        ElementActions.sendKeys(driver, postalCode, zip);
    }

    public void clickContinue() {
        ElementActions.click(driver, continueButton);
    }

    public String getErrorMessage() {
        return ElementActions.getText(driver, errorMessage);
    }
}


