package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.ArrayList;
import utils.ElementActions;

public class InventoryPage {

    private WebDriver driver;

    private By inventoryContainer = By.id("inventory_container");
    private By inventoryItemNames = By.cssSelector(".inventory_item_name");
    private By cartLink = By.className("shopping_cart_link");
    private By sortSelect = By.cssSelector("select.product_sort_container");
    private By inventoryItemPrices = By.cssSelector(".inventory_item_price");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isDisplayed() {
        return driver.findElement(inventoryContainer).isDisplayed();
    }

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

    public void openCart() {
        ElementActions.click(driver, cartLink);
    }

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

    public List<String> getProductNames() {
        List<WebElement> elements = driver.findElements(inventoryItemNames);
        List<String> names = new ArrayList<String>();
        for (WebElement e : elements) {
            names.add(e.getText().trim());
        }
        return names;
    }

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
