package test;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BasePage;

public class GithubTest extends BaseTest{


    @Test(description = "automated test for the GitHub search using multiple filters")
    public void testGithubSearch()  {
        try {
            advSearchResultPage = homePage.
                    searchFor("react").
                    openAdvanceSearch().
                    setWrittenInLanguageFilter("JavaScript").
                    setManyStarsFilter(">45").
                    setFollowersFilter(">50").
                    setLicenseFilter("bsl-1.0").
                    doSearch();

        s_Assert.assertEquals(advSearchResultPage.getSearchCount(),1, "Verify result count is 1");
        s_Assert.assertEquals(advSearchResultPage.getSearchResultRepoName(),"mvoloskov/decider", "Verify repo name is correct");

        String readMeText = advSearchResultPage.goToRepo().getReadMeText();
        String requiredText = readMeText.substring(0,300);
        System.out.println(requiredText);
        test.get().log(Status.INFO,requiredText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            s_Assert.assertAll();
        }

    }
}
