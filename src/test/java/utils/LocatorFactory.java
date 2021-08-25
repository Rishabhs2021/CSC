package utils;

import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class LocatorFactory {

    private LocatorFactory(){};

    private static final Map<String, Function<String, By>> MAP = new HashMap<>();

    private static final Function<String,By> XPATH = (value) -> By.xpath(value);
    private static final Function<String,By> CSS = (value) -> By.cssSelector(value);
    private static final Function<String,By> ID = (value) -> By.id(value);
    private static final Function<String,By> TAG = (value) -> By.tagName(value);
    private static final Function<String,By> NAME = (value) -> By.name(value);

    static {
        MAP.put("xpath",XPATH);
        MAP.put("id",ID);
        MAP.put("css",CSS);
        MAP.put("tag",TAG);
        MAP.put("name",NAME);
    }

    public static By getByT(String locator, String value){
        return MAP.get(locator).apply(value);
    }

}
