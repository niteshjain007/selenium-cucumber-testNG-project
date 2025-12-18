package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.InventoryPage;
import utils.DriverFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventorySortSteps {

    private final InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());

    @And("user sorts products by {string}")
    public void user_sorts_products_by(String sortName) {
        inventoryPage.sort(sortName);
    }

    @Then("products should be sorted by {string}")
    public void products_should_be_sorted_by(String sortName) {
        if (sortName.startsWith("Name")) {
            List<String> names = inventoryPage.getProductNames();
            List<String> expected = new ArrayList<>(names);
            if (sortName.contains("A to Z")) {
                expected.sort(String.CASE_INSENSITIVE_ORDER);
            } else {
                expected.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
            }
            Assert.assertEquals(names, expected, "Product names order mismatch for sort: " + sortName);
        } else if (sortName.startsWith("Price")) {
            List<Double> prices = inventoryPage.getProductPrices();
            List<Double> expected = new ArrayList<>(prices);
            if (sortName.contains("low to high")) {
                expected.sort(Comparator.naturalOrder());
            } else {
                expected.sort(Comparator.reverseOrder());
            }
            Assert.assertEquals(prices, expected, "Product prices order mismatch for sort: " + sortName);
        } else {
            Assert.fail("Unsupported sort option: " + sortName);
        }
    }
}


