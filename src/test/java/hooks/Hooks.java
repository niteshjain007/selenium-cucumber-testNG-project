package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import utils.DriverFactory;

/**
 * Global Cucumber hooks.
 * - @Before: initialize a WebDriver for the current scenario/thread
 * - @After: on failure, attach a screenshot to Cucumber and Extent; then quit driver
 *
 * Screenshot paths are kept consistent with Extent's configuration so they appear in the report.
 *
 * @author NiteshJainQaTestology
 */
public class Hooks {

    /**
     * Creates a browser session before each scenario.
     */
    @Before
    public void setUp() {
        DriverFactory.initDriver();
    }

    /**
     * Tears down the browser after each scenario. On failure:
     * - Attaches a PNG screenshot to the Cucumber report for traceability
     * - Saves a file under extent-reports/screenshots and links it in Extent
     */
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                // Attach to Cucumber for traceability
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");

                // Save screenshot file for Extent and add to report
                File src = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
                String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
                String timestamp = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS").format(LocalDateTime.now());
                String fileName = safeName + "_" + timestamp + ".png";

                // Use paths configured in extent.properties (relative to project root)
                Path screenshotsDir = Paths.get("extent-reports", "screenshots");
                Files.createDirectories(screenshotsDir);
                Path dest = screenshotsDir.resolve(fileName);
                Files.copy(src.toPath(), dest);

                // Add screenshot to Extent (use relative path for proper linking)
                String relativePath = "./screenshots/" + fileName;
                ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(relativePath);
            } catch (Exception ignored) {
            }
        }
        DriverFactory.quitDriver();
    }
}
