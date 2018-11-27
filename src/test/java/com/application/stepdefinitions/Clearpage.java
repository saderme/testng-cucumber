package com.application.stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.application.runners.BBOSBaseCucumberRunnerLoginTest;
import com.application.runners.BBOSBaseCucumberRunnerSearchTest;
import com.application.runners.BaseCucumberTestNGRunner;
import com.framework.utilities.DriverFactory;

import cucumber.api.java.en.Then;

public class Clearpage extends BBOSBaseCucumberRunnerSearchTest {
	

	@Then("^I clear search textbox$")
	public void Clear() throws Throwable {
		driver = DriverFactory.getInstance().getDriver();
		WebElement clearSearchBox = driver.findElement(By.cssSelector("input[name='q']"));
		explicitWait(clearSearchBox);
		clearSearchBox.clear();

	}

}
