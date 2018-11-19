package com.framework.utilities;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.framework.core.GlobalConfig;
import com.framework.core.TestLogger;
import com.framework.customexception.NotCurrentPageException;
import com.framework.webelements.ButtonElement;
import com.framework.webelements.HtmlElement;
import com.framework.webelements.Table;

public class MasterHelper {

	private static Logger log = TestLogger.getLogger(MasterHelper.class);
	private static WebDriver driver;

	public MasterHelper(WebDriver drv) {
		driver = drv;
		TestLogger.logRepInfo("MasterHelper has been initialised");
	}

	public static void refreshMasterDriver() {
		driver = DriverFactory.getInstance().getDriver();
	}

	public static void refreshMasterDriver(String browser) {
		driver = DriverFactory.getInstance().getDriver(browser);
	}

	/******************************************************************
	 * Wait Helpers
	 *****************************************************************/
	public static void setImplicitWait(long timeout, TimeUnit unit) {
		TestLogger.logRepInfo("Implicit Wait has been set to: " + timeout);
		driver.manage().timeouts().implicitlyWait(timeout, unit);
	}

	public static void WaitForElementClickable(WebElement element, int timeOutInSeconds) {
		TestLogger.logRepInfo("waiting for :" + element.toString() + " for :" + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		TestLogger.logRepInfo("element " + element.toString() + " is clickable now");
	}

	public boolean waitForElementNotPresent(WebElement element, long timeOutInSeconds) {
		TestLogger.logRepInfo("waiting for :" + element.toString() + " for :" + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		boolean status = wait.until(ExpectedConditions.invisibilityOf(element));
		TestLogger.logRepInfo("element " + element.toString() + " is invisibile now");
		return status;
	}

	public void waitForframeToBeAvailableAndSwitchToIt(WebElement element, long timeOutInSeconds) {
		TestLogger.logRepInfo("waiting for :" + element.toString() + " for :" + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		TestLogger.logRepInfo("frame is available and switched");
	}

	public void pageLoadTime(long timeout, TimeUnit unit) {
		TestLogger.logRepInfo("waiting for page to load for : " + timeout + " seconds");
		driver.manage().timeouts().pageLoadTimeout(timeout, unit);
		TestLogger.logRepInfo("page is loaded");
	}

	public static void waitForElement(WebElement element, int timeOutInSeconds) {
		TestLogger.logRepInfo("waiting for :" + element.toString() + " for :" + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		TestLogger.logRepInfo("element " + element.toString() + "  is visible now");
	}

	public static void waitForPresent(final By by) {
		TestLogger.logRepInfo("wait for " + by.toString() + " to present.");

		final Wait<WebDriver> wait = new WebDriverWait(driver, GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(final WebDriver driver) {
				return driver.findElement(by);
			}
		});
	}
	public void waitForElementToDisappear(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to disappear.");

		final WebDriverWait wait = new WebDriverWait(driver, GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(element.getBy()));
	}

	/**
	 * Wait For seconds. Provide a value less than WebSessionTimeout i.e. 180
	 * Seconds
	 *
	 * @param seconds
	 */
	public static void waitForSeconds(final int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ignore) {
		}
	}
	public static void waitForTextPresent(final HtmlElement element, final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logRepInfo("wait for text \"" + text + "\" to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.textToBePresentInElement(element.getBy(), text));
	}

	public static void waitForTextPresent(final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logRepInfo("wait for text \"" + text + "\" to be present.");

		boolean b = false;

		for (int millisec = 0; millisec < (GlobalConfig.EXPLICIT_WAIT_TIME_OUT * 1000); millisec += 1000) {

			try {

				if ((isTextPresent(text))) {
					b = true;

					break;
				}
			} catch (final Exception ignore) {
			}

			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		assertHTML(b, "Timed out waiting for text \"" + text + "\" to be there.");
	}

	public static  void waitForTextToDisappear(final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logRepInfo("wait for text \"" + text + "\" to disappear.");

		boolean textPresent = true;

		for (int millisec = 0; millisec < (GlobalConfig.EXPLICIT_WAIT_TIME_OUT * 1000); millisec += 1000) {

			try {

				if (!(isTextPresent(text))) {
					textPresent = false;

					break;
				}
			} catch (final Exception ignore) {
			}

			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		assertHTML(!textPresent, "Timed out waiting for text \"" + text + "\" to be gone.");
	}

	public static void waitForTextToDisappear(final String text, final int explicitWaitTimeout) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logRepInfo("wait for text \"" + text + "\" to disappear.");

		boolean textPresent = true;

		for (int millisec = 0; millisec < (explicitWaitTimeout * 1000); millisec += 1000) {

			try {

				if (!(isTextPresent(text))) {
					textPresent = false;

					break;
				}
			} catch (final Exception ignore) {
			}

			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		assertHTML(!textPresent, "Timed out waiting for text \"" + text + "\" to be gone.");
	}
	
	public static void waitForElementChecked(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to be checked.");

		final WebDriverWait wait = new WebDriverWait(driver, GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeSelected(element.getBy()));
	}

	public static void waitForElementEditable(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to be editable.");
		final WebDriverWait wait = new WebDriverWait(driver, GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeClickable(element.getBy()));
		
	}

	public static void waitForElementPresent(final By by) {
		TestLogger.logRepInfo("wait for " + by.toString() + " to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public static void waitForElementPresent(final By by, final int timeout) {
		TestLogger.logRepInfo("wait for " + by.toString() + " to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public static void waitForElementPresent(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(element.getBy()));
	}

	public static void waitForElementToBeVisible(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to be visible.");

		final WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), GlobalConfig.EXPLICIT_WAIT_TIME_OUT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element.getBy()));
	}

	public static void waitForURLToChange(final String match, final int waitSeconds, final boolean partialMatch) {

		for (int i = 0; i < waitSeconds; i++) {
			if (partialMatch) {
				if (DriverFactory.getInstance().getDriver().getCurrentUrl().contains(match)) {
					break;
				} else {
					MasterHelper.waitForSeconds(1);
				}
			} else {
				if (DriverFactory.getInstance().getDriver().getCurrentUrl().equals(match)) {
					break;
				} else {
					MasterHelper.waitForSeconds(1);
				}
			}
		}
	}

	/******************************************************************
	 * JavaScript Helpers
	 *****************************************************************/
	public static Object executeScript(String script) {
		refreshMasterDriver();
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		return exe.executeScript(script);
	}

	public static Object executeScript(String script, Object... args) {
		refreshMasterDriver();
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		return exe.executeScript(script, args);
	}

    public static String getEval(By by, final String script) {
		WebElement element = driver.findElement(by);
		return (String)executeScript(script, element);
    }
	
	public static void scrollToElement(WebElement element) {
		TestLogger.logRepInfo("scroll to WebElement...");
		executeScript("window.scrollTo(arguments[0],arguments[1])", element.getLocation().x, element.getLocation().y);
	}

    public static void simulateMoveToElement(By by, final int x, final int y) {
		WebElement element = driver.findElement(by);
		executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|errorLogger|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
	            "simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]);", element, x, y);
    }

	public static final void refresh() throws NotCurrentPageException {
		TestLogger.logRepInfo("refresh");
		try {
			driver.navigate().refresh();
		} catch (final org.openqa.selenium.TimeoutException ex) {
			TestLogger.logRepInfo("got time out customexception, ignore");
		}
	}
    public static void simulateClick(By by) {
		WebElement element = driver.findElement(by);
        final String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		executeScript(mouseOverScript, element);
        MasterHelper.waitForSeconds(2);

        final String clickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onclick');}";
        executeScript(clickScript, element);
        MasterHelper.waitForSeconds(2);
    }
	
	public static void scrollToElementAndClick(WebElement element) {
		scrollToElement(element);
		element.click();
		TestLogger.logRepInfo("element is clicked: " + element.toString());
	}

	public static void scrollIntoView(WebElement element) {
		TestLogger.logRepInfo("scroll till web element");
		executeScript("arguments[0].scrollIntoView()", element);
	}

	public static void scrollIntoViewAndClick(WebElement element) {
		scrollIntoView(element);
		element.click();
		TestLogger.logRepInfo("element is clicked: " + element.toString());
	}

	public static void scrollDownVertically() {
		TestLogger.logRepInfo("scrolling down vertically...");
		executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public static void scrollUpVertically() {
		TestLogger.logRepInfo("scrolling up vertically...");
		executeScript("window.scrollTo(0,-document.body.scrollHeight)");
	}

	public static void scrollDownByPixel(int pixel) {
		executeScript("window.scrollBY(0," + pixel + ")");
	}

	public static void scrollUpByPixel(int pixel) {
		executeScript("window.scrollBY(0,-" + pixel + ")");
	}

	public static void zoomInBy100Percentage() {
		executeScript("document.body.style.zoom='100%'");
	}

	public static void zoomInBy60Percentage() {
		executeScript("document.body.style.zoom='40%'");
	}

	public static void clickElement(WebElement element) {
		executeScript("arguments[0].click();", element);
	}

	public static void simulateMouseOver(By by) {
		final String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		WebElement element = driver.findElement(by);
		executeScript(mouseOverScript, element);
	}

	public static void simulateMouseOver(WebElement element) {
		final String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		executeScript(mouseOverScript, element);
	}

	/******************************************************************
	 * UI Mouse Helpers
	 *****************************************************************/
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
		MasterHelper.waitForSeconds(1);
		assertCurrentPage(true);
	}

	public static final void selectWindow(final int index) throws NotCurrentPageException {
		TestLogger.logRepInfo("select window, locator={\"" + index + "\"}");
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[index]);
	}

	public static final void selectNewWindow() throws NotCurrentPageException {
		TestLogger.logRepInfo("select new window");
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
		MasterHelper.waitForSeconds(1);
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
                MasterHelper.waitForSeconds(2);
                count = driver.findElements(by).size();
            } else if ((e.getMessage() != null) && e.getMessage().contains("TransformedEntriesMap cannot be cast to java.util.List")) {
            	TestLogger.logRepInfo("Got CastException, retry");
                MasterHelper.waitForSeconds(2);
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
			refreshMasterDriver();
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

	public static void maximizeWindow() {
		try {
			driver.manage().window().maximize();
		} catch (Exception ex) {

			try {
				executeScript(
						"if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);}");
			} catch (Exception ignore) {
			}
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
    
	/******************************************************************
	 * Assertion Helpers
	 *****************************************************************/
    public static void assertAlertHTML(final boolean condition, final String message) {

		if (!condition) {
			Assert.assertTrue(condition, message);
		}
	}
	
	public static void assertAlertPresent() {
		TestLogger.logRepInfo("assert alert present.");

		try {
			driver.switchTo().alert();
		} catch (final Exception ex) {
			assertAlertHTML(false, "assert alert present.");
		}
	}
	
	public static void assertConfirmationText(final String text) {
		TestLogger.logRepInfo("assert confirmation text.");

		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();

		assertAlertHTML(seenText.contains(text), "assert confirmation text.");
	}
	public static void assertPromptText(final String text) {
		TestLogger.logRepInfo("assert prompt text.");

		final Alert alert = driver.switchTo().alert();
		final String seenText = alert.getText();
		assertAlertHTML(seenText.contains(text), "assert prompt text.");
	}

	public static void assertTable(final Table table, final int row, final int col, final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" equals " + table.toHTML() + " at (row, col) = (" + row
				+ ", " + col + ").");

		final String content = table.getContent(row, col);
		assertHTML((content != null) && content.equals(text), "Text= {" + text + "} not found on " + table.toString()
				+ " at cell(row, col) = {" + row + "," + col + "}");
	}

	public static void assertTableContains(final Table table, final int row, final int col, final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" contains " + table.toHTML() + " at (row, col) = (" + row
				+ ", " + col + ").");

		final String content = table.getContent(row, col);
		assertHTML((content != null) && content.contains(text), "Text= {" + text + "} not found on " + table.toString()
				+ " at cell(row, col) = {" + row + "," + col + "}");
	}

	public static void assertTableMatches(final Table table, final int row, final int col, final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" matches " + table.toHTML() + " at (row, col) = (" + row
				+ ", " + col + ").");

		final String content = table.getContent(row, col);
		assertHTML((content != null) && content.matches(text), "Text= {" + text + "} not found on " + table.toString()
				+ " at cell(row, col) = {" + row + "," + col + "}");
	}

	public static void assertTextNotPresent(final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" is not present.");
		assertHTML(!isTextPresent(text), "Text= {" + text + "} found.");
	}

	public static void assertTextNotPresentIgnoreCase(final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" is not present.(ignore case)");
		assertHTML(!getBodyText().toLowerCase().contains(text.toLowerCase()), "Text= {" + text + "} found.");
	}

