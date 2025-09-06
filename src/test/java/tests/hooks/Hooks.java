package tests.hooks;
import tests.utils.DriverFactory;
import io.cucumber.java.*;
public class Hooks {
    @Before public void setUp(){ DriverFactory.initDriver(); }

    @After public void tearDown(Scenario s){
        if (s.isFailed()){
            try{
                var bytes = ((org.openqa.selenium.TakesScreenshot) DriverFactory.getDriver())
                        .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                s.attach(bytes, "image/png", "failure");
            }catch(Exception ignored){}
        }
        DriverFactory.quitDriver();
    }
}
