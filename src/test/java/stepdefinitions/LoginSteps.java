package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverFactory;
import utils.WaitUtils;

public class LoginSteps {

    LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());

    @Given("user is on SauceDemo login page")
    public void user_is_on_login_page() {
        loginPage.open();
    }

    @When("user logs in with username {string} and password {string}")
    public void user_logs_in(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("inventory page should be displayed")
    public void inventory_page_should_be_displayed() {
        Assert.assertTrue(
                inventoryPage.isDisplayed(),
                "Inventory page is not displayed"
        );
    }

    @Then("error message should be {string}")
    public void error_message_should_be(String expected) {
        // Wait for potential error message to render
        WaitUtils.waitForPageLoad(DriverFactory.getDriver());
        String actual = loginPage.getErrorMessage();
        Assert.assertEquals(actual, expected, "Invalid login error message mismatch");
    }
}
