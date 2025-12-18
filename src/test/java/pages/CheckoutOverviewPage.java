package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import utils.ElementActions;

public class CheckoutOverviewPage {

    private WebDriver driver;

    private By lineItems = By.cssSelector(".cart_item .inventory_item_name");
    private By finishButton = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

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

    public void clickFinish() {
        ElementActions.click(driver, finishButton);
    }
}


