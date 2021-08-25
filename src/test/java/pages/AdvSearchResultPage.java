package pages;

import org.openqa.selenium.By;
import utils.Log;
import utils.WebDriverUtil;

public class AdvSearchResultPage extends BasePage{

    private By lblSearchCount = By.cssSelector("div.d-flex>h3");
    private By lblSearchRepoName = By.cssSelector("div.text-normal>a");
    private By textReadMe = By.cssSelector("article.markdown-body.entry-content.container-lg");

    public AdvSearchResultPage(WebDriverUtil driver) {
        super(driver);
    }

    public int getSearchCount(){
        int result=0;
        String text = driver.getText(lblSearchCount);
        result = Integer.parseInt(text.replaceAll("[^0-9]", ""));
        Log.info("Search result :: "+result);
        return result;
    }

    public String getSearchResultRepoName(){
        return driver.getText(lblSearchRepoName);
    }

    public AdvSearchResultPage goToRepo() throws Exception {
        driver.clickLocator(lblSearchRepoName);
        return  this;
    }

    public String getReadMeText(){
        return driver.getText(textReadMe);
    }
}
