package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.ArrayList;
import utils.ElementActions;

/**
 * Page Object for the inventory (products listing) page.
 * Supports visibility checks, cart navigation, sorting, and reading product names/prices.
 *
 * @author NiteshJainQaTestology
 */
public class InventoryPage {

    private WebDriver driver;

    // Key elements on the inventory screen
    private By inventoryContainer = By.id("inventory_container");
    private By inventoryItemNames = By.cssSelector(".inventory_item_name");
    private By cartLink = By.className("shopping_cart_link");
    private By sortSelect = By.cssSelector("select.product_sort_container");
    private By inventoryItemPrices = By.cssSelector(".inventory_item_price");

    /**
     * Constructs a new instance bound to the provided WebDriver.
     */
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns true if the inventory container is displayed.
     */
    public boolean isDisplayed() {
        return driver.findElement(inventoryContainer).isDisplayed();
    }

    /**
     * Adds the specified product to the cart by matching its name in the list.
     * Locates the item's container via ancestor traversal, then clicks its Add to cart button.
     *
     * @throws IllegalStateException when the product is not found in the list
     */
    public void addProductToCart(String productName) {
        List<WebElement> names = driver.findElements(inventoryItemNames);
        for (WebElement nameEl : names) {
            if (nameEl.getText().trim().equalsIgnoreCase(productName)) {
                // For this item, find its container and click its Add to cart button
                WebElement item = nameEl.findElement(By.xpath("./ancestor::div[@class='inventory_item']"));
                item.findElement(By.cssSelector("button.btn.btn_primary.btn_small.btn_inventory")).click();
                return;
            }
        }
        throw new IllegalStateException("Product not found on inventory: " + productName);
    }

    /**
     * Opens the shopping cart page.
     */
    public void openCart() {
        ElementActions.click(driver, cartLink);
    }

    /**
     * Applies one of the available sort options by visible label.
     *
     * @param sortName one of: Name (A to Z), Name (Z to A), Price (low to high), Price (high to low)
     */
    public void sort(String sortName) {
        Select select = new Select(driver.findElement(sortSelect));
        switch (sortName) {
            case "Name (A to Z)":
                select.selectByValue("az");
                break;
            case "Name (Z to A)":
                select.selectByValue("za");
                break;
            case "Price (low to high)":
                select.selectByValue("lohi");
                break;
            case "Price (high to low)":
                select.selectByValue("hilo");
                break;
            default:
                throw new IllegalArgumentException("Unsupported sort option: " + sortName);
        }
    }

    /**
     * Returns the list of product names shown on the page (trimmed).
     */
    public List<String> getProductNames() {
        List<WebElement> elements = driver.findElements(inventoryItemNames);
        List<String> names = new ArrayList<String>();
        for (WebElement e : elements) {
            names.add(e.getText().trim());
        }
        return names;
    }

    /**
     * Returns the list of product prices as numbers (currency symbol stripped).
     */
    public List<Double> getProductPrices() {
        List<WebElement> elements = driver.findElements(inventoryItemPrices);
        List<Double> prices = new ArrayList<Double>();
        for (WebElement e : elements) {
            String text = e.getText().replace("$", "").trim();
            prices.add(Double.valueOf(text));
        }
        return prices;
    }
}
