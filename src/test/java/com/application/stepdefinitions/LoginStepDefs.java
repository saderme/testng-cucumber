package com.application.stepdefinitions;

import static org.testng.Assert.assertTrue;

import com.application.pages.idvAccountVerifyPage;
import com.application.pages.idvLoginPage;
import com.application.runners.BBOSBaseCucumberRunnerLoginTest;
import com.framework.core.GlobalConfig;
import com.framework.utilities.DriverFactory;
import com.framework.utilities.JsonDataReader;
import com.framework.utilities.WaitHelper;
import com.test.testdatatypes.LoginPOJO;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStepDefs extends BBOSBaseCucumberRunnerLoginTest {

	private idvLoginPage loginPage;
	private idvAccountVerifyPage avPage;

	@Given("^I am on the BBOS login screen$")
	public void i_am_on_the_BBOS_login_screen() {
		driver = DriverFactory.getInstance().getDriver();
		driver.get(GlobalConfig.APP_URL);
		driver.manage().window().maximize();
		try {
			loginPage = new idvLoginPage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WaitHelper.waitForElement(loginPage.getWEuserid(), GlobalConfig.DEFAULT_EXPLICIT_WAIT_TIME_OUT);
		assertTrue(driver.getTitle().contains("GCP-ID&V Login"));
	}

	@When("^I login using \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_login_using_and(String username, String password) throws Exception {

		WaitHelper.waitForElementPresent(loginPage.getHTuserid().getBy());
		loginPage.login(username, password);
	}

	@When("^I login using \\\"(.*)\\\" credentials$")
	public void login_using_username_credentials(String userid) {
		LoginPOJO loginpojo = JsonDataReader.getLoginJsonReader().getLoginDataByUserId(userid);
		
		driver.get(GlobalConfig.APP_URL);
		driver.manage().window().maximize();
		assertTrue(driver.getTitle().contains("GCP-ID&V Login"));
		try {
			loginPage = new idvLoginPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		WaitHelper.waitForElementPresent(loginPage.getHTuserid().getBy());
		loginPage.login(loginpojo.getUsername(), loginpojo.getPassword());

		try {
			avPage = new idvAccountVerifyPage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WaitHelper.waitForElementPresent(avPage.getXpassCode().getBy());
		assertTrue(driver.getTitle().contains("GCP-ID&V Login"));
		avPage.submitAccountKeyDetails(loginpojo.getAccountkey());
		assertTrue(driver.getTitle().contains("Account Summary"));
	}

	@When("^I successfully login using \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_successfully_login_using_and(String username, String password, String accountkey) {
		driver.get(GlobalConfig.APP_URL);
		driver.manage().window().maximize();
		assertTrue(driver.getTitle().contains("GCP-ID&V Login"));
		try {
			loginPage = new idvLoginPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		WaitHelper.waitForElementPresent(loginPage.getHTuserid().getBy());
		loginPage.login(username, password);

		try {
			avPage = new idvAccountVerifyPage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WaitHelper.waitForElementPresent(avPage.getXpassCode().getBy());
		assertTrue(driver.getTitle().contains("GCP-ID&V Login"));
		avPage.submitAccountKeyDetails(accountkey);
		assertTrue(driver.getTitle().contains("Account Summary"));
	}

	@And("^I reach the BBOS verify account key screen$")
	public void i_am_on_the_BBOS_verify_account_screen() throws Exception {
		avPage = new idvAccountVerifyPage();
		WaitHelper.waitForElementPresent(avPage.getXpassCode().getBy());
		assertTrue(driver.getTitle().contains("GCP-ID&V Login"));
	}

	@And("^I submit requested \"([^\"]*)\" details$")
	public void i_submit_requested_accountkey_details(String accountkey) throws Exception {
		avPage.submitAccountKeyDetails(accountkey);
	}

	@And("^I reach the BBOS summary screen$")
	public void i_reach_the_BBOS_summary_screen() {
		assertTrue(driver.getTitle().contains("Account Summary"));
	}

	/***
	 * User reached an Error screen
	 */
	@Then("^I see an error on screen$")
	public void i_see_an_error_on_screen() {
		assertTrue(driver.getTitle().contains("GCP-ID&V Login"));
	}
}