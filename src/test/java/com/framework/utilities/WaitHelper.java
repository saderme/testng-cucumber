package com.framework.utilities;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.framework.core.GlobalConfig;
import com.framework.core.TestLogger;
import com.framework.webelements.HtmlElement;

public class WaitHelper {
	
	private static Logger log = TestLogger.getLogger(WaitHelper.class);
	private static WebDriver driver;

	public WaitHelper (WebDriver drv) {
		driver = drv;
		TestLogger.logRepInfo("WaitHelper has been initialised");
	}
	
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

	public static void waitForElement(WebElement element, long timeOutInSeconds) {
		TestLogger.logRepInfo("waiting for :" + element.toString() + " for :" + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		TestLogger.logRepInfo("element " + element.toString() + "  is visible now");
	}

	public static void waitForPresent(final By by) {
		TestLogger.logRepInfo("wait for " + by.toString() + " to present.");

		final Wait<WebDriver> wait = new WebDriverWait(driver,Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
		wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(final WebDriver driver) {
				return driver.findElement(by);
			}
		});
	}
	public void waitForElementToDisappear(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to disappear.");

		final WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
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

		final WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
		wait.until(ExpectedConditions.textToBePresentInElement(element.getBy(), text));
	}

	public static void waitForTextPresent(final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logRepInfo("wait for text \"" + text + "\" to be present.");

		boolean b = false;

		for (int millisec = 0; millisec < (Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue() * 1000); millisec += 1000) {

			try {

				if ((WebUIHelper.isTextPresent(text))) {
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

		AssertionHelper.assertHTML(b, "Timed out waiting for text \"" + text + "\" to be there.");
	}

	public static  void waitForTextToDisappear(final String text) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logRepInfo("wait for text \"" + text + "\" to disappear.");

		boolean textPresent = true;

		for (int millisec = 0; millisec < (Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue() * 1000); millisec += 1000) {

			try {

				if (!(WebUIHelper.isTextPresent(text))) {
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

		AssertionHelper.assertHTML(!textPresent, "Timed out waiting for text \"" + text + "\" to be gone.");
	}

	public static void waitForTextToDisappear(final String text, final int explicitWaitTimeout) {
		Assert.assertNotNull(text, "Text can't be null");
		TestLogger.logRepInfo("wait for text \"" + text + "\" to disappear.");

		boolean textPresent = true;

		for (int millisec = 0; millisec < (explicitWaitTimeout * 1000); millisec += 1000) {

			try {

				if (!(WebUIHelper.isTextPresent(text))) {
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

		AssertionHelper.assertHTML(!textPresent, "Timed out waiting for text \"" + text + "\" to be gone.");
	}
	
	public static void waitForElementChecked(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to be checked.");

		final WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
		wait.until(ExpectedConditions.elementToBeSelected(element.getBy()));
	}

	public static void waitForElementEditable(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to be editable.");
		final WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
		wait.until(ExpectedConditions.elementToBeClickable(element.getBy()));
		
	}

	public static void waitForElementPresent(final By by) {
		TestLogger.logRepInfo("wait for " + by.toString() + " to be present.");

		final WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
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

		final WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
		wait.until(ExpectedConditions.presenceOfElementLocated(element.getBy()));
	}

	public static void waitForElementToBeVisible(final HtmlElement element) {
		Assert.assertNotNull(element, "Element can't be null");
		TestLogger.logRepInfo("wait for " + element.toString() + " to be visible.");

		final WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Long.valueOf(GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT).longValue());
		wait.until(ExpectedConditions.visibilityOfElementLocated(element.getBy()));
	}

	public static void waitForURLToChange(final String match, final int waitSeconds, final boolean partialMatch) {

		for (int i = 0; i < waitSeconds; i++) {
			if (partialMatch) {
				if (DriverFactory.getInstance().getDriver().getCurrentUrl().contains(match)) {
					break;
				} else {
					waitForSeconds(1);
				}
			} else {
				if (DriverFactory.getInstance().getDriver().getCurrentUrl().equals(match)) {
					break;
				} else {
					waitForSeconds(1);
				}
			}
		}
	}


}
