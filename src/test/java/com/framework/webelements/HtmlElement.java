/*
 * Copyright 2015 www.seleniumtests.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.framework.webelements;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.framework.core.TestLogger;
import com.framework.utilities.DriverFactory;
import com.framework.utilities.MasterHelper;

/**
 * Provides methods to interact with a web page. All HTML element (ButtonElement, LinkElement, TextFieldElement, etc.)
 * extends from this class.
 */
public class HtmlElement {

    //private static final int EXPLICIT_WAIT_TIME_OUT = WebUIDriver.getWebUIDriver().getExplicitWait();
    protected static final Logger logger = TestLogger.getLogger(HtmlElement.class);

    private static enum LocatorType {
        ID, NAME, CLASS_NAME, LINK_TEXT, PARTIAL_LINK_TEXT, CSS_SELECTOR, TAG_NAME, XPATH,
    }

    protected WebDriver driver = DriverFactory.getInstance().getDriver();
    protected WebElement element = null;
    private String label = null;
    private String locator = null;
    private By by = null;

    /**
     * Find element using BY locator. Make sure to initialize the driver before calling findElement()
     *
     * @param   label  - element name for logging
     * @param   by     - By type
     *
     * @sample  {@code new HtmlElement("UserId", By.id(userid))}
     */
    public HtmlElement(final String label, final By by) {
        this.label = label;
        this.by = by;
    }

    /**
     * This constructor locates the element using locator and locator type.
     *
     * @param  label
     * @param  locator  - locator
     */
    public HtmlElement(final String label, final String locator,
        final LocatorType locatorType) {
        this.label = label;
        this.locator = locator;
        this.by = getLocatorBy(locator, locatorType);
    }

    /**
     * Captures snapshot of the current browser window.
     */
/*    public void captureSnapshot() {
        captureSnapshot(ContextHelper.getCallerMethod() + " on ");
    }*/

    /**
     * Captures snapshot of the current browser window, and prefix the file name with the assigned string.
     */
/*    protected void captureSnapshot(final String messagePrefix) {
        ScreenshotUtil.captureSnapshot(messagePrefix);
    }
*/
    public void click() {
        findElement();
        element.click();
    }

    /**
     * Click element in native way by Actions.
     */
    public void clickAt() {
        clickAt("1,1");

    }

    /**
     * Click element in native way by Actions.
     *
     * <p/>
     * <pre class="code">
       clickAt(&quot;1, 1&quot;);
     * </pre>
     */
    public void clickAt(final String value) {
    	MasterHelper.clickAt(this.by, value);
    }

    public void clickSpecificValuefromDropDown(WebElement dropdown, final By findby, String selectValue) {
    	MasterHelper.clickSpecificValuefromDropDown(dropdown, findby, selectValue);
    }
    
    public void clickSpecificDatefromCalendar(WebElement calendar, final By findby, String selectValue) {
    	MasterHelper.clickSpecificDatefromCalendar(calendar, findby, selectValue);
    }  
    
    public void simulateClick() {
    	MasterHelper.simulateClick(this.by);
    }

    public void simulateMoveToElement(final int x, final int y) {
    	MasterHelper.simulateMoveToElement(this.by, x, y);
    }

    /**
     * Finds the element using By type. Implicit Waits is built in createWebDriver() in WebUIDriver to handle dynamic
     * element problem. This method is invoked before all the basic operations like click, sendKeys, getText, etc. Use
     * waitForPresent to use Explicit Waits to deal with special element which needs long time to present.
     */
    protected void findElement() {
        element = MasterHelper.getElement(this.by);
    }

    /**
     * Get all elements in the current page with same locator.
     */
    public List<WebElement> getAllElements() {
    	return MasterHelper.getAllElements(this.by);
    }

    /**
     * Gets an attribute (using standard key-value pair) from the underlying attribute.
     */
    public String getAttribute(final String name) {
    	return MasterHelper.getAttribute(this.by,name );
    }

    /**
     * Returns the BY locator stored in the HtmlElement.
     */
    public By getBy() {
        return by;
    }

    /**
     * Returns the value for the specified CSS key.
     *
     * @param   propertyName
     */
    public String getCssValue(final String propertyName) {
    	return MasterHelper.getCssValue(this.by,propertyName );
    }

    /**
     * Get and refresh underlying WebDriver.
     */
    protected WebDriver getDriver() {
        return DriverFactory.getInstance().getDriver();
    }

    /**
     * Returns the underlying WebDriver WebElement.
     */
    public WebElement getElement() {
    	return MasterHelper.getElement(this.by);
    }

    /**
     * Executes the given JavaScript against the underlying WebElement.
     */
    public String getEval(final String script) {
    	return MasterHelper.getEval(this.by, script);
    }

