package com.framework.webelements;

import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.framework.core.GlobalConfig;
//import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.framework.core.TestLogger;
import com.framework.customexception.CustomSeleniumTestsException;
import com.framework.customexception.NotCurrentPageException;
import com.framework.utilities.DriverFactory;
import com.framework.utilities.MasterHelper;

/**
 * Base html page abstraction. Used by webPageObject and WebPageSection
 */
public abstract class webBasePage {

	//	protected 	GlobalConfig gc = new GlobalConfig("configuration.properties");
	protected WebDriver driver = DriverFactory.getInstance().getDriver();
	private static final Logger applog = TestLogger.getLogger(webBasePage.class);
	private boolean frameFlag = false;
	private final String popupWindowName = null;
	private String windowHandle = null;
	private String title = null;
	private String url = null;
	private String bodyText = null;
	private String htmlSource = null;
	private String htmlSavedToPath = null;
	private String suiteName = null;
	private String outputDirectory = null;
	private String htmlFilePath = null;
	private String imageFilePath = null;
	
	public WebDriver getDriver() {
		driver = DriverFactory.getInstance().getDriver();
		return driver;
	}
	
	public String getHtmlSavedToPath() {
		return htmlSavedToPath;
	}

	public String getHtmlSource() {
		return htmlSource;
	}
	
	public By getByLocater(HtmlElement elem) {
		return elem.getBy();
	}	
	
	private String getBodyText() {
		if (bodyText == null) {
			bodyText = MasterHelper.getBodyText();
		}
		return bodyText;
	}

	public String getPopupWindowName() {
		return popupWindowName;
	}

	public String getUrl() {
		return url;
	}

	public String getCanonicalURL() {
		return new LinkElement("Canonical URL", By.cssSelector("link[rel=canonical]")).getAttribute("href");
	}

	public String getWindowHandle() {
		return windowHandle;
	}

	public boolean isFrame() {
		return frameFlag;
	}	
	
	public final void selectFrame(final int index) {
		MasterHelper.selectFrame(index);
		frameFlag = true;
	}

	public final void selectFrame(final By by) {
		MasterHelper.selectFrame(by);
		frameFlag = true;
	}

	public final void selectFrame(final String locator) {
		MasterHelper.selectFrame(locator);
		frameFlag = true;
	}	
	public final void goBack() {
		MasterHelper.goBack();
		frameFlag = false;
	}

	public final void goForward() {
		MasterHelper.goForward();
		frameFlag = false;
	}
	
	protected void setHtmlSavedToPath(final String htmlSavedToPath) {
		this.htmlSavedToPath = htmlSavedToPath;
	}

	protected void setTitle(final String title) {
		this.title = title;
	}

	protected void setUrl(final String openUrl) {
		this.url = openUrl;
	}
	
	private void open(final String url) throws Exception {

		if (this.getDriver() == null) {
			TestLogger.logRepInfo("Launch application");
			driver = DriverFactory.getInstance().getDriver("chrome");
		}

		setUrl(url);

		try {
				driver.navigate().to(url);
		} catch (final UnreachableBrowserException e) {

			// handle if the last window is closed
			TestLogger.logRepInfo("Launch application");
			driver = DriverFactory.getInstance().getDriver("chrome");
			MasterHelper.maximizeWindow();
			driver.navigate().to(url);
		} catch (final UnsupportedCommandException e) {
			TestLogger.logRepInfo("get UnsupportedCommandException, retry");
			driver = DriverFactory.getInstance().getDriver("chrome");
			MasterHelper.maximizeWindow();
			driver.navigate().to(url);
		} catch (final org.openqa.selenium.TimeoutException ex) {
			TestLogger.logRepInfo("got time out when loading " + url + ", ignored");
		} catch (final org.openqa.selenium.UnhandledAlertException ex) {
			TestLogger.logRepInfo("got UnhandledAlertException, retry");
			driver.navigate().to(url);
		} catch (final Throwable e) {
			e.printStackTrace();
			throw new CustomSeleniumTestsException(e);
		}
	}

	private void populateAndCapturePageSnapshot() {

		try {
			setTitle(driver.getTitle());
			htmlSource = driver.getPageSource();

			try {bodyText =  MasterHelper.getBodyText();
			} catch (final StaleElementReferenceException ignore) {
				TestLogger.logAppLogWarning(applog, "StaleElementReferenceException got in populateAndCapturePageSnapshot");
				bodyText =  MasterHelper.getBodyText();
			}
		} catch (final UnreachableBrowserException e) { // throw

			// UnreachableBrowserException
			throw new WebDriverException(e);
		} catch (final WebDriverException e) {
			throw e;
		}

		//capturePageSnapshot();
	}

	private void waitForPageToLoad() throws Exception {
		try {
			populateAndCapturePageSnapshot();
		} catch (final Exception ex) {

			// ex.printStackTrace();
			throw ex;
		}
	}
    
}
