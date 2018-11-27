package com.application.pages;

import static com.framework.core.Locator.locateById;
import static com.framework.core.Locator.locateByXPath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.framework.utilities.DriverFactory;
import com.framework.utilities.JavaScriptHelper;
import com.framework.utilities.WaitHelper;
import com.framework.webelements.ButtonElement;
import com.framework.webelements.HtmlElement;
import com.framework.webelements.LabelElement;
import com.framework.webelements.TextFieldElement;
import com.framework.webelements.webBasePage;
import com.test.testdatatypes.LoginPOJO;

public class idvAccountVerifyPage extends webBasePage {
	
    private static final TextFieldElement xpasscode = new TextFieldElement("xpasscode",locateById("xpasscode"));
    private static final TextFieldElement ypasscode = new TextFieldElement("ypasscode",locateById("ypasscode"));
    private static final ButtonElement loginButton = new ButtonElement("Login Button",locateById("passcodeSubmit"));
    private static final LabelElement xpassLabel = new LabelElement("xPass Label",locateByXPath("//label[@class='long' and @for='xpasscode']"));
    private static final LabelElement ypassLabel = new LabelElement("yPass Label",locateByXPath("//label[@class='long' and @for='ypasscode']"));

	
	private WebDriver driver;
	private int xpassscreenchar;
	private int ypassscreenchar;
	private String xpassno;
	private String ypassno;	
	private String accountKey;

	public idvAccountVerifyPage() throws Exception { 
		this.driver = DriverFactory.getInstance().getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public  HtmlElement getXpassCode() {
		return xpasscode;
	}
	

	public void populategetXPassChar()
	{
		//Get the Character from the screen
		System.out.println("Screen Textx: " + xpassLabel.getText());
		Matcher m = Pattern.compile("[^0-9]*([0-9]+).*").matcher(xpassLabel.getText());
		if (m.matches()) {
		    System.out.println(m.group(1));
		}	
		xpassscreenchar =  Integer.valueOf(m.group(1));
		System.out.println("Screen Textx char: " + xpassscreenchar);
		//Find the specified character from the Account key and input on screen
		xpassno = Character.toString(accountKey.charAt(xpassscreenchar-1));
		System.out.println("Screen Textx no: " + xpassno);
		xpasscode.sendKeys(xpassno);
		
	}

	public void populategetYPassChar()
	{
		//Get the Character from the screen
		System.out.println("Screen Texty: " + ypassLabel.getText());
		Matcher m = Pattern.compile("[^0-9]*([0-9]+).*").matcher(ypassLabel.getText());
		if (m.matches()) {
		    System.out.println(m.group(1));
		}	
		ypassscreenchar =  Integer.valueOf(m.group(1));
		System.out.println("Screen Texty char: " + ypassscreenchar);
		//Find the specified character from the Account key and input on screen
		ypassno = Character.toString(accountKey.charAt(ypassscreenchar-1));
		System.out.println("Screen Texty no: " + ypassno);
		ypasscode.sendKeys(ypassno);
	}	

	public void submitAccountKeyDetails(String accountkey) {
		
		this.accountKey=accountkey;		
		WaitHelper.waitForElementToBeVisible(xpasscode);
		populategetXPassChar();
		populategetYPassChar();
		JavaScriptHelper.clickElement(loginButton.getElement());
	}
	
	public void submitAccountKeyDetails(LoginPOJO login) {
		this.accountKey=login.getAccountkey();		
		WaitHelper.waitForElementToBeVisible(xpasscode);
		populategetXPassChar();
		populategetYPassChar();
		JavaScriptHelper.clickElement(loginButton.getElement());
	}
	
}











