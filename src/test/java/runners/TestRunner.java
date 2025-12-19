package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG+Cucumber runner configuration.
 * - Features path: src/test/resources/features
 * - Glue packages: stepdefinitions, hooks
 * - Plugins: pretty + ExtentReports adapter
 * - Tags: defaults to "@smoke or @regression" (override via -Dcucumber.filter.tags)
 *
 * Extends {@link AbstractTestNGCucumberTests} to leverage TestNG's DataProvider
 * for parallel execution of Cucumber scenarios.
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
        // Default: run smoke or regression; override with -Dcucumber.filter.tags
        tags = "@smoke or @regression"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // Enable parallel execution of scenarios via TestNG's DataProvider thread pool.
    // Control threads from CLI: -Ddataproviderthreadcount=1 (sequential) or >1 (parallel)
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