    /**
     * Returns the 'height' property of the underlying WebElement's Dimension.
     */
    public int getHeight() {
    	return MasterHelper.getHeight(this.by);
    }

    /**
     * Returns the label used during initialization.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the Point location of the underlying WebElement.
     */
    public Point getLocation() {
    	return MasterHelper.getLocation(this.by);
    }

    /**
     * Returns the locator used to find the underlying WebElement.
     */
    public String getLocator() {
        return locator;
    }

    private By getLocatorBy(final String locator,
        final LocatorType locatorType) {

        switch (locatorType) {

        case ID:
            return By.id(locator);

        case NAME:
            return By.name(locator);

        case CLASS_NAME:
            return By.className(locator);

        case LINK_TEXT:
            return By.linkText(locator);

        case PARTIAL_LINK_TEXT:
            return By.partialLinkText(locator);

        case CSS_SELECTOR:
            return By.cssSelector(locator);

        case TAG_NAME:
            return By.tagName(locator);

        default:
            return By.xpath(locator);
        }
    }

    /**
     * Returns the Dimension property of the underlying WebElement.
     */
    public Dimension getSize() {
    	return MasterHelper.getSize(this.by);
    }

    /**
     * Returns the HTML Tag for the underlying WebElement (div, a, input, etc).
     */
    public String getTagName() {
    	return MasterHelper.getTagName(this.by);
    }

    /**
     * Returns the text body of the underlying WebElement.
     */
    public String getText() {
    	return MasterHelper.getText(this.by);
    }

    /**
     * Returns the 'value' attribute of the underlying WebElement.
     */
    public String getValue() {
    	return MasterHelper.getValue(this.by);
    }

    /**
     * Returns the 'width' property of the underlying WebElement's Dimension.
     */
    public int getWidth() {
    	return MasterHelper.getWidth(this.by);
    }

    /**
     * Refreshes the WebUIDriver before locating the element, to ensure we have the current version (useful for when the
     * state of an element has changed via an AJAX or non-page-turn action).
     */
    public void init() {
    	WebDriver driver = DriverFactory.getInstance().getDriver();
        element = driver.findElement(by);
    }

    /**
     * Indicates whether or not the web element is currently displayed in the browser.
     */
    public boolean isDisplayed() {
    	return MasterHelper.isDisplayed(this.by);
    }

    /**
     * Searches for the element using the BY locator, and indicates whether or not it exists in the page. This can be
     * used to look for hidden objects, whereas isDisplayed() only looks for things that are visible to the user
     */
    public boolean isElementPresent() {
    	return MasterHelper.isElementPresent(this.by);
     }

    /**
     * Indicates whether or not the element is enabled in the browser.
     */
    public boolean isEnabled() {
    	return MasterHelper.isEnabled(this.by);
    }
    /**
     * Indicates whether or not the element is selected in the browser.
     */
    public boolean isSelected() {
    	return MasterHelper.isSelected(this.by);
    }

    /**
     * Whether or not the indicated text is contained in the element's getText() attribute.
     */
    public boolean isTextPresent(final String text) {
    	return MasterHelper.isTextPresent(this.by, text);
    }

    /**
     * Forces a mouseDown event on the WebElement.
     */
    public void mouseDown() { 
    	MasterHelper.mouseDown(this.by);
    }

    /**
     * Forces a mouseOver event on the WebElement.
     */
    public void mouseOver() {
    	MasterHelper.mouseOver(this.by);
    }

    /**
     * Forces a mouseOver event on the WebElement using simulate by JavaScript way for some dynamic menu.
     */
    public void simulateMouseOver() {
    	MasterHelper.simulateMouseOver(this.by);
    }

    /**
     * Forces a mouseUp event on the WebElement.
     */
    public void mouseUp() {
    	MasterHelper.mouseUp(this.by);
    }

    /**
     * Sends the indicated CharSequence to the WebElement.
     */
    public void sendKeys(final CharSequence... arg) {
    	MasterHelper.sendKeys(this.by, arg);
    }

    /**
     * Converts the Type, Locator and LabelElement attributes of the HtmlElement into a readable and report-friendly
     * string.
     */
    public String toHTML() {
        return getClass().getSimpleName().toLowerCase() +
            " <a style=\"font-style:normal;color:#8C8984;text-decoration:none;\" href=# \">" + getLabel() + ",: " + getBy().toString() + "</a>";
    }

    /**
     * Returns a friendly string, representing the HtmlElement's Type, LabelElement and Locator.
     */
    public String toString() {
        return getClass().getSimpleName().toLowerCase() + " " + getLabel() + ", by={" + getBy().toString() + "}";
    }

    /**
     * Wait element to present using Explicit Waits with default EXPLICIT_WAIT_TIME_OUT = 15 seconds.
     */
    public void waitForPresent() {
    	MasterHelper.waitForPresent(this.by);
    }
}