/**
 * Button Element Class
 */

package com.framework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.framework.core.TestLogger;
import com.framework.utilities.DriverFactory;

public class ButtonElement extends HtmlElement {

    public ButtonElement(final String label, final By by) {
        super(label, by);
    }

    @Override
    public void click() {
        TestLogger.logRepInfo("click on " + toHTML());
        super.click();
/*        BrowserType browser = WebUIDriver.getBrowserType();
        if (browser == BrowserType.InternetExplore) {
            super.sendKeys(Keys.ENTER);
        } else {
            super.click();
        }*/
    }

    public void submit() {
    	TestLogger.logRepInfo("Submit form by clicking on " + toHTML());
        findElement();
        element.submit();
    }
}
