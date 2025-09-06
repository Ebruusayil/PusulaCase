package tests.pages;

import tests.utils.DriverFactory;
import tests.utils.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver d = DriverFactory.getDriver();
    private final WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(15));
    private final By changeCustomerBtn = By.xpath("//*[@id='AbpTenantSwitchLink']");
    private final By nameInput         = By.xpath("//*[@id='Input_Name']");
    private final By saveBtnPrimary    = By.xpath("/html/body/div[1]/form/div/div/div/div[3]/button[2]");
    private final By saveBtnByText     = By.xpath("//button[normalize-space()='Kaydet']");
    private final By saveBtnInModal    = By.xpath("//div[starts-with(@id,'Abp_Modal') and contains(@id,'Container')]//button[normalize-space()='Kaydet']");
    private final By anyModalContainer = By.xpath("//div[starts-with(@id,'Abp_Modal') and contains(@id,'Container')]");
    private final By username = By.xpath("//*[@id='LoginInput_UserNameOrEmailAddress']");
    private final By password = By.xpath("//*[@id='password-input']");
    private final By submitPrimary = By.xpath("/html/body/div/div/div/div/div/div/div[2]/div/div[3]/form/div[3]/button");
    private final By submitFallback = By.xpath("//form//button[@type='submit' or normalize-space()='Giriş']");


    private final By dashboardMarker = By.xpath("//*[contains(@class,'menu') or contains(@class,'sidebar') or contains(@id,'menu')]");

    public void open() {
        d.get(Config.get("baseUrl", "http://localhost"));
    }


    public void ensureCustomer(String targetName) {

        if (d.findElements(changeCustomerBtn).isEmpty()) return;

        w.until(ExpectedConditions.elementToBeClickable(changeCustomerBtn)).click();

        WebElement input = w.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        input.clear();
        input.sendKeys(targetName);

        By[] candidates = new By[]{ saveBtnPrimary, saveBtnByText, saveBtnInModal };
        boolean clicked = false;

        for (By loc : candidates) {
            try {
                WebElement btn = w.until(ExpectedConditions.elementToBeClickable(loc));
                try {
                    btn.click();
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) d).executeScript("arguments[0].click();", btn);
                }
                clicked = true;
                break;
            } catch (TimeoutException | NoSuchElementException ignored) {  }
        }

        if (!clicked) {

            try {
                WebElement btn = w.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'modal') or contains(@class,'dialog')]" +
                                "//button[@type='submit' or normalize-space()='Kaydet' or contains(@class,'btn-primary')]")
                ));
                btn.click();
                clicked = true;
            } catch (Exception ignored) {}
        }

        if (!clicked) throw new NoSuchElementException("Kaydet butonu bulunamadı.");

        try {
            w.until(ExpectedConditions.invisibilityOfElementLocated(anyModalContainer));
        } catch (Exception ignored) {

            try { w.until(ExpectedConditions.invisibilityOfElementLocated(saveBtnByText)); } catch (Exception ignore2) {}
        }
    }

    public void login(String u, String p) {
        WebElement userEl = w.until(ExpectedConditions.visibilityOfElementLocated(username));
        userEl.clear();
        userEl.sendKeys(u);

        WebElement passEl = d.findElement(password);
        passEl.clear();
        passEl.sendKeys(p);

        WebElement loginBtn;
        try {
            loginBtn = w.until(ExpectedConditions.elementToBeClickable(submitPrimary));
        } catch (TimeoutException e) {
            loginBtn = w.until(ExpectedConditions.elementToBeClickable(submitFallback));
        }

        try {
            loginBtn.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) d).executeScript("arguments[0].click();", loginBtn);
        }

        By modulesText = By.xpath("//*[normalize-space()='Modüller']");
        w.until(ExpectedConditions.visibilityOfElementLocated(modulesText));
    }


}
