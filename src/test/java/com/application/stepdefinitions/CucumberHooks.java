package com.application.stepdefinitions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.cucumber.listener.Reporter;
import com.framework.core.GlobalConfig;
import com.framework.core.TestLogger;
import com.framework.utilities.DriverFactory;
import com.google.common.io.Files;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class CucumberHooks {
	private static final Logger applog = TestLogger.getLogger(CucumberHooks.class);
	private static final String IMAGE_PNG = "image/png";
	private static final String SCREENSHOTS_PATH = "target/cucumber-screenshots/";
	private static final String SCREENSHOTS_FORMAT = SCREENSHOTS_PATH + "%s.png";
	
	private long threadId;
	@Before
	public void setUp(Scenario scenario) {
		threadId = Thread.currentThread().getId();
		TestLogger.logAppLogInfo(applog, "@Before Scenario " + scenario.getName() + " in thread: " + threadId + " Started");
		Reporter.setTestRunnerOutput("Scenario Started: " + scenario.getName() + "<br>");
	}

	@After
	public void takeScreenshot(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			byte[] screenshotAsBytes = ((TakesScreenshot) DriverFactory.getInstance().getDriver())
					.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshotAsBytes, IMAGE_PNG);
			//Reporter.addScenarioLog("Scenario Log message goes here");
			Reporter.setTestRunnerOutput("Scenario Failed: " + scenario.getName()+ "<br>");

			File destination = new File(System.getProperty("user.dir") + "\\target\\html_report\\screenshots\\" +"sample.png");
			File scrFile = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, destination.getAbsoluteFile());
			Reporter.addScreenCaptureFromPath(destination.getAbsoluteFile().toString());

		}
		else
		{
			Reporter.setTestRunnerOutput("Scenario Passed: " + scenario.getName()+ "<br>");
		}
		
	}

	
	@After
	public void tearDown(Scenario scenario) {
		TestLogger.logAppLogInfo(applog, "@After Scenario " + scenario.getName() + " in thread: " +threadId + " with status "+ scenario.getStatus());
		if (scenario.isFailed()) {
		}
		
	}

}
