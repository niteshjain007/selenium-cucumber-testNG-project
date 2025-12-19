package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runner for executing only the url_checks.feature.
 * Override tags from CLI with: -Dcucumber.filter.tags="@negative"
 *
 * Execution mode:
 * - Sequential: mvn test -Dsurefire.suiteXmlFiles=testng-url-checks.xml -Ddataproviderthreadcount=1
 * - Parallel:   mvn test -Dsurefire.suiteXmlFiles=testng-url-checks.xml -Ddataproviderthreadcount=4
 * The @DataProvider(parallel = true) enables concurrency; thread count is controlled by the CLI flag.
 *
 * @author NiteshJainQaTestology
 */
@CucumberOptions(
        features = "src/test/resources/features/url_checks.feature",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class UrlChecksRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}


