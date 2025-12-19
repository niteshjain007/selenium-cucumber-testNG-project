package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

/**
 * Page Object for the order completion (Thank You) page.
 * Used to assert that checkout has reached the final success screen.
 *
 * @author NiteshJainQaTestology
 */
public class CheckoutCompletePage {

    private WebDriver driver;

    // Heading shown upon successful order completion
    private By completeHeader = By.cssSelector("h2.complete-header");

    /**
     * Constructs a new instance bound to the provided WebDriver.
     */
    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns true if the completion header is displayed.
     */
    public boolean isComplete() {
        // Verifies the thank you header is present
        return ElementActions.isDisplayed(driver, completeHeader);
    }
}


