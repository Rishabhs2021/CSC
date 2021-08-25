package pages;

import org.openqa.selenium.By;
import utils.WebDriverUtil;

public class BasePage {

    protected WebDriverUtil driver;

    public BasePage(WebDriverUtil driver){
        this.driver = driver;
    }

    public HomePage launchApplication(String url){
        driver.launchUrl(url);
        return new HomePage(driver);
    }

}
