package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutInformationPage;
import pages.CheckoutOverviewPage;
import pages.InventoryPage;
import pages.CheckoutCompletePage;
import utils.DriverFactory;

/**
 * Step definitions for the end-to-end checkout flow.
 * These steps orchestrate actions across Inventory, Cart, Checkout Information, and Overview pages.
 *
 * @author NiteshJainQaTestology
 */
public class CheckoutSteps {

    private final InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());
    private final CartPage cartPage = new CartPage(DriverFactory.getDriver());
    private final CheckoutInformationPage checkoutInformationPage = new CheckoutInformationPage(DriverFactory.getDriver());
    private final CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(DriverFactory.getDriver());
    private final CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(DriverFactory.getDriver());

    /**
     * Adds a product to the cart from the inventory page.
     */
    @And("user adds product {string} to the cart from inventory")
    public void user_adds_product_to_cart_from_inventory(String productName) {
        Assert.assertTrue(inventoryPage.isDisplayed(), "Inventory page should be displayed before adding to cart");
        inventoryPage.addProductToCart(productName);
    }

    /**
     * Opens the cart and starts the checkout flow.
     */
    @And("user opens the cart and starts checkout")
    public void user_opens_the_cart_and_starts_checkout() {
        inventoryPage.openCart();
        cartPage.clickCheckout();
    }

    /**
     * Enters checkout information and continues to the overview page.
     */
    @And("user enters first name {string} last name {string} postal code {string} and continues")
    public void user_enters_checkout_info_and_continues(String firstName, String lastName, String postalCode) {
        checkoutInformationPage.enterInformation(firstName, lastName, postalCode);
        checkoutInformationPage.clickContinue();
    }

    /**
     * Verifies the selected product is present in the checkout overview.
     */
    @Then("checkout overview shows product {string}")
    public void checkout_overview_shows_product(String productName) {
        Assert.assertTrue(checkoutOverviewPage.isProductListed(productName),
                "Expected product not found on checkout overview: " + productName);
    }

    /**
     * Validates checkout information page error message (for negative scenarios).
     */
    @Then("checkout info error should be {string}")
    public void checkout_info_error_should_be(String expectedError) {
        String actual = checkoutInformationPage.getErrorMessage();
        Assert.assertEquals(actual, expectedError, "Checkout info validation error mismatch");
    }

    /**
     * Completes the checkout on the overview page.
     */
    @And("user finishes the checkout")
    public void user_finishes_the_checkout() {
        checkoutOverviewPage.clickFinish();
    }

    /**
     * Verifies the order completion screen is displayed.
     */
    @Then("order completion page should be displayed")
    public void order_completion_page_should_be_displayed() {
        Assert.assertTrue(checkoutCompletePage.isComplete(), "Order completion page not displayed");
    }
}


