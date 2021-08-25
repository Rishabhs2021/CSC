package pages;

import org.openqa.selenium.By;
import utils.Log;
import utils.WebDriverUtil;

public class SearchPage extends BasePage{

    public SearchPage(WebDriverUtil driver) {
        super(driver);
    }

    private By lnkAdvSearch = By.linkText("Advanced search");
    private By lblAdvSearch = By.id("search-title");
    private By selectLanguage = By.id("search_language");
    private By btnSearch = By.cssSelector("div[class=\"d-flex d-md-block flex-shrink-0 pt-2 pt-md-0\"] button[type=\"submit\"]");
    private By inputWithThisManyStars = By.id("search_stars");
    private By inputFollowers = By.id("search_followers");
    private By selectLicense = By.id("search_license");

    public SearchPage openAdvanceSearch() throws Exception {
        Log.info("opening advanced search link");
        driver.clickLocator(lnkAdvSearch);
        driver.waitForLocatorToBeVisible(lblAdvSearch);
        return this;
    }

    public SearchPage setWrittenInLanguageFilter(String filterValue) throws Exception {
        Log.info("Setting written in langauge filter");
        driver.selectValueFromOptions(selectLanguage,filterValue);
        return this;
    }

    public SearchPage setManyStarsFilter(String filerValue) throws Exception {
        Log.info("Setting Many stars filter");
        driver.sendKeys(inputWithThisManyStars,filerValue);
        return this;
    }

    public SearchPage setFollowersFilter(String filerValue) throws Exception {
        Log.info("Setting followers filter");
        driver.sendKeys(inputFollowers,filerValue);
        return this;
    }

    public SearchPage setLicenseFilter(String filterValue) throws Exception {
        Log.info("Setting license filter");
        driver.selectValueFromOptions(selectLicense,filterValue);
        return this;
    }

    public AdvSearchResultPage doSearch() throws Exception {
        Log.info("Clicking Search button");
        driver.clickLocator(btnSearch);
        return new AdvSearchResultPage(driver);
    }

}
