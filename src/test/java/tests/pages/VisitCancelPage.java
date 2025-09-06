package tests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.utils.DriverFactory;

import java.time.Duration;

public class VisitCancelPage {
    private final WebDriver d = DriverFactory.getDriver();
    private final WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(15));


    private final By financeCardBtn = By.xpath("//button[normalize-space()='Finans Kartı']");


    private final By moreMenuBtnXpath = By.xpath("//*[@id='sfdropdownbutton77068f25-9b2c-4532-9df9-2047c0254aed']");
    private final By moreMenuBtnFallback = By.cssSelector("button[id^='sfdropdownbutton']");
    private final By cancelVisitXpath = By.xpath("//*[@id='e-dropdown-popup58753c17-8e07-4a4b-a61c-5cdbfd700577']/ul/li[2]");
    private final By cancelVisitFallback = By.xpath("//ul/li[.//text()[normalize-space()='Vizit İptal Et']]");


    private final By confirmBtnXpath = By.xpath("//*[@id='dialog-369c539b-ea99-4064-84c7-cfb479530d7b_dialog-content']/div/div[2]/button");
    private final By confirmBtnFallback = By.xpath("//button[normalize-space()='Onayla']");

    private void jsClick(WebElement el){ ((JavascriptExecutor)d).executeScript("arguments[0].click();", el); }
    private WebElement clickable(By by){ return w.until(ExpectedConditions.elementToBeClickable(by)); }

    public void openFinanceCard(){
        try { clickable(financeCardBtn).click(); } catch (ElementClickInterceptedException e){ jsClick(clickable(financeCardBtn)); }
    }

    public void openMoreMenu(){
        try {
            try { clickable(moreMenuBtnXpath).click(); }
            catch (TimeoutException e){ clickable(moreMenuBtnFallback).click(); }
        } catch (ElementClickInterceptedException e){
            try { jsClick(clickable(moreMenuBtnXpath)); }
            catch (Exception ignored){ jsClick(clickable(moreMenuBtnFallback)); }
        }
    }

    public void chooseCancelVisit(){
        try {
            try { clickable(cancelVisitXpath).click(); }
            catch (TimeoutException e){ clickable(cancelVisitFallback).click(); }
        } catch (ElementClickInterceptedException e){
            try { jsClick(clickable(cancelVisitXpath)); }
            catch (Exception ignored){ jsClick(clickable(cancelVisitFallback)); }
        }
    }

    public void confirmCancel(){
        try {
            try { clickable(confirmBtnXpath).click(); }
            catch (TimeoutException e){ clickable(confirmBtnFallback).click(); }
        } catch (ElementClickInterceptedException e){
            try { jsClick(clickable(confirmBtnXpath)); }
            catch (Exception ignored){ jsClick(clickable(confirmBtnFallback)); }
        }
    }
}
