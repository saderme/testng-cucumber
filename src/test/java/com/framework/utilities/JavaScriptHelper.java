package com.framework.utilities;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.framework.core.TestLogger;
import com.framework.customexception.NotCurrentPageException;

public class JavaScriptHelper {
	
	private static Logger log = TestLogger.getLogger(JavaScriptHelper.class);
	private static WebDriver driver;

	public JavaScriptHelper (WebDriver drv) {
		driver = drv;
		TestLogger.logRepInfo("JavaScriptHelper has been initialised");
	}
	
	public static Object executeScript(String script) {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		return exe.executeScript(script);
	}

	public static Object executeScript(String script, Object... args) {
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
        WaitHelper.waitForSeconds(2);

        final String clickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onclick');}";
        executeScript(clickScript, element);
        WaitHelper.waitForSeconds(2);
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

    public static void maximizeWindow() {
        try {
            driver.manage().window().maximize();
        } catch (Exception ex) {

            try {
            	executeScript("if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);}");
             } catch (Exception ignore) {
                com.framework.core.TestLogger.logRepInfo("Unable to maximize browser window. Exception occured: " + ignore.getMessage());
            }
        }
    }
	
}
