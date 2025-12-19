## SauceDemo UI Test Automation (Selenium + Cucumber + TestNG)

End-to-end UI automation for SauceDemo using Selenium WebDriver, Cucumber (Gherkin), TestNG, and ExtentReports.

### Stack
- **Language**: Java (JDK 21)
- **Build**: Maven
- **Test Framework**: Cucumber 7 + TestNG
- **Browser Automation**: Selenium 4
- **Reporting**: ExtentReports (Spark)

### Dependencies (key versions)
- Selenium: 4.18.1
- Cucumber: 7.33.0
- TestNG: 7.9.0
- ExtentReports Cucumber7 Adapter: 1.14.0

### Project structure
```text
saucedemo-ui/
  pom.xml
  testng.xml
  extent-reports/                 # Extent HTML report output (after a run)
  src/
    test/
      java/
        base/
        hooks/                    # Hooks (WebDriver setup/teardown, screenshots on failure)
        pages/                    # Page Objects (Login, Inventory, Cart, Checkout*)
        runners/                  # TestNG Cucumber runner (TestRunner.java)
        stepdefinitions/          # Step definitions
        utils/                    # DriverFactory (browser/headless/ThreadLocal)
      resources/
        features/                 # Gherkin features (login, checkout, inventory_sort)
        extent.properties         # Extent configuration
```

### How it works
- `runners.TestRunner` is a TestNG-based Cucumber runner.
  - Default tags in code: `@smoke or @regression` (can be overridden at CLI).
  - Parallel execution via DataProvider (controlled by CLI thread count).
- `utils.DriverFactory` creates a ThreadLocal `WebDriver` per scenario:
  - Browser: `-Dbrowser=chrome|firefox` (default: firefox)
  - Headless: `-Dheadless=true|false` (default: false)
- `hooks.Hooks` manages driver lifecycle and attaches screenshots on failures.
- `extent.properties` configures the report location at project root `extent-reports/index.html`.
- `testng.xml` runs the `runners.TestRunner` via Maven Surefire.

### Setup
1) Install JDK 21 and Maven.
2) Ensure Chrome and/or Firefox are installed. Selenium Manager will auto-provision drivers.
3) From project root, run any of the commands below.

### Running tests
- Default run (uses TestNG suite, default tags in runner):

```bash
mvn -q -DskipTests=false test
```

- Run only smoke:

```bash
mvn -q -DskipTests=false -Dcucumber.filter.tags="@smoke" test
```

- Run only regression:

```bash
mvn -q -DskipTests=false -Dcucumber.filter.tags="@regression" test
```

### Sequential vs parallel
- Sequential (1 thread):

```bash
mvn -q -DskipTests=false -Ddataproviderthreadcount=1 test
```

- Parallel (e.g., 4 threads):

```bash
mvn -q -DskipTests=false -Ddataproviderthreadcount=4 test
```

Notes:
- Parallel is enabled via TestNG DataProvider in `TestRunner`. Each scenario gets its own ThreadLocal driver.
- If your system/browser footprint is limited, reduce the thread count.

### Headless vs UI mode
- Firefox headless:

```bash
mvn -q -DskipTests=false -Dbrowser=firefox -Dheadless=true test
```

- Chrome UI:

```bash
mvn -q -DskipTests=false -Dbrowser=chrome -Dheadless=false test
```

You can mix with tags and parallel, e.g.:

```bash
mvn -q -DskipTests=false -Dcucumber.filter.tags="@regression" -Dbrowser=chrome -Dheadless=true -Ddataproviderthreadcount=3 test
```

### Reports and screenshots
- Extent HTML report is generated at:
  - `extent-reports/index.html`
- On scenario failure:
  - A PNG screenshot is saved under `extent-reports/screenshots/`
  - The screenshot is embedded in the Extent report step log

### Features included
- `login.feature`: basic login validation (@smoke)
- `checkout.feature`: add product, checkout, and verify overview (@regression, Scenario Outline with Examples)
- `inventory_sort.feature`: sort by name and price (A/Z and low/high) (@regression, Scenario Outline with Examples)

### Customization tips
- Update default tag selection in `runners/TestRunner.java` (`tags = "@smoke or @regression"`).
- Add new page objects under `src/test/java/pages` and reference them in step definitions.
- Extend `extent.properties` if you need additional reporters or configurations.

### Common issues
- If browsers fail to launch:
  - Ensure the browser is installed and up to date.
  - Try `-Dbrowser=firefox` or `-Dbrowser=chrome` explicitly.
  - For headless servers, ensure `-Dheadless=true` and configure any display restrictions.

### License
For training/demo purposes.


### Getting the project locally (step-by-step)
1) Clone the repository

```bash
git clone <your-repo-url>.git
cd saucedemo-ui
```

2) Open in your IDE and let Maven import dependencies (`pom.xml`).

3) Verify the build

```bash
mvn -q -DskipTests=true clean verify
```

4) Run a quick smoke sample

```bash
mvn test -Dsurefire.suiteXmlFiles=testng-smoke.xml
```

### How the framework is organized
- Full-suite runner: `runners.TestRunner` (default tags: `@smoke or @regression`)
- Per-feature runners (run one feature at a time):
  - `runners.LoginRunner` → `features/login.feature`
  - `runners.CheckoutRunner` → `features/checkout.feature`
  - `runners.InventorySortRunner` → `features/inventory_sort.feature`
  - `runners.UrlChecksRunner` → `features/url_checks.feature`
- Tag-specific runners:
  - `runners.SmokeRunner` → runs `@smoke`
  - `runners.RegressionRunner` → runs `@regression`

### Quick run options
- Full suite (default `testng.xml`):

```bash
mvn test
```

- Smoke or regression suites:

```bash
mvn test -Dsurefire.suiteXmlFiles=testng-smoke.xml
mvn test -Dsurefire.suiteXmlFiles=testng-regression.xml
```

- Single feature via suite XMLs:

```bash
mvn test -Dsurefire.suiteXmlFiles=testng-login.xml
mvn test -Dsurefire.suiteXmlFiles=testng-checkout.xml
mvn test -Dsurefire.suiteXmlFiles=testng-inventory-sort.xml
mvn test -Dsurefire.suiteXmlFiles=testng-url-checks.xml
```

- Tag filters (works with any suite):

```bash
mvn test -Dcucumber.filter.tags="@smoke and not @wip"
```

- Browser and headless:

```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

### Running from IDE
- Right‑click a runner class and choose “Run”:
  - Full: `runners.TestRunner`
  - Per-feature: `LoginRunner`, `CheckoutRunner`, `InventorySortRunner`, `UrlChecksRunner`
  - Tag suites: `SmokeRunner`, `RegressionRunner`
- Or right‑click a TestNG suite XML (e.g., `testng-smoke.xml`) and run.

### Configuration flags (system properties)
- `-Dbrowser=chrome|firefox` (default: firefox)
- `-Dheadless=true|false` (default: false)
- `-Dwait.timeout.seconds=10`
- `-Dwait.poll.millis=200`
- `-Ddataproviderthreadcount=1..N`
- `-Dcucumber.filter.tags="@tagExpr"`

### What is created after runs (ignored by Git)
- `target/` (Maven output)
- `surefire-reports/`, `test-output/` (TestNG reports)
- `extent-reports/` (Extent HTML and screenshots)
- `allure-results/` (if generated locally)

