package com.framework.utilities;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.framework.core.TestLogger;
import com.framework.customexception.NotCurrentPageException;
import com.framework.webelements.HtmlElement;
import com.framework.webelements.Table;

public class AssertionHelper {
	
	private static Logger log = TestLogger.getLogger(AssertionHelper.class);
	private static WebDriver driver;

	public AssertionHelper (WebDriver drv) {
		driver = drv;
		TestLogger.logRepInfo("AssertionHelper has been initialised");
	}
	
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
		assertHTML(!WebUIHelper.isTextPresent(text), "Text= {" + text + "} found.");
	}

	public static void assertTextNotPresentIgnoreCase(final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" is not present.(ignore case)");
		assertHTML(!WebUIHelper.getBodyText().toLowerCase().contains(text.toLowerCase()), "Text= {" + text + "} found.");
	}

	public static void assertTextPresent(final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" is present.");
		assertHTML(WebUIHelper.isTextPresent(text), "Text= {" + text + "} not found.");
	}

	public static void assertTextPresentIgnoreCase(final String text) {
		TestLogger.logRepInfo("assert text \"" + text + "\" is present.(ignore case)");
		assertHTML(WebUIHelper.getBodyText().toLowerCase().contains(text.toLowerCase()), "Text= {" + text + "} not found.");
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
