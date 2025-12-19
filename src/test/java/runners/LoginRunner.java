package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runner for executing only the login.feature.
 * Override tags from CLI with: -Dcucumber.filter.tags="@smoke"
 *
 * Execution mode:
 * - Sequential: mvn test -Dsurefire.suiteXmlFiles=testng-login.xml -Ddataproviderthreadcount=1
 * - Parallel:   mvn test -Dsurefire.suiteXmlFiles=testng-login.xml -Ddataproviderthreadcount=4
 * The @DataProvider(parallel = true) enables concurrency; thread count is controlled by the CLI flag.
 *
 * @author NiteshJainQaTestology
 */
@CucumberOptions(
        features = "src/test/resources/features/login.feature",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
        // No default tags; run all scenarios in this feature unless -Dcucumber.filter.tags is provided
)
public class LoginRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}


