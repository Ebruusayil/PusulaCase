package tests.pages;

import tests.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private final WebDriver d = DriverFactory.getDriver();
    private final WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(12));


    private final By appointmentModule = By.xpath("/html/body/main/section/div[1]/div/div/div/div[2]/div/div/a[1]");

    public void goToAppointmentModule() {
        WebElement el = w.until(ExpectedConditions.elementToBeClickable(appointmentModule));
        ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        el.click();
    }
}
