package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

/**
 * Page Object for the checkout information page.
 * Captures user details (first name, last name, postal code) and proceeds to overview.
 *
 * @author NiteshJainQaTestology
 */
public class CheckoutInformationPage {

    private WebDriver driver;

    // Form field locators and continue button
    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    /**
     * Constructs a new instance bound to the provided WebDriver.
     */
    public CheckoutInformationPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Fills in the user information fields.
     */
    public void enterInformation(String fName, String lName, String zip) {
        ElementActions.sendKeys(driver, firstName, fName);
        ElementActions.sendKeys(driver, lastName, lName);
        ElementActions.sendKeys(driver, postalCode, zip);
    }

    /**
     * Clicks the continue button to proceed to checkout overview.
     */
    public void clickContinue() {
        ElementActions.click(driver, continueButton);
    }

    /**
     * Returns the validation error message when form inputs are invalid or missing.
     */
    public String getErrorMessage() {
        return ElementActions.getText(driver, errorMessage);
    }
}


