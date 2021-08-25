package utils;


import com.google.common.util.concurrent.Uninterruptibles;
import driver.LocalDriverFactory;
import driver.LocalDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WebDriverUtil {

    private WebDriver driver;
    private WebElement element;
    private FluentWait<WebDriver> wait;
    private int defaultTimeout;
    private String browser;
    private int POLLING_TIME=100;

    public WebDriverUtil(WebDriver driver,int timeout){
        //this.driver = LocalDriverManager.getDriver();
        this.driver = driver;
        this.defaultTimeout = timeout;
    }

    public void launchUrl(String url){
        driver.navigate().to(url);
    }

    public WebElement findElement(By by) throws Exception{
        element=waitForElement(by);
        return element;
    }

    public WebElement findElement(By by, int timeout) throws Exception{
        element=waitForElement(by,timeout);
        return element;
    }

    public List<WebElement> findElements(By by){
        List<WebElement> list;
        //turnOffImplicitWaits();
        list=driver.findElements(by);
        //turnOnImplicitWaits();
        return list;
    }

    public boolean IsLocatorVisible(By by) {
        boolean IsVisible=false;
        try{
            IsVisible=driver.findElement(by).isDisplayed();
        }catch(Exception e){

        }
        return IsVisible;
    }

    public boolean IsLocatorPresent(By by){
        boolean IsPresent=false;
        if(this.findElements(by).size()>0){
            IsPresent=true;
        }
        return IsPresent;
    }

    public void waitForLocatorToBeClickable(By locator) {
        long startTime = System.currentTimeMillis();
        wait = new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

    }

    public void waitForLocatorToBeClickable(By locator, int timeOut) {
        wait = new WebDriverWait(driver, timeOut,POLLING_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementoBeClickable(WebElement webElement) {
        long startTime = System.currentTimeMillis();
        wait = new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }

    public void waitForElementToBeClickable(WebElement webElement, int timeOut) {
        wait = new WebDriverWait(driver, timeOut,POLLING_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void waitForLocatorToBePresent(By by){
        wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));

    }

    public void waitForLocatorToBePresent(final By by, int timeOut,boolean ...failOnException) throws Exception{
        boolean IsException=false;
        try {
            wait=new WebDriverWait(driver, timeOut,POLLING_TIME);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception e) {
            IsException=true;
        }
        if(failOnException.length==0 && IsException){
            throw new Exception("Not Element "+by.toString() +"found in "+timeOut+" seconds");
        }
        if(failOnException.length!=0){
            if(failOnException[0]==true && IsException){
                throw new Exception("Not Element "+by.toString() +"found in "+timeOut+" seconds");
            }
        }

    }

    public void waitForLocatorToBeVisible(final By by){
        long startTime = System.currentTimeMillis();
        wait=new WebDriverWait(driver, defaultTimeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }

    public void waitForLocatorToBeVisible(final By by,int timeOut){
        long startTime = System.currentTimeMillis();
        wait=new WebDriverWait(driver, defaultTimeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }

    public boolean waitForPageLoad() {
        boolean isLoaded = false;
        long startTime = System.currentTimeMillis();
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").equals("complete");
            }
        };
        wait = new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(pageLoadCondition);
        isLoaded = true;
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        return isLoaded;
    }


    public boolean waitForAjax(){
        boolean isLoaded = false;
        long startTime = System.currentTimeMillis();
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return (window.jQuery != null) && (jQuery.active === 0);").equals("true");
            }
        };
        wait = new WebDriverWait(driver, defaultTimeout,POLLING_TIME).ignoring(TimeoutException.class);
        wait.until(pageLoadCondition);
        isLoaded = true;
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        return isLoaded;
    }

    public boolean waitForAjax(int timeOutInSeconds){
        boolean isLoaded = false;
        try {
            long startTime = System.currentTimeMillis();
            ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript(
                            "return (window.jQuery != null) && (jQuery.active === 0);").equals("true");
                }
            };
            wait = new WebDriverWait(driver, timeOutInSeconds,POLLING_TIME);
            wait.until(pageLoadCondition);
            isLoaded = true;
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isLoaded;
    }

    public boolean waitForPageLoad(int timeout) {
        boolean isLoaded = false;
        long startTime = System.currentTimeMillis();
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").equals("complete");
            }
        };
        wait = new WebDriverWait(driver, timeout,POLLING_TIME);
        wait.until(pageLoadCondition);
        isLoaded = true;
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        return isLoaded;
    }

    public void pauseExecutionFor(long lTimeInMilliSeconds) throws InterruptedException {
        Thread.sleep(lTimeInMilliSeconds);
    }

    public void clickLocatorFromList(By by,String sValueToBeSelected) throws Exception {
        sValueToBeSelected=sValueToBeSelected.toUpperCase();
        List<WebElement> elements=findElements(by);
        String text="";
        boolean flag = false;
        for (WebElement e : elements) {
            text=e.getText().toUpperCase();
            if (text.equalsIgnoreCase(sValueToBeSelected)) {
                clickElement(e);
                flag = true;
                break;
            }
        }
        if (flag == false) {
            throw new Exception("No match found in the list for value->"+ sValueToBeSelected);
        }
    }

    public void clickLocatorFromListByJS(By by,String sValueToBeSelected) throws Exception {
        sValueToBeSelected=sValueToBeSelected.toUpperCase();
        List<WebElement> elements=findElements(by);
        String text="";
        boolean flag = false;
        for (WebElement e : elements) {
            text=e.getText().toUpperCase();
            if (text.equalsIgnoreCase(sValueToBeSelected)) {
                moveToElement(e);
                clickElementByJS(e);
                flag = true;
                break;
            }
        }
        if (flag == false) {
            throw new Exception("No match found in the list for value->"+ sValueToBeSelected);
        }
    }

    public void doubleClickFromListByAction(By by,String sValueToBeSelected) throws Exception {
        sValueToBeSelected=sValueToBeSelected.toUpperCase();
        List<WebElement> elements=findElements(by);
        String text="";
        boolean flag = false;
        for (WebElement e : elements) {
            text=e.getText().toUpperCase();
            if (text.equalsIgnoreCase(sValueToBeSelected)) {
                //				moveToElement(e);
                pauseExecutionFor(2000);
                doubleClickElementByAction(e);
                //sendKeys(element, Keys.ENTER);
                flag = true;
                break;
            }
        }
        if (flag == false) {
            throw new Exception("No match found in the list for value->"+ sValueToBeSelected);
        }
    }


    public boolean IsElementTextPresentInList(By by, String value){
        boolean IsPresent=false;
        List<WebElement> elements=findElements(by);
        String text="";
        for (WebElement e : elements) {
            text=e.getText();
            if (text.equalsIgnoreCase(value)) {
                IsPresent=true;
                break;
            }
        }
        return IsPresent;
    }

    public void clickLocatorByJS(By by) throws Exception {
        WebElement element = findElement(by);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public String getTextByJS(WebElement element){
        String text="";
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        text=(String) executor.executeScript("return arguments[0].innerHTML;", element);
        return text;
    }


    public String getTextByJS(By by) throws Exception{
        String text="";
        WebElement element = this.findElement(by);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        text=(String) executor.executeScript("return arguments[0].innerHTML;", element);
        return text;
    }


    public void clickLocator(By by) throws Exception{
        element=findElement(by);
        waitForLocatorToBeClickable(by);
        element.click();
    }


    public void clickLocatorIfExists(By by) throws Exception{
        List<WebElement> elements=findElements(by);
        if(elements.size()>0){
            elements.get(0).click();
        }
    }

    public void clickLocator(By by, int timeout) throws Exception{
        element=findElement(by, timeout);
        waitForLocatorToBeClickable(by,timeout);
        element.click();
    }


    public void submitLocator(By by) throws Exception{
        element=findElement(by);
        //waitForLocatorToBeVisible(by);
        element.submit();
    }

    public void clickLocatorByAction(By by) throws Exception{
        //waitForPageLoad();
        element=findElement(by);
        //waitForLocatorToBeVisible(by);
        Actions actions = new Actions(driver);
        actions.click().build().perform();
    }

    public void clickElementByAction(WebElement element){
        Actions actions = new Actions(driver);
        actions.click(element).build().perform();
    }

    public void clickElement(WebElement element){
        waitForElementoBeClickable(element);
        element.click();
    }

    public void clickElementByJS(WebElement eElement){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void clickLocatorAndWaitFor(By by, int timeOut) throws Exception{
        this.findElement(by).click();
        pauseExecutionFor(timeOut);
    }

    public void sendKeys(By by,String value) throws Exception{
        waitForLocatorToBeVisible(by);
        element=this.findElement(by);
        element.clear();
        element.sendKeys(value);
    }

    public void sendSlowKeys(By by,String value, long timeout) throws Exception{
        waitForLocatorToBeVisible(by);
        element=this.findElement(by);
        element.clear();
        char[] values=value.toCharArray();
        for (char input : values){
            element.sendKeys(""+input);
            Uninterruptibles.sleepUninterruptibly(timeout, TimeUnit.MILLISECONDS);
        }
    }

    public void sendKeysLocatorByJS(By by,String text) throws Exception{
        element=this.findElement(by);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value=arguments[1];", element,text);
    }

    public void sendKeysElementByJS(WebElement eElement,String text){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value=arguments[1];", eElement,text);
    }

    public void sendKeys(By by,Keys key) throws Exception{
        waitForLocatorToBeVisible(by);
        element=this.findElement(by);
        element.sendKeys(key);
    }

    public void sendKeys(WebElement element,Keys key){
        element.sendKeys(key);
    }

    public void clear(By by) throws Exception{
        element=findElement(by);
        element.clear();
    }

    public String getText(By by){
        long startTime = System.currentTimeMillis();
        String text="";
        try {
            text=this.findElement(by).getText();
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
        } catch (Exception e) {

        }
        return text;
    }

    public String getText(WebElement element){
        return element.getText();
    }



    public boolean waitExplicitlyForLocatorPresence(By locator) throws InterruptedException {
        boolean flag = false;
        try {
            //turnOffImplicitWaits();
            for(int count=1; count<=5; count++) {
                pauseExecutionFor(500);
                if(IsLocatorPresent(locator)) {
                    flag = true;
                    if(count == 4)
                    break;
                }
            }
        } finally {
            //turnOnImplicitWaits();
        }
        return flag;
    }

    public boolean waitExplicitlyForLocatorPresence(By locator, int time) throws InterruptedException {
        boolean flag = false;
        try {
            for(int count=1; count<=time; count++) {
                pauseExecutionFor(500);
                //pauseExecutionFor(time);
                if(IsLocatorPresent(locator)) {
                    flag = true;
                    if(count == 4 || flag)

                    break;
                }
            }
        } finally {
        }
        return flag;
    }

    public String getAttribute(By by,String attribute) throws Exception{
        String value="";
        value=this.findElement(by).getAttribute(attribute);
        return value;
    }

    public String getAttribute(WebElement element,String attribute){
        String value="";
        value=element.getAttribute(attribute);
        return value;
    }

    public void selectValueFromOptions(By by, String valueToSelect) throws Exception{
        Select select = new Select(findElement(by));
        select.selectByValue(valueToSelect);
    }

    public void selectTextFromOptions(By by, String textToSelect) throws Exception{
        try {
            long startTime = System.currentTimeMillis();
            Select select = new Select(findElement(by));
            select.selectByVisibleText(textToSelect);
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
        } catch (Exception e) {
            throw new Exception("Option with value ::"+textToSelect+" is not present in dropdown :: "+by.toString());
        }
    }

    public void selectTextFromOptions(WebElement element, String textToSelect){
        Select select = new Select(element);
        select.selectByVisibleText(textToSelect);
    }

    public void selectIndexFromOptions(By by, int indexToSelect) throws Exception{
        Select select = new Select(findElement(by));
        select.selectByIndex(indexToSelect);
    }

    public void selectIndexFromOptions(WebElement element, int indexToSelect){
        Select select = new Select(element);
        select.selectByIndex(indexToSelect);
    }

    public String getSelectedValueFromOptions(WebElement element){
        String text="";
        Select select = new Select(element);
        text=select.getFirstSelectedOption().getText();
        return text;
    }

    public String getSelectedValueFromOptions(By by) throws Exception{
        String text="";
        Select select = new Select(findElement(by));
        text=select.getFirstSelectedOption().getText();
        return text;
    }

    public void waitForLocatorToBeStale(By by) throws Exception{
        wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.stalenessOf(findElement(by)));
    }

    public void waitForLocatorToBeDeleted(final By by){

        long startTime   = System.currentTimeMillis();
        wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return findElements(by).size()==0;
            }
        });
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

    }

    public void waitForLocatorToBeDeleted(final By by,int time){

        long startTime   = System.currentTimeMillis();
        wait=new WebDriverWait(driver, time,POLLING_TIME);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return findElements(by).size()==0;
            }
        });
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

    }
    public void switchToFrame(By by) throws Exception{

        driver.switchTo().frame(findElement(by));
    }

    public void switchToFrame(int frameIndex){

        driver.switchTo().frame(frameIndex);
    }

    public void switchToFrame(String frameName){

        driver.switchTo().frame(frameName);
    }

    public void switchToDefault(){

        driver.switchTo().defaultContent();

    }

    public String getScreenshot() throws IOException {
        String FullSnapShotFilePath = "";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String sFilename = "";
        sFilename = "Screenshot-" + getDateTime() + ".png";
        FullSnapShotFilePath = System.getProperty("user.dir")+ "\\test-output\\ScreenShots\\" + sFilename;
        FileUtils.copyFile(scrFile, new File(FullSnapShotFilePath));
        return FullSnapShotFilePath;
    }

    private String getDateTime() {
        String sDateTime = "";
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            String strTime = sdfTime.format(now);
            strTime = strTime.replace(":", "-");
            sDateTime = "D" + strDate + "_T" + strTime;
        } catch (Exception e) {
            System.err.println(e);
        }
        return sDateTime;
    }


    public String getRandomNumber(){
        return ""+System.currentTimeMillis();
    }

    public String getTitle(){
        return driver.getTitle();
    }

    public void waitForFrameAndSwitchToIt(By by){

        wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public void waitForFrameAndSwitchToIt(By by, int timeOut){

        long startTime = System.currentTimeMillis();
        wait=new WebDriverWait(driver, timeOut,POLLING_TIME);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

    }

    public void waitForFrameAndSwitchToIt(int frameNumber){
        wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNumber));
    }

    public void waitForFrameAndSwitchToIt(String frameName){
        wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    public void waitForDropDownToLoad(final By by) throws Exception{

        long startTime = System.currentTimeMillis();
        final Select dropList=new Select(findElement(by));
        wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return (!dropList.getOptions().isEmpty());
            }
        });
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

    }

    public void waitForDropDownToLoad(final By by, int timeOut) throws Exception{

        long startTime = System.currentTimeMillis();
        final Select dropList=new Select(findElement(by));
        wait=new WebDriverWait(driver, timeOut,POLLING_TIME);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return (!dropList.getOptions().isEmpty());
            }
        });
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

    }
    //	public void highlightElementBorder(WebElement element) {
    //		if(config.getProperty("highlight").equalsIgnoreCase("1")){
    //			try{
    //				for (int i = 0; i < 1; i++) {
    //					JavascriptExecutor js = (JavascriptExecutor) driver;
    //					js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
    //							element, "background: yellow;border: 4px solid red;");
    //					Thread.sleep(500L);
    //					js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
    //							element, "");
    //				}
    //			}catch(Exception e){
    //				e.printStackTrace();
    //			}
    //		}
    //
    //	}

    public int getNumberOfWindows(){
        Set<String>allHanles= driver.getWindowHandles();
        return allHanles.size();
    }

    public void switchToLastWindow(){
        Set<String> allHanles= driver.getWindowHandles();
        Iterator<String> iterator=allHanles.iterator();
        String lastHandle="";
        while(iterator.hasNext()){
            lastHandle=iterator.next();
        }

        driver.switchTo().window(lastHandle);
    }

    public void switchToWindow(String windowTitle) {
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(windowTitle)) {
                return;
            }
        }
    }

    public boolean IsCheckBoxSelected(By by) throws Exception{
        return findElement(by).isSelected();
    }

    public void refreshPage(){

        driver.navigate().refresh();
    }

    public void waitForTitle(final String title){

        wait=new WebDriverWait(driver, defaultTimeout);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return driver.getTitle().equalsIgnoreCase(title);
            }
        });

    }

    public void doubleClickElement(WebElement element) throws InterruptedException{
        clickElement(element);
        pauseExecutionFor(500);
        clickElement(element);
    }

    public void doubleClickElementByAction(WebElement element) throws InterruptedException{

        Actions action = new Actions(driver);
        action.doubleClick(element).build().perform();
    }

    public void doubleClickLocatorByAction(By by) throws Exception{

        WebElement element=findElement(by);
        Actions action = new Actions(driver);
        action.doubleClick(element).build().perform();
    }

    public void doubleClickElement(By by) throws Exception{
        element=findElement(by);
        clickElement(element);
        pauseExecutionFor(500);
        clickElement(element);
    }



    public void waitForAttributeChange( By by,final String attributeName,final String attributeValue) throws Exception{

        final WebElement element=findElement(by);
        wait=new WebDriverWait(driver, defaultTimeout);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return element.getAttribute(attributeName).equalsIgnoreCase(attributeValue);
            }
        });

    }

    public void waitForTextChange( By by,final String attributeValue) throws Exception{

        final WebElement element=findElement(by);
        wait=new WebDriverWait(driver, defaultTimeout);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return element.getText().equalsIgnoreCase(attributeValue);
            }
        });

    }


    public WebElement waitForElement(final By findBy) throws Exception {
        long startTime = System.currentTimeMillis();
        WebElement webElement = null;
        try{
            wait=new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
            webElement= wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement element = driver.findElement(findBy);
                    return element;
                }
            });
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
        }catch(Exception e){
            throw new Exception("Error while waiting for element ::"+findBy.toString());
        }
        return webElement;
    }

    public WebElement waitForElement(final By findBy, int timeout) throws Exception {
        long startTime = System.currentTimeMillis();
        WebElement webElement = null;
        try{
            wait=new WebDriverWait(driver, timeout,POLLING_TIME);
            webElement= wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement element = driver.findElement(findBy);
                    return element;
                }
            });
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
        }catch(Exception e){
            throw new Exception("Error while waiting for element ::"+findBy.toString());
        }
        return webElement;
    }






    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void waitAndAcceptAlert(){
        wait= new WebDriverWait(driver, defaultTimeout,POLLING_TIME);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert al = driver.switchTo().alert();
        al.accept();
    }

    public void acceptAlert(){
        Alert al = driver.switchTo().alert();
        al.accept();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public void moveToElement(WebElement element){
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    public void moveToElementByJS(WebElement element){
        ((JavascriptExecutor)
                driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public void moveToLocator(By by) throws Exception{
        WebElement element=this.findElement(by);
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    public void moveToLocatorByJS(By by) throws Exception{
        WebElement element=this.findElement(by);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);",element);
    }

    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 3) {
            try {

                driver.findElement(by).click();
                result = true;

                break;
            } catch(StaleElementReferenceException  e) {

            }
            attempts++;
        }
        return result;
    }

    public void waitForElementoBeStale(WebElement webElement, int time) {

        long startTime = System.currentTimeMillis();
        wait = new WebDriverWait(driver, time,POLLING_TIME);
        wait.until(ExpectedConditions.stalenessOf(webElement));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

    }

    public void sendESCKey() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void scrollDownByJS(int distance) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, "+distance+")");
    }
    // Clicking some Element untill required elements are not visible on screen
    public void waitForElementToBePresent(By loadLocator,By expectedLocator,int time) {
        try {
            do{
                driver.findElement(loadLocator).click();
                pauseExecutionFor(1000);
                waitExplicitlyForLocatorAbsence(By.xpath("//div[text()='Loading...']"),200);
                if(driver.findElements(expectedLocator).size()>0)
                    break;
                else
                    Thread.sleep(time);
            }while(driver.findElements(expectedLocator).size()==0);
        }
        catch(Exception e) {

        }
    }

    public void sendKeysByAction(By locator, String text) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(locator));
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();
    }

    public ExpectedCondition<Boolean> absenceOfElementLocated(
            final By locator) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    driver.findElement(locator);
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                } catch (StaleElementReferenceException e) {
                    return true;
                }
            }

            @Override
            public String toString() {
                return "element to not being present: " + locator;
            }
        };
    }

    public void sendDOWN_ENTERKey() throws InterruptedException {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DOWN).build().perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.RETURN).build().perform();
    }

    public boolean waitExplicitlyForLocatorAbsence(By locator, int time) throws InterruptedException {
        boolean flag = false;
        try {
            for(int count=1; count<=time; count++) {

                pauseExecutionFor(1000);
                if(!IsLocatorVisible(locator)) {
                    flag = true;
                    if(count == 4 || flag)

                    break;
                }
            }
        } finally {
        }
        return flag;
    }

    public void scrollTillElement(String path) {

        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebElement Element = driver.findElement(By.xpath(path));

        //This will scroll the page till the element is found
        js.executeScript("arguments[0].scrollIntoView();", Element);

    }

    public void sendDOWNKey() throws InterruptedException {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DOWN).build().perform();
        Thread.sleep(2000);
    }

    public void maximizeScreen() throws InterruptedException {
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }



}
