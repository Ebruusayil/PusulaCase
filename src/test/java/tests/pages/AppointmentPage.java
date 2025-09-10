package tests.pages;

import tests.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class AppointmentPage {
    private final WebDriver d = DriverFactory.getDriver();
    private final WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(15));

    private final By appointmentModule = By.xpath("/html/body/main/section/div[1]/div/div/div/div[2]/div/div/a[1]");

    private final By targetSlotPrimary  = By.xpath("//*[@id='Schedule-196cc4f1-dfce-415c-b0b3-a396c4cd0da3']/div[4]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[18]/td[4]");
    private final By targetSlotFallback = By.xpath("//div[starts-with(@id,'Schedule-')]/div[4]//table//tbody/tr[2]//table//tbody/tr[18]/td[4]");

    private final By patientSearchInput = By.xpath("//*[@id='85515ea3-f057-4b07-a2bd-a4361660f99e']");
    private final By patientSearchBtn   = By.xpath("//*[@id='00000000-0000-0000-0000-000000000002']/div[2]/button");
    private final By dateInput    = By.xpath("//*[@id='appointment-date']");
    private final By timeInput    = By.xpath("//*[@id='start-time']");
    private final By durationElem = By.xpath("//*[@id='duration']");

    private final By saveBtnExact   = By.xpath("//*[@id='dataform-37f185ce-459e-4b1c-ba38-fcecf5b72ffd']/div[6]/div/button");
    private final By saveBtnText    = By.xpath("//button[normalize-space()='Kaydet']");
    private final By saveBtnGeneric = By.xpath("//button[contains(@class,'primary') or contains(@class,'success')]");
    private final By toastSuccess = By.cssSelector(
            "#toast_default_container .e-toast-message, " +
                    ".toast-success, .alert-success, " +
                    "[class*='Toastify__toast--success'], " +
                    "[role='status'], [aria-live='polite']"
    );


    public void goToAppointmentModule() {
        WebElement box = w.until(ExpectedConditions.elementToBeClickable(appointmentModule));
        ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center'});", box);
        try { box.click(); } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor)d).executeScript("arguments[0].click();", box);
        }

        new WebDriverWait(d, Duration.ofSeconds(5)).until(
                drv -> ((JavascriptExecutor)drv).executeScript("return document.readyState").equals("complete")
        );
    }

    private final By filterOpenBtn   = By.xpath("/html/body/main/section/div[1]/div/div[1]/div/button[1]");
    private final By sourcesField    = By.xpath("/html/body/main/section/div[1]/div/div[2]/div/div[1]/div/div[3]/div/div");
    private final By sourcesCloseOne = By.xpath("/html/body/main/section/div[1]/div/div[2]/div/div[1]/div/div[3]/div/div/span[3]");
    private final By acceptFilterBtn = By.xpath("//button[normalize-space()='Kabul et']");
    private final By anyChipClose = By.cssSelector("div[id*='div'][class*='chips'], .e-chips .e-chips-close, .e-chip .e-chip-close, span.e-chips-close");

    private By doctorOption(String name){
        String n = name.trim();
        return By.xpath("//li[normalize-space()='"+n+"'] | //li//span[normalize-space()='"+n+"']/ancestor::li[1]");
    }
    public void openFilterPanel(){
        WebElement btn = w.until(ExpectedConditions.elementToBeClickable(filterOpenBtn));
        scrollIntoView(btn);
        try { btn.click(); } catch (ElementClickInterceptedException e){ jsClick(btn); }
        sleep(400);
    }

    public void clearDoctorFilterTokens(){
        sleep(150);

        WebElement field = w.until(ExpectedConditions.visibilityOfElementLocated(sourcesField));
        new org.openqa.selenium.interactions.Actions(d).moveToElement(field).perform();
        sleep(1500);

        try {
            WebElement x = w.until(ExpectedConditions.elementToBeClickable(sourcesCloseOne));
            try { x.click(); } catch (Exception ex){ jsClick(x); }
            sleep(100);
        } catch (TimeoutException ignored){}

        java.util.List<WebElement> closes = d.findElements(anyChipClose);
        for (WebElement c : closes){
            if (!c.isDisplayed()) continue;
            try { c.click(); } catch (Exception ex){ jsClick(c); }
            sleep(80);
        }
    }

    /*public void selectDoctorAndApply(String doctorName){

        WebElement field = w.until(ExpectedConditions.elementToBeClickable(sourcesField));
        scrollIntoView(field);
        try { field.click(); } catch (ElementClickInterceptedException e){ jsClick(field); }

        WebElement opt = w.until(ExpectedConditions.elementToBeClickable(doctorOption(doctorName)));
        scrollIntoView(opt);
        try { opt.click(); } catch (ElementClickInterceptedException e){ jsClick(opt); }

        WebElement accept = w.until(ExpectedConditions.elementToBeClickable(acceptFilterBtn));
        scrollIntoView(accept);
        try { accept.click(); } catch (ElementClickInterceptedException e){ jsClick(accept); }

        sleep(600);
    }*/
    public void selectDoctorAndApply(String doctorName) {
        WebElement field = w.until(ExpectedConditions.elementToBeClickable(sourcesField));
        scrollIntoView(field);

        try { field.click(); } catch (ElementClickInterceptedException e){ jsClick(field); }
        sleep(600);
        w.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("ul[role='listbox'], .e-popup-open")));
        sleep(600);
        List<WebElement> options = d.findElements(By.xpath("//li[starts-with(normalize-space(.),'Doç. Dr.')]"));

        if (options.isEmpty()) {
            throw new NoSuchElementException("Hiç doktor seçeneği bulunamadı!");
        }
        WebElement lastOpt = options.get(options.size() - 1);
        scrollIntoView(lastOpt);
        try { lastOpt.click(); } catch (Exception ex) { jsClick(lastOpt); }
        WebElement accept = w.until(ExpectedConditions.elementToBeClickable(acceptFilterBtn));
        scrollIntoView(accept);
        try { accept.click(); } catch (ElementClickInterceptedException e){ jsClick(accept); }

        sleep(600);
    }



    public void clickTargetSlot() {
        By slotLocator = By.xpath("/html/body/main/section/div[1]/div/div[2]/div/div[1]/div[4]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[18]/td");

        WebElement cell = w.until(ExpectedConditions.presenceOfElementLocated(slotLocator));
        ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center'});", cell);

        try {
            new org.openqa.selenium.interactions.Actions(d)
                    .moveToElement(cell)
                    .pause(java.time.Duration.ofMillis(200))
                    .click()
                    .perform();
            System.out.println("Slot başarıyla Actions ile tıklandı.");
        } catch (Exception e) {
            ((JavascriptExecutor) d).executeScript("arguments[0].click();", cell);
            System.out.println("Slot JS ile tıklandı (fallback).");
        }

        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
    }


    public void searchAndSelectPatient(String fullName){
        try {
            for (WebElement f : d.findElements(By.cssSelector("iframe,iframe[src]"))) {
                if (f.isDisplayed()) { d.switchTo().frame(f); break; }
            }
        } catch (Exception ignored) {}

        By[] inputCandidates = new By[]{
                patientSearchInput,
                By.xpath("(//*[normalize-space()='Hasta Arama']/following::input)[1]"),
                By.xpath("//input[contains(@placeholder,'Ara') or contains(@aria-label,'Ara')]"),
                By.cssSelector("input[type='text']")
        };
        WebElement input = null;
        for (By by : inputCandidates) {
            try { input = w.until(ExpectedConditions.elementToBeClickable(by)); break; }
            catch (Exception ignored) {}
        }
        if (input == null) throw new NoSuchElementException("Hasta arama input'u bulunamadı.");

        ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        input.click();
        try { input.clear(); } catch (Exception ignored) {}
        input.sendKeys(fullName);
        input.sendKeys(Keys.ENTER);

        try {
            WebElement btnAra = w.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id='00000000-0000-0000-0000-000000000002']/div[2]/button | //button[normalize-space()='Ara']")));
            try { btnAra.click(); } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor)d).executeScript("arguments[0].click();", btnAra);
            }
        } catch (TimeoutException ignored) {}

        try {
            w.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".loading, .spinner, [aria-busy='true']")));
        } catch (Exception ignored) {}

        WebElement nameEl = null;
        try {
            nameEl = w.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-testid='patient-name-0']")));
        } catch (TimeoutException ignored) {}

        if (nameEl == null) {
            java.util.List<WebElement> candidates =
                    d.findElements(By.cssSelector("[data-testid^='patient-name-']"));
            String needle = fullName.trim().toLowerCase();
            for (WebElement el : candidates) {
                if (!el.isDisplayed()) continue;
                String txt = el.getText() != null ? el.getText().trim().toLowerCase() : "";
                String title = el.getAttribute("title") != null ? el.getAttribute("title").trim().toLowerCase() : "";
                if (txt.equals(needle) || title.equals(needle)) {
                    nameEl = el;
                    break;
                }
            }
        }

        if (nameEl == null) throw new NoSuchElementException("Arama sonucunda hasta bulunamadı: " + fullName);

        WebElement clickable = nameEl;
        try {
            clickable = nameEl.findElement(By.xpath("./ancestor::*[self::button or self::tr or self::li or self::div][1]"));
        } catch (NoSuchElementException ignored) {}

        ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", clickable);
        w.until(ExpectedConditions.elementToBeClickable(clickable));
        try { clickable.click(); } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor)d).executeScript("arguments[0].click();", clickable);
        }

        try { d.switchTo().defaultContent(); } catch (Exception ignored) {}
    }
    private java.time.LocalDateTime clampToBusinessHours(java.time.LocalDateTime dt){
        java.time.LocalTime start = java.time.LocalTime.of(8, 0);
        java.time.LocalTime end   = java.time.LocalTime.of(18, 0);

        java.time.LocalDate d = dt.toLocalDate();
        java.time.LocalTime t = dt.toLocalTime();

        if (t.isBefore(start)) {
            t = start;
        } else if (!t.isBefore(end)) {
            d = d.plusDays(1);
            t = start;
        }
        return java.time.LocalDateTime.of(d, t);
    }

    public void fillDateTimeAndDurationForNow() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        int m = now.getMinute();
        int rounded = ((m + 4) / 5) * 5;

        if (rounded >= 60) {
            now = now.plusHours(1).withMinute(0).withSecond(0).withNano(0);
        } else {
            now = now.withMinute(rounded).withSecond(0).withNano(0);
        }

        java.time.LocalDateTime slot = clampToBusinessHours(now);

        if (!isDateInputToday(dateInput)) {
            setDateSmart(dateInput, slot.toLocalDate());
        }

        setTimeSmart(timeInput, slot.toLocalTime());

        sleep(150);
    }

    private boolean isDateInputToday(By locator){
        try {
            var el = w.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String v = el.getAttribute("value");
            if (v == null || v.isBlank()) return false;

            java.time.LocalDate today = java.time.LocalDate.now();

            if (v.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return java.time.LocalDate.parse(v).isEqual(today);
            }

            if (v.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                var fmt = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return java.time.LocalDate.parse(v, fmt).isEqual(today);
            }

            java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{4})").matcher(v);
            if (m.find()) {
                int y = Integer.parseInt(m.group(1));
                return y == today.getYear();
            }
        } catch (Exception ignored) {}
        return false;
    }

    private void setDateSmart(By locator, java.time.LocalDate date){
        WebElement el = w.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", el);

        String vDate = date.toString();
        String tDate = date.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));


        String type = "";
        try { type = el.getAttribute("type"); } catch (Exception ignored) {}
        String valueToWrite = "date".equalsIgnoreCase(type) ? vDate : tDate;

        String jsSetValue = ""
                + "var el=arguments[0], val=arguments[1];"
                + "var proto=Object.getPrototypeOf(el);"
                + "var desc=Object.getOwnPropertyDescriptor(proto,'value');"
                + "if(desc && desc.set){desc.set.call(el,val);} else {el.value=val;}"
                + "el.dispatchEvent(new Event('input',{bubbles:true}));"
                + "el.dispatchEvent(new Event('change',{bubbles:true}));";

        try {
            ((JavascriptExecutor)d).executeScript(jsSetValue, el, valueToWrite);
            Thread.sleep(120);
        } catch (InterruptedException ignored) {}

        String got = el.getAttribute("value");
        if (!isYearValid(got)) {

            String alt = valueToWrite.equals(vDate) ? tDate : vDate;
            ((JavascriptExecutor)d).executeScript(jsSetValue, el, alt);
            try { Thread.sleep(120);} catch (InterruptedException ignored) {}
            got = el.getAttribute("value");
        }

        if (!isYearValid(got)) {
            try {
                el.click();
                By[] todayBtns = new By[]{
                        By.xpath("//*[normalize-space()='Bugün' or normalize-space()='Today']"),
                        By.cssSelector(".e-today, .datepicker-today, button[aria-label*='Bugün'], button[aria-label*='Today']")
                };
                for (By b : todayBtns) {
                    var btns = d.findElements(b);
                    if (!btns.isEmpty() && btns.get(0).isDisplayed()) {
                        btns.get(0).click();
                        Thread.sleep(120);
                        break;
                    }
                }
            } catch (Exception ignored) {}
        }
    }

    private boolean isYearValid(String value){
        if (value == null || value.isEmpty()) return false;
        try {
            int year = -1;
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                year = Integer.parseInt(value.substring(0,4));
            } else if (value.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                year = Integer.parseInt(value.substring(6,10));
            } else {
                java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{4})").matcher(value);
                if (m.find()) year = Integer.parseInt(m.group(1));
            }
            return year >= 1900;
        } catch (Exception e) {
            return false;
        }
    }

    private void setTimeSmart(By locator, java.time.LocalTime time){
        WebElement el = w.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", el);

        String hhmm = time.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        String type = ""; try { type = el.getAttribute("type"); } catch (Exception ignored) {}

        try {
            if ("time".equalsIgnoreCase(type)) {
                ((JavascriptExecutor)d).executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                                "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                        el, hhmm
                );
            } else {
                el.click();
                try { el.clear(); } catch (Exception ignored) {}
                el.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.DELETE);
                el.sendKeys(hhmm);
                el.sendKeys(Keys.TAB);
            }
            Thread.sleep(120);

            String v = el.getAttribute("value");
            if (v == null || !v.startsWith(hhmm)) throw new RuntimeException("time mismatch");
        } catch (Exception e) {
            ((JavascriptExecutor)d).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                            "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                    el, hhmm
            );
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
    }


    private void setSelectOrInput(By locator, String value){
        WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(10));
        WebElement el = w.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", el);

        String tag = el.getTagName().toLowerCase();
        if ("select".equals(tag)) {
            try {
                new Select(el).selectByVisibleText(value);
            } catch (Exception e) {
                for (WebElement opt : el.findElements(By.tagName("option"))) {
                    String t = opt.getText().trim();
                    if (t.equals(value) || t.contains(value)) { opt.click(); break; }
                }
            }
        } else {
            try {
                el.click();
                try { el.clear(); } catch (Exception ignored) {}
                el.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.DELETE);
                el.sendKeys(value);
                el.sendKeys(Keys.TAB);
            } catch (Exception e) {
                ((JavascriptExecutor)d).executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                                "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                        el, value
                );
            }
        }
    }

    public void saveAppointment() {
        By[] candidates = new By[]{ saveBtnExact, saveBtnText, saveBtnGeneric };

        boolean clicked = false;
        for (By loc : candidates) {
            try {
                WebElement btn = w.until(ExpectedConditions.elementToBeClickable(loc));
                try { btn.click(); }
                catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor)d).executeScript("arguments[0].click();", btn);
                }
                clicked = true;
                break;
            } catch (Exception ignored) {}
        }
        if (!clicked) throw new NoSuchElementException("Kaydet butonu bulunamadı.");

        try {
            w.until(ExpectedConditions.visibilityOfElementLocated(toastSuccess));
        } catch (Exception e) {
            System.out.println("Uyarı: Başarı mesajı yakalanamadı ama Kaydet tıklandı.");
        }
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }

    public void assertAppointmentSaved() {

        try {
            new WebDriverWait(d, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(toastSuccess));
            return;
        } catch (Exception ignored) {}

        try {
            new WebDriverWait(d, Duration.ofSeconds(2))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.invisibilityOfElementLocated(saveBtnExact),
                            ExpectedConditions.invisibilityOfElementLocated(saveBtnText),
                            ExpectedConditions.invisibilityOfElementLocated(saveBtnGeneric),
                            ExpectedConditions.not(ExpectedConditions.elementToBeClickable(saveBtnText))
                    ));
            return;
        } catch (Exception ignored) {}

        try {
            new WebDriverWait(d, Duration.ofSeconds(4))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                            By.xpath("//*[starts-with(@id,'dataform-')]")));
            return;
        } catch (Exception ignored) {}


        throw new TimeoutException("Kaydetme sonrası başarı sinyali bulunamadı (toast/buton/form).");
    }


    private void jsClick(WebElement el){ ((JavascriptExecutor)d).executeScript("arguments[0].click();", el); }
    private void scrollIntoView(WebElement el){ ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", el); }

    private final By apptCardWrapper = By.xpath("//*[@id='e-appointment-wrapper-0']/div/div[2]/div/div");

    private final By apptCardWrapperExact = By.xpath("//*[@id='e-appointment-wrapper-0']/div/div[2]/div/div");

    private final By apptCardWrapperAny   = By.xpath("(//*[starts-with(@id,'e-appointment-wrapper-')]/div/div[2]/div/div)[1]");
    private final By btnCheckInText    = By.xpath("//button[normalize-space()='Check-in' or normalize-space()='Check In']");
    private final By btnAdmissionText  = By.xpath("//button[normalize-space()='Admission']");
    private final By btnSilText        = By.xpath("//button[normalize-space()='Sil']");
    private final By btnDuzenleText    = By.xpath("//button[normalize-space()='Düzenle']");
    private final By anyDrawerLike     = By.xpath("//*[contains(@class,'drawer') or contains(@class,'dialog') or contains(@class,'modal') or contains(@class,'sidebar') or @role='dialog']");

    private void safeClick(WebElement el){
        try { el.click(); } catch (ElementClickInterceptedException e){
            ((JavascriptExecutor)d).executeScript("arguments[0].click();", el);
        }
    }
    private By apptCardWrapperByName(String name){

        String upper = name.toUpperCase();
        String xp =
                "//*[starts-with(@id,'e-appointment-wrapper-')]" +
                        "[.//*[contains(translate(normalize-space(.),'çğıöşüi','CGIOSUI'), '" + upper + "')]]" +
                        "/div/div[2]/div/div";
        return By.xpath(xp);
    }
    public void openAppointmentCard(String patientName){

        try { Thread.sleep(50); } catch (InterruptedException ignored) {}

        WebElement card = null;

        try {
            card = w.until(ExpectedConditions.elementToBeClickable(apptCardWrapperByName(patientName)));
        } catch (TimeoutException ignored) {}

        if (card == null){
            try { card = w.until(ExpectedConditions.elementToBeClickable(apptCardWrapperExact)); }
            catch (TimeoutException ignored) {}
        }

        if (card == null){
            card = w.until(ExpectedConditions.elementToBeClickable(apptCardWrapperAny));
        }

        scrollIntoView(card);
        safeClick(card);

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        w.until(driver ->
                !d.findElements(btnCheckInText).isEmpty()
                        || !d.findElements(btnAdmissionText).isEmpty()
                        || !d.findElements(btnSilText).isEmpty()
                        || !d.findElements(btnDuzenleText).isEmpty()
                        || !d.findElements(anyDrawerLike).isEmpty()
        );


    }

    private final By checkInBtn = By.cssSelector("button[data-testid='status-button']");
    private void sleep(long ms){ try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
    public void openAppointmentCardSimple(){


        WebElement card = w.until(ExpectedConditions.elementToBeClickable(apptCardWrapper));
        scrollIntoView(card);
        try { card.click(); } catch (ElementClickInterceptedException e) { jsClick(card); }
    }

    public void clickCheckInSimple(){
        sleep(2000);
        WebElement btn = w.until(ExpectedConditions.elementToBeClickable(checkInBtn));
        try { btn.click(); } catch (ElementClickInterceptedException e) { jsClick(btn); }
    }

    public void clickAdmissionSimple(){
        sleep(1000);
        WebElement btn = w.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-testid='status-button']")));
        scrollIntoView(btn);
        try { btn.click(); } catch (ElementClickInterceptedException e) { jsClick(btn); }
    }


    public void saveAdmissionSimple(){
        sleep(1000);
        WebElement save = w.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='submit' and (normalize-space()='Kaydet' or contains(@class,'e-primary'))]")));
        scrollIntoView(save);
        try { save.click(); } catch (ElementClickInterceptedException e) { jsClick(save); }
        sleep(2000);
    }

    public void closePhonePopup() {
        By closeBy = By.xpath("//*[@id='dialog-c0cda8d6-c49c-43a4-9e82-7b3264786f4a_title']/div/button");
        By[] fallbacks = new By[]{
                closeBy,
                By.xpath("//div[contains(@class,'dialog') or @role='dialog']//*[local-name()='button' and (@aria-label='Kapat' or @aria-label='Close')]"),
                By.xpath("//div[contains(@class,'dialog') or @role='dialog']//button[contains(@class,'close') or .//*[local-name()='i']]")
        };

        WebElement btn = null;
        for (By by : fallbacks) {
            try {
                WebDriverWait shortWait = new WebDriverWait(d, Duration.ofSeconds(2));
                btn = shortWait.until(ExpectedConditions.elementToBeClickable(by));
                break;
            } catch (TimeoutException ignored) {}
        }
        if (btn == null) return;
        scrollIntoView(btn);
        try { btn.click(); } catch (ElementClickInterceptedException e) { jsClick(btn); }

        try { w.until(ExpectedConditions.invisibilityOfElementLocated(fallbacks[0])); } catch (Exception ignored) {}
    }

    private final By modulesBtnTopBar = By.xpath("/html/body/main/nav/div/div[2]/div[1]/div/button");
    private final By randevuTileExact = By.xpath("//*[@id='e918bf71-2397-4191-8881-d303e4a8c9f7']/div[2]/div/div[2]/a[1]");
    private final By randevuTileText  = By.xpath("//a[.//text()[normalize-space()='Randevu'] or @href and contains(translate(.,'RAND','rand'),'randevu')]");

    public void openModulesDrawer(){
        WebElement btn = w.until(ExpectedConditions.elementToBeClickable(modulesBtnTopBar));
        scrollIntoView(btn);
        try { btn.click(); } catch (ElementClickInterceptedException e) { jsClick(btn); }
        sleep(100);
    }

    public void selectRandevuFromDrawer(){
        WebElement tile;
        try {
            tile = w.until(ExpectedConditions.elementToBeClickable(randevuTileExact));
        } catch (TimeoutException e) {
            tile = w.until(ExpectedConditions.elementToBeClickable(randevuTileText));
        }
        scrollIntoView(tile);
        try { tile.click(); } catch (ElementClickInterceptedException e) { jsClick(tile); }

        w.until(driver -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
        sleep(200);
    }

    public String getAppointmentStatus() {
        WebElement container = null;
        try {
            WebElement header = w.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[normalize-space()='Randevu Detayı']")));

            WebElement cur = header;
            for (int i = 0; i < 6 && cur != null; i++) {
                String cls = cur.getAttribute("class");
                if (cls != null && (cls.contains("card") || cls.contains("drawer") || cls.contains("dialog"))) {
                    container = cur;
                    break;
                }
                try { cur = cur.findElement(By.xpath("..")); } catch (Exception e) { break; }
            }
        } catch (TimeoutException ignored) {
        }


        By[] inPanel = new By[]{
                By.cssSelector("p.text-xs.truncate"),
                By.xpath(".//p[contains(@class,'truncate') or contains(@class,'text-xs')]"),
                By.xpath(".//*[normalize-space()='Tamamlandı' or normalize-space()='Bekliyor' or normalize-space()='Geldi']")
        };

        if (container != null) {
            for (By by : inPanel) {
                try {
                    WebElement el = container.findElement(by);
                    if (el.isDisplayed()) return el.getText().trim();
                } catch (Exception ignored) {}
            }
        }

        By[] global = new By[]{
                By.xpath("//*[normalize-space()='Tamamlandı']"),
                By.xpath("//*[normalize-space()='Bekliyor']"),
                By.xpath("//*[normalize-space()='Geldi']")
        };
        for (By by : global) {
            try {
                WebElement el = w.until(ExpectedConditions.visibilityOfElementLocated(by));
                return el.getText().trim();
            } catch (Exception ignored) {}
        }

        throw new NoSuchElementException("Randevu durumu bulunamadı (Tamamlandı/Bekliyor/Geldi görünür değil).");
    }


    private WebElement waitClick(By by){
        return new WebDriverWait(d, Duration.ofSeconds(2))
                .until(ExpectedConditions.elementToBeClickable(by));
    }
    private void click(By by){
        WebElement el = waitClick(by);
        ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        try { el.click(); } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor)d).executeScript("arguments[0].click();", el);
        }
    }

    private final By btnSil   = By.xpath("//button[normalize-space()='Sil']");
    private final By btnEvet  = By.xpath("//button[normalize-space()='Evet']");
    private final By btnTamam = By.xpath("//button[@id='okay-button' or normalize-space()='Tamam' or normalize-space()='OK']");
    private final By btnSilExact   = By.xpath("//*[@id='Schedule-5a5787d1-7f29-4c35-98ce-41eff06e1184']/div[5]/div/div[3]/div/div[1]/button[1]");
    private final By btnEvetExact  = By.xpath("//*[@id='dialog-d52558a7-cc5b-40e3-8544-034147b0811a']/div[4]/button[2]");

    public void clickDeleteOnCard(){
        try { click(btnSilExact); }
        catch (Exception ignored) { click(btnSil); }
    }
    public void confirmDeleteYes(){
        try { click(btnEvetExact); }
        catch (Exception ignored) { click(btnEvet); }
    }
    public void confirmOk(){
        click(btnTamam);
    }
    public void deleteAppointmentSimple(){
        clickDeleteOnCard();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        confirmDeleteYes();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        confirmOk();
    }


    private final By toastContainer = By.cssSelector(
            "#toast_default, " +
                    "#toast_default_container, " +
                    "div.e-toast-container[id^='toast'], " +
                    ".e-toast.e-toast-container"
    );

    private WebElement waitToastContainerVisible(int sec) {
        return new WebDriverWait(d, Duration.ofSeconds(sec))
                .until(ExpectedConditions.visibilityOfElementLocated(toastContainer));
    }

    private String extractText(WebElement el) {
        try {
            String t = el.getText();
            if (t != null && !t.trim().isEmpty()) return t.trim();
        } catch (Exception ignored) {}

        try {
            String jsText = (String) ((JavascriptExecutor) d)
                    .executeScript("return (arguments[0] && arguments[0].innerText) ? arguments[0].innerText.trim() : '';", el);
            return jsText == null ? "" : jsText.trim();
        } catch (Exception ignored) {}
        return "";
    }

    public String waitToastTextFromContainer(int timeoutSec) {
        WebElement cont = waitToastContainerVisible(timeoutSec);
        long end = System.currentTimeMillis() + timeoutSec * 1000L;
        try {
            WebElement msg = cont.findElement(By.cssSelector(".e-toast-message, .toast-success, .alert-success"));
            String t = extractText(msg);
            if (!t.isBlank()) return t;
        } catch (Exception ignored) {}

        String text = extractText(cont);
        while (text.isBlank() && System.currentTimeMillis() < end) {
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            text = extractText(cont);
        }
        return text;
    }

}
