package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import driver.LocalDriverFactory;
import driver.LocalDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.AdvSearchResultPage;
import pages.BasePage;
import pages.HomePage;
import utils.*;
import utils.ExtentReports.ExtentManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class BaseTest {

    protected ConfigUtil configUtil;
    protected DriverUtil driverUtil;
    protected WebDriverUtil webDriverUtil;
    protected WebDriver driver;
    protected BasePage basePage;
    protected HomePage homePage;
    protected AdvSearchResultPage advSearchResultPage;
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected SoftAssert s_Assert;

    @BeforeTest
    public void beforeTest() throws MalformedURLException, UnknownHostException {
        configUtil =  ConfigFactory.create(ConfigUtil.class);
        driver = LocalDriverFactory.createInstance(configUtil.browser());
        driver.manage().window().maximize();
        LocalDriverManager.setWebDriver(driver);
        webDriverUtil = new WebDriverUtil(driver,Integer.parseInt(configUtil.timeout()));
        basePage = new BasePage(webDriverUtil);
        homePage = basePage.launchApplication(configUtil.url());
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, ITestResult result) {
        long id = Thread.currentThread().getId();
        Log.info("Before test-method. Thread id is: " + id);
        Log.info("*************************** STARTING TEST CASE " + method.getDeclaringClass() + " --> "
                + method.getName() + " **************");
        Test t;
        t = method.getAnnotation(Test.class);
        System.out.println(method.getName() + "  " + t.description());
        ExtentTest extentTest = extent.createTest(method.getName(),t.description());
        test.set(extentTest);
        s_Assert = new SoftAssert(extentTest);
    }



    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult result) throws IOException {
        Log.info("*************************** TEST CASE END " + method.getName() + " **************");
        if (result.getStatus() == ITestResult.FAILURE) {
            Log.debug(result.getThrowable().getStackTrace().toString());

            test.get()
                    .fail("Automation script issue !!! Test case failed due to exception: "
                            + result.getThrowable().getStackTrace().toString() + "at method "
                            + result.getThrowable().getStackTrace()[0].getMethodName() + " at line number: "
                            + "at line number " + result.getThrowable().getStackTrace()[0].getLineNumber());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.get().skip(result.getThrowable());
            Log.warn("Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("Automation Script passed");
            Log.info("Test case is passed ==> "+ result.getName());
        }
    }

    @AfterTest
    public void afterTest(){
        extent.flush();
        LocalDriverManager.getDriver().quit();
    }
}
