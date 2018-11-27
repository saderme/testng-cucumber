package com.framework.utilities;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;

import com.framework.core.TestLogger;
import com.framework.customexception.NotCurrentPageException;

public class WebUIHelper {
	
	private static Logger log = TestLogger.getLogger(WebUIHelper.class);
	private static WebDriver driver;

	public WebUIHelper (WebDriver drv) {
		driver = drv;
		TestLogger.logRepInfo("WebUIHelper has been initialised");
	}
	
	public static void mouseDown(WebElement element) {
		TestLogger.logRepInfo("MouseDown " + element.toString());
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseDown(null);
	}

	public static void mouseDown(By by) {
		WebElement element = driver.findElement(by);
		TestLogger.logRepInfo("MouseDown " + element.toString());
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseDown(null);
	}

	public static void mouseOver(By by) {
		TestLogger.logRepInfo("MouseOver " + by.toString());
		WebElement element = driver.findElement(by);
		// build and perform the mouseOver with Advanced User Interactions API
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
		final Locatable hoverItem = (Locatable) element;
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());
	}

	public static void mouseOver(WebElement element) {
		TestLogger.logRepInfo("MouseOver " + element.toString());
		// build and perform the mouseOver with Advanced User Interactions API
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
		final Locatable hoverItem = (Locatable) element;
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());
	}

	public static void mouseUp(By by) {
		TestLogger.logRepInfo("MouseUp " + by.toString());
		WebElement element = driver.findElement(by);
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseUp(null);
	}

	public static void mouseUp(WebElement element) {
		TestLogger.logRepInfo("MouseUp " + element.toString());
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseUp(null);
	}

	/******************************************************************
	 * UI Element Helpers
	 *****************************************************************/
	public static final void selectFrame(final int index) {
		TestLogger.logRepInfo("select frame using index" + index);
		driver.switchTo().frame(index);
	}

	public static final void selectFrame(final By by) {
		TestLogger.logRepInfo("select frame, locator={\"" + by.toString() + "\"}");
		driver.switchTo().frame(driver.findElement(by));
	}

	public static final void selectFrame(final String locator) {
		TestLogger.logRepInfo("select frame, locator={\"" + locator + "\"}");
		driver.switchTo().frame(locator);
	}

	public static final void selectWindow() throws NotCurrentPageException {
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
		WaitHelper.waitForSeconds(1);
		AssertionHelper.assertCurrentPage(true);
	}

	public static final void selectWindow(final int index) throws NotCurrentPageException {
		TestLogger.logRepInfo("select window, locator={\"" + index + "\"}");
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[index]);
	}

	public static final void selectNewWindow() throws NotCurrentPageException {
		TestLogger.logRepInfo("select new window");
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
		WaitHelper.waitForSeconds(1);
	}
	
	public static WebElement getElement(By by) {
		WebElement element = driver.findElement(by);
        return element;
    }
	
	public static String getTitle() {
		return driver.getTitle();
	}	
	
	public static void switchToDefaultContent() {

		try {
			driver.switchTo().defaultContent();
		} catch (final UnhandledAlertException e) {
		}
	}
	
	public static void waitForGivenURLTermToAppear(final String urlTerm, final int waitCount) {

		for (int index = 0; index < waitCount; index++) {

			if (DriverFactory.getInstance().getDriver().getCurrentUrl().contains(urlTerm)) {
				break;
			} else {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
				index = index + 1;
			}
		}
	}
	
	public static WebElement getElement(final By by, final String elementName) {
		WebElement element = null;

		try {
			element = driver.findElement(by);
		} catch (final Exception e) {
			TestLogger.logRepError(elementName + " is not found with locator - " + by.toString());
		}

		return element;
	}

	public static String getElementUrl(final By by, final String name) {
		return getElement(by, name).getAttribute("href");
	}

	public static String getElementText(final By by, final String name) {
		return getElement(by, name).getText();
	}

	public static String getElementSrc(final By by, final String name) {
		return getElement(by, name).getAttribute("src");
	}
	
	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}	
	
    public static String getBodyText() {
		final WebElement body = driver.findElement(By.tagName("body"));
		return body.getText();
	}
    
    public static String getCssValue(By by, final String propertyName) {
		WebElement element = driver.findElement(by);
        return element.getCssValue(propertyName);
    }
    
    public static String getAttribute(By by, final String name) {
		WebElement element = driver.findElement(by);
        return element.getAttribute(name);
    }   

    public static List<WebElement> getAllElements(By by) {
		WebElement element = driver.findElement(by);
        return driver.findElements(by);
    }
    
	public static final void goBack() {
		TestLogger.logRepInfo("goBack");
		driver.navigate().back();
	}
	public static final void goForward() {
		TestLogger.logRepInfo("goForward");
		driver.navigate().forward();
	}

	public static void click(By by) {
		WebElement element = driver.findElement(by);
		element.click();
	}

	public static void click(WebElement element) {
		element.click();
	}

    public static boolean isElementPresent(By by) {

        int count = 0;
        try {
            count = driver.findElements(by).size();
        } catch (final RuntimeException e) {
            if (e instanceof InvalidSelectorException) {
            	TestLogger.logRepInfo("Got InvalidSelectorException, retry");
            	WaitHelper.waitForSeconds(2);
                count = driver.findElements(by).size();
            } else if ((e.getMessage() != null) && e.getMessage().contains("TransformedEntriesMap cannot be cast to java.util.List")) {
            	TestLogger.logRepInfo("Got CastException, retry");
            	WaitHelper.waitForSeconds(2);
                count = driver.findElements(by).size();
            } else {
                throw e;
            }
        }

        if (count == 0) {return false;}
        return true;
    }

	public static  boolean isTextPresent(final String text) {
		final WebElement body = driver.findElement(By.tagName("body"));
		return body.getText().contains(text);
	}	
	
	public static boolean isTextPresent(WebElement element, final String text) {
		return element.getText().contains(text);
	}

	public static boolean isTextPresent(By by, final String text) {
		return driver.findElement(by).getText().contains(text);
	}

	public static void sendKeys(By by, final CharSequence... arg) {
		WebElement element = driver.findElement(by);
		element.sendKeys(arg);
	}

	public static void sendKeys(WebElement element, final CharSequence... arg) {
		element.sendKeys(arg);
	}

	public static boolean isSelected(By by) {
		return driver.findElement(by).isSelected();
	}

	public static boolean isSelected(WebElement element) {
		return element.isSelected();
	}

	public static boolean isEnabled(By by) {
		return driver.findElement(by).isEnabled();
	}

	public static boolean isEnabled(WebElement element) {
		return element.isEnabled();
	}

	public static boolean isDisplayed(By by) {
		try {
			WebElement element = driver.findElement(by);
			return element.isDisplayed();
		} catch (final StaleElementReferenceException e) {
			return driver.findElement(by).isDisplayed();
		} catch (final Exception e) {
			return false;
		}
	}
	
	public static final void selectWindow(final String windowName) throws NotCurrentPageException {

		if (windowName == null) {
			try {
				driver.switchTo().window(windowName);
			} catch (final Exception e) {
				driver.switchTo().defaultContent();
			}
		}
	}

	public static void refreshPageTillTextAppears(final String text, final int waitPeriodInSec) throws InterruptedException {
		for (int i = 0; i <= waitPeriodInSec; i++) {
			if (!driver.getPageSource().contains(text)) {
				Thread.sleep(1000);
				driver.navigate().refresh();
			} else {
				break;
			}
		}
	}
	public static String getValue(By by) {
		WebElement element = driver.findElement(by);
		return element.getAttribute("value");
	}

	public static int getWidth(By by) {
		WebElement element = driver.findElement(by);
		return element.getSize().getWidth();
	}
	
    public static int getHeight(By by) {
		WebElement element = driver.findElement(by);
        return element.getSize().getHeight();
    }
    
	public static String getText(By by) {
		WebElement element = driver.findElement(by);
		return element.getText();
	}

	public static String getTagName(By by) {
		WebElement element = driver.findElement(by);
		return element.getTagName();
	}

	public static Dimension getSize(By by) {
		WebElement element = driver.findElement(by);
		return element.getSize();
	}

	public static Point getLocation(By by) {
		WebElement element = driver.findElement(by);
		return element.getLocation();
	}

	public static void resizeWindow(final int width, final int height) {
		try {
			Dimension size = new Dimension(width, height);
			driver.manage().window().setPosition(new Point(0, 0));
			driver.manage().window().setSize(size);
		} catch (Exception ex) {
		}
	}

	public static void acceptAlert() throws NotCurrentPageException {
		final Alert alert = driver.switchTo().alert();
		alert.accept();
		driver.switchTo().defaultContent();
	}

	public static String cancelConfirmation() throws NotCurrentPageException {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();
		alert.dismiss();
		driver.switchTo().defaultContent();

		return seenText;
	}

	public static Alert getAlert() {
		final Alert alert = driver.switchTo().alert();

		return alert;
	}

	public static String getAlertText() {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();
		return seenText;
	}
	
	public static String getConfirmation() {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();

		return seenText;
	}

	public static String getPrompt() {
		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();

		return seenText;
	}
	
	public static void switchToWindow(final Set<String> allWindows, final String currentWindow) {

		for (final String window : allWindows) {

			if (!window.equals(currentWindow)) {
				DriverFactory.getInstance().getDriver().switchTo().window(window);
			}
		}
	}
	
    public static void clickSpecificDatefromCalendar(WebElement calendar, final By findby, String selectValue) {
    	List<WebElement> dates = (List<WebElement>) calendar.findElements(findby);
    	
    	Calendar cal = Calendar.getInstance();
    	//int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
    	//String domstr = String.valueOf(dayofmonth);
    	
    	for (WebElement date:dates) {
    		if (date.getText().equals(selectValue)){
    			date.click();
    			break;
    		}
     	}
    }  
	
    public static void clickSpecificValuefromDropDown(WebElement dropdown, final By findby, String selectValue) {
    	List<WebElement> ddentries = (List<WebElement>) dropdown.findElements(findby);
    	
    	for (WebElement ddentry:ddentries) {
    		if (ddentry.getText().equals(selectValue)){
    			ddentry.click();
    			break;
    		}
     	}
    }
    
    public static void clickAt(By by, final String value) {
		WebElement element = driver.findElement(by);

        final String[] parts = value.split(",");
        final int xOffset = Integer.parseInt(parts[0]);
        final int yOffset = Integer.parseInt(parts[1]);

        try {
            new Actions(driver).moveToElement(element, xOffset, yOffset).click().perform();
        } catch (final InvalidElementStateException e) {
            e.printStackTrace();
            element.click();
        }

        try {  driver.switchTo().alert().accept();
        } catch (final NoAlertPresentException e) {
            e.printStackTrace();
        }
    }
    

}
