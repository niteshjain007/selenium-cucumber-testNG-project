package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import utils.ElementActions;

/**
 * Page Object for the checkout overview page.
 * Lets tests verify items present in the order and finish the checkout.
 *
 * @author NiteshJainQaTestology
 */
public class CheckoutOverviewPage {

    private WebDriver driver;

    // Line items listed during checkout and the finish CTA
    private By lineItems = By.cssSelector(".cart_item .inventory_item_name");
    private By finishButton = By.id("finish");

    /**
     * Constructs a new instance bound to the provided WebDriver.
     */
    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Checks whether a product with the given name is present in the overview line items.
     *
     * @param productName case-insensitive product name
     * @return true if listed, false otherwise
     */
    public boolean isProductListed(String productName) {
        List<WebElement> items = driver.findElements(lineItems);
        for (WebElement e : items) {
            String text = e.getText().trim();
            if (text.equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clicks the finish button to complete the order.
     */
    public void clickFinish() {
        ElementActions.click(driver, finishButton);
    }
}


