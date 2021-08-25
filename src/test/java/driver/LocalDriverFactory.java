package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class LocalDriverFactory {

    private static final Supplier<WebDriver> chromeSupplier = () -> {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    };

    private static final Supplier<WebDriver> firefoxSupplier = () -> {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    };

    private final static Map<String,Supplier<WebDriver>> MAP = new HashMap<>();

    static {
        MAP.put("chrome", chromeSupplier);
        MAP.put("firefox", firefoxSupplier);
    }

    public static WebDriver createInstance(String browser){
        return MAP.getOrDefault(browser,chromeSupplier).get();

    }
 }