	public static void assertTextPresent(final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" is present.");
		assertHTML(isTextPresent(text), "Text= {" + text + "} not found.");
	}

	public static void assertTextPresentIgnoreCase(final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" is present.(ignore case)");
		assertHTML(getBodyText().toLowerCase().contains(text.toLowerCase()), "Text= {" + text + "} not found.");
	}
	public static void assertAlertText(final String text) {
		TestLogger.logRepInfo("assert alert text.");

		final Alert alert = driver.switchTo().alert();
		final String alertText = alert.getText();
		assertAlertHTML(alertText.contains(text), "assert alert text.");
	}
	protected static void assertCurrentPage(final boolean log) throws NotCurrentPageException {

	}

	public static void assertElementNotPresent(final HtmlElement element) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " is not present.");
		assertHTML(!element.isElementPresent(), element.toString() + " found.");
	}

	public static void assertElementPresent(final HtmlElement element) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " is present.");
		assertHTML(element.isElementPresent(), element.toString() + " not found.");
	}

	public static void assertElementEnabled(final HtmlElement element) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " is enabled.");
		assertHTML(element.isEnabled(), element.toString() + " not found.");
	}

	public static void assertElementNotEnabled(final HtmlElement element) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " is not enabled.");
		assertHTML(!element.isEnabled(), element.toString() + " not found.");
	}

	public static void assertElementDisplayed(final HtmlElement element) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " is displayed.");
		assertHTML(element.isDisplayed(), element.toString() + " not found.");
	}

	public static void assertElementSelected(final HtmlElement element) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " is selected.");
		assertHTML(element.isSelected(), element.toString() + " not found.");
	}

	public static void assertElementNotSelected(final HtmlElement element) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " is NOT selected.");
		assertHTML(!element.isSelected(), element.toString() + " not found.");
	}

	public static void assertCondition(final boolean condition, final String message) {
		TestLogger.logRepInfo("assert that " + message);
		assert condition;
	}

	public static void assertHTML(final boolean condition, final String message) {

		if (!condition) {
			Assert.assertTrue(condition, message);
		//	CustomAssertion.assertTrue(condition, message);
		}
	}
	public static void assertAttribute(final HtmlElement element, final String attributeName, final String value) {
		TestLogger.logRepInfo(	"assert " + element.toHTML() + " attribute = " + attributeName + ", expectedValue ={" + value + "}.");

		final String attributeValue = element.getAttribute(attributeName);

		assertHTML((value != null) && value.equals(attributeValue), element.toString() + " attribute = " + attributeName
				+ ", expectedValue = {" + value + "}" + ", attributeValue = {" + attributeValue + "}");
	}

	public static void assertAttributeContains(final HtmlElement element, final String attributeName, final String keyword) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " attribute=" + attributeName + ", contains keyword = {"
				+ keyword + "}.");

		final String attributeValue = element.getAttribute(attributeName);

		assertHTML((attributeValue != null) && (keyword != null) && attributeValue.contains(keyword),
				element.toString() + " attribute=" + attributeName + ", expected to contains keyword {" + keyword + "}"
						+ ", attributeValue = {" + attributeValue + "}");
	}

	public static void assertAttributeMatches(final HtmlElement element, final String attributeName, final String regex) {
		TestLogger.logRepInfo("assert " + element.toHTML() + " attribute=" + attributeName + ", matches regex = {" + regex + "}.");

		final String attributeValue = element.getAttribute(attributeName);

		assertHTML((attributeValue != null) && (regex != null) && attributeValue.matches(regex),
				element.toString() + " attribute=" + attributeName + " expected to match regex {" + regex + "}"
						+ ", attributeValue = {" + attributeValue + "}");
	}

}
