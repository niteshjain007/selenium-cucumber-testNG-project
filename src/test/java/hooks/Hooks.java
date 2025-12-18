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

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver();
    }

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
