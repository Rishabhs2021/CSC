package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;



/**
 * Class for dynamic locator templates definition<BR>
 * Usage:<BR>
 * 	ByT locator = ByT.xpath("//table/tr[%1$d]/td[%2$d]/a[contains(text(),'%3$s')]");<BR>
 * 	WebLink link = new WebLink(locator.format(3, 5, "Click"));<BR>
 * ByT should never be used to construct static locators which can be fully defined before running the test.
 */
public class ByT extends By{
	private enum Strategy {CLASSNAME, CSSSELECTOR, ID, LINKTEXT, NAME, PARTIALLINKTEXT, TAGNAME, XPATH, JSLOCATOR};

	private String template;
	private Strategy strategy;

	private ByT(String t, Strategy s){
		template = t;
		strategy = s;
	}

	public static ByT className(String className){
		return new ByT(className, Strategy.CLASSNAME);
	}

	public static ByT cssSelector(String selector){
		return new ByT(selector, Strategy.CSSSELECTOR);
	}

	public static ByT id(String id){
		return new ByT(id, Strategy.ID);
	}

	public static ByT linkText(String linkText){
		return new ByT(linkText, Strategy.LINKTEXT);
	}

	public static ByT name(String name){
		return new ByT(name, Strategy.NAME);
	}

	public static ByT partialLinkText(String linkText){
		return new ByT(linkText, Strategy.PARTIALLINKTEXT);
	}

	public static ByT tagName(String tagName){
		return new ByT(tagName, Strategy.TAGNAME);
	}

	public static ByT xpath(String xpathExpression){
		return new ByT(xpathExpression, Strategy.XPATH);
	}

	public static ByT jsLocator(String jsLocator){
		return new ByT(jsLocator, Strategy.JSLOCATOR);
	}
	
	/**
	 * Generate locator instance from template
	 * @param args - arguments for template formatting (see {@link String#format(String, Object...))
	 * @return - instance of By class
	 */
	public By format(Object...args){
		String locator = String.format(template, args);
		switch(strategy){
		case CLASSNAME:
			return new ByClassName(locator);
		case CSSSELECTOR:
			return new ByCssSelector(locator);
		case ID:
			return new ById(locator);
		case LINKTEXT:
			return new ByLinkText(locator);
		case NAME:
			return new ByName(locator);
		case PARTIALLINKTEXT:
			return new ByPartialLinkText(locator);
		case TAGNAME:
			return new ByTagName(locator);
		case XPATH:
			return new ByXPath(locator);
		default:
			Log.error("Unsupported location strategy: " + strategy);
			return null;
		}
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		// TODO Auto-generated method stub
		return null;
	}
}
