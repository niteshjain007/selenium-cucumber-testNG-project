package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.InventoryPage;
import utils.DriverFactory;
import utils.WaitUtils;

public class UrlSteps {

    private final InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());

    @And("user opens the cart page")
    public void user_opens_the_cart_page() {
        // Navigate to cart from inventory page
        inventoryPage.openCart();
        WaitUtils.waitForPageLoad(DriverFactory.getDriver());
    }

    @Then("current page URL should be {string}")
    public void current_page_url_should_be(String expectedUrl) {
        // Note: Some scenarios intentionally use an incorrect expected URL to demonstrate
        // screenshot capture on assertion failure (see url_checks.feature).
        WaitUtils.waitForPageLoad(DriverFactory.getDriver());
        String actual = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertEquals(actual, expectedUrl, "Unexpected page URL");
    }
}


