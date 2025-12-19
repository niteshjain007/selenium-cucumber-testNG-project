package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

/**
 * Page Object for the cart page.
 * Encapsulates actions related to proceeding to checkout from the cart.
 *
 * @author NiteshJainQaTestology
 */
public class CartPage {

    private WebDriver driver;

    // Checkout CTA on the cart page
    private By checkoutButton = By.id("checkout");

    /**
     * Constructs a new instance bound to the provided WebDriver.
     */
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clicks the checkout button to start the checkout flow.
     */
    public void clickCheckout() {
        ElementActions.click(driver, checkoutButton);
    }
}


