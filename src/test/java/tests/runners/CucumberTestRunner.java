package tests.runners;

import io.cucumber.testng.*;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"tests.steps","tests.hooks"},
        plugin = {"pretty","summary","html:target/cucumber-report.html"},
        tags = "@appointment"
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    @Override @DataProvider(parallel = true)
    public Object[][] scenarios(){ return super.scenarios(); }
}
