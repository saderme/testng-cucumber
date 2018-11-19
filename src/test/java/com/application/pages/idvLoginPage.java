package com.application.pages;

import static com.framework.core.Locator.locateById;
import static com.framework.core.Locator.locateByXPath;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.framework.core.GlobalConfig;
import com.framework.utilities.DriverFactory;
import com.framework.webelements.ButtonElement;
import com.framework.webelements.HtmlElement;
import com.framework.webelements.LabelElement;
import com.framework.webelements.LinkElement;
import com.framework.webelements.TextFieldElement;
import com.framework.webelements.webBasePage;
import com.framework.utilities.MasterHelper;
public class idvLoginPage extends webBasePage {
	
	private WebDriver driver;
	private idvAccountVerifyPage av = new idvAccountVerifyPage();
	
	/**
	 * WebElements 
	 */
    private TextFieldElement userid = new TextFieldElement("email address or userid field",locateById("userid"));
    private TextFieldElement password = new TextFieldElement("password field",locateById("password"));
    //private static final ButtonElement nextBtn = new ButtonElement("Next Button", locateByXPath("//input[@id = 'nextBtn']"));
    private ButtonElement nextBtn = new ButtonElement("Next Button", locateById("nextBtn"));
    private LinkElement register = new LinkElement("Register link",locateById("goToRegistration"));
    private LinkElement forgotUserId = new LinkElement("Forgotten User link",locateById("forgotUserId"));
    private LinkElement forgotPwdBtn = new LinkElement("Forgotten Password link",locateById("forgotPwdBtn"));
    private TextFieldElement errorBody = new TextFieldElement("error text", locateByXPath("//div[@class='error-body']"));

	
	public idvLoginPage() throws Exception {
		this.driver = DriverFactory.getInstance().getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public void login(String userName, String pwd) {
		userid.sendKeys(userName);
		password.sendKeys(pwd);
		//Using JS to Click and not Selenium as the popup is preventing click
		MasterHelper.clickElement(nextBtn.getElement());
	}

	public  HtmlElement getHTuserid() {
		return userid;
	}
	
	public  WebElement getWEuserid() {
		return userid.getElement();
	}
	
	public  boolean isErrorVisible() {
		return errorBody.isElementPresent();
	}
	
}











