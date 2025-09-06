package tests.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();
    public static void initDriver(){
        WebDriverManager.chromedriver().setup();
        WebDriver d = new ChromeDriver();
        d.manage().window().maximize();
        TL.set(d);
    }
    public static WebDriver getDriver(){ return TL.get(); }
    public static void quitDriver(){ var d = TL.get(); if (d!=null){ d.quit(); TL.remove(); } }
}
