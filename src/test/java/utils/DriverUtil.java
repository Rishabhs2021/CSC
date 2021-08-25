package utils;

import driver.LocalDriverFactory;
import driver.LocalDriverManager;
import org.openqa.selenium.WebDriver;



public class DriverUtil {
    private WebDriver driver;

    public void launchBrowser(String browser){
        driver = LocalDriverFactory.createInstance(browser);
        LocalDriverManager.setWebDriver(driver);
        LocalDriverManager.getDriver().manage().window().maximize();
    }

    public void closeBrowser(){
        LocalDriverManager.getDriver().close();
    }

    public void quitBrowser(){
        LocalDriverManager.getDriver().quit();
    }

    public void navigateTo(String url){
        LocalDriverManager.getDriver().navigate().to(url);

    }

    public WebDriver getDriver() {
        return this.driver;
    }

}
