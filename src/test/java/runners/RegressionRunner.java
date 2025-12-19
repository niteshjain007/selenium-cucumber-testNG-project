package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runner for executing all @regression scenarios across all features.
 *
 * Execution mode:
 * - Sequential: mvn test -Dsurefire.suiteXmlFiles=testng-regression.xml -Ddataproviderthreadcount=1
 * - Parallel:   mvn test -Dsurefire.suiteXmlFiles=testng-regression.xml -Ddataproviderthreadcount=4
 * The @DataProvider(parallel = true) enables concurrency; thread count is controlled by the CLI flag.
 *
 * @author NiteshJainQaTestology
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        tags = "@regression"
)
public class RegressionRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}


