package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import utils.Log;
import utils.WebDriverUtil;

public class HomePage extends BasePage{


    public HomePage(WebDriverUtil driver) {
        super(driver);
    }

    private By inputSearch = By.name("q");

    public SearchPage searchFor(String keyword) throws Exception {
        Log.info("searching for keyword :: "+keyword);
        driver.sendKeys(inputSearch,keyword);
        driver.sendKeys(inputSearch, Keys.ENTER);
        return new SearchPage(driver);
    }

}
