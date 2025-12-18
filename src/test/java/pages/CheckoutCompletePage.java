package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class CheckoutCompletePage {

    private WebDriver driver;

    private By completeHeader = By.cssSelector("h2.complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isComplete() {
        // Verifies the thank you header is present
        return ElementActions.isDisplayed(driver, completeHeader);
    }
}


