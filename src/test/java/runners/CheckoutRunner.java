package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runner for executing only the checkout.feature.
 * Override tags from CLI with: -Dcucumber.filter.tags="@regression"
 *
 * Execution mode:
 * - Sequential: mvn test -Dsurefire.suiteXmlFiles=testng-checkout.xml -Ddataproviderthreadcount=1
 * - Parallel:   mvn test -Dsurefire.suiteXmlFiles=testng-checkout.xml -Ddataproviderthreadcount=4
 * The @DataProvider(parallel = true) enables concurrency; thread count is controlled by the CLI flag.
 *
 * @author NiteshJainQaTestology
 */
@CucumberOptions(
        features = "src/test/resources/features/checkout.feature",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class CheckoutRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}


