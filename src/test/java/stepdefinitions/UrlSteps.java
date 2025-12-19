package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.InventoryPage;
import utils.DriverFactory;
import utils.WaitUtils;

/**
 * Step definitions for URL validation scenarios.
 * Demonstrates both passing and intentionally failing assertions to capture screenshots.
 *
 * @author NiteshJainQaTestology
 */
public class UrlSteps {

    private final InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());

    /**
     * Navigates from inventory to the cart page.
     */
    @And("user opens the cart page")
    public void user_opens_the_cart_page() {
        // Navigate to cart from inventory page
        inventoryPage.openCart();
        WaitUtils.waitForPageLoad(DriverFactory.getDriver());
    }

    /**
     * Verifies the current browser URL matches the expected value.
     */
    @Then("current page URL should be {string}")
    public void current_page_url_should_be(String expectedUrl) {
        // Note: Some scenarios intentionally use an incorrect expected URL to demonstrate
        // screenshot capture on assertion failure (see url_checks.feature).
        WaitUtils.waitForPageLoad(DriverFactory.getDriver());
        String actual = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertEquals(actual, expectedUrl, "Unexpected page URL");
    }
}


