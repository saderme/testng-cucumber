package com.application.runners;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.framework.core.GlobalConfig;
import com.framework.core.TestLogger;
import com.framework.utilities.DriverFactory;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		strict = true, monochrome = true, 
		features = "src/test/resources/features", 
		glue = "stepdefinition", plugin = {
		"pretty", "html:target/cucumber-html-report-search" }, 
		tags = { "@Search" }
		)

public class BBOSBaseCucumberRunnerSearchTest extends AbstractTestNGCucumberTests {

	public static Properties config = null;
	public GlobalConfig gc = new GlobalConfig(System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
	//protected WebDriver driver  = DriverFactory.getInstance().getDriver();
	protected WebDriver driver;
	private static final Logger applog = TestLogger.getLogger(BBOSBaseCucumberRunnerLoginTest.class);
	private Date start;

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		long threadId = Thread.currentThread().getId();
		start = new Date();
		TestLogger.logAppLogInfo(applog, "@BeforeSuite - BaseCucumberSearchTest - Thread: " + threadId + " Test Suite Started: " + start.getTime());
	}

	public void explicitWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String cal1 = dateFormat.format(cal.getTime());
		return cal1;
	}

	@AfterClass(alwaysRun = true)
	public void takeScreenshot() throws IOException {
		long threadId = Thread.currentThread().getId();
		TestLogger.logAppLogInfo(applog, "@AfterClass - BaseCucumberSearchTest Threadid" + threadId);
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"headless" , "softassert"})
	public void before(String headless, String softassert) throws Exception {
		long threadId = Thread.currentThread().getId();
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		TestLogger.logAppLogInfo(applog, "@BeforeMethod BaseCucumberSearchTest - Thread: " + threadId);
		System.out.println("Started in thread: " + threadId + ", in JVM: " + processName);
		GlobalConfig.setHeadless(headless);
		GlobalConfig.setSoftAssert(softassert);
		
		driver = DriverFactory.getInstance().getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(GlobalConfig.DEFAULT_IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Long.valueOf(GlobalConfig.PAGE_LOAD_TIMEOUT).longValue(),TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.get(GlobalConfig.APP_URL);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownr(final Method method, ITestResult result) throws IOException {
		long threadId = Thread.currentThread().getId();
		System.out.println("@AfterMethod - BaseCucumberSearchTest " + method.getName());
		if (result.isSuccess()) {
			TestLogger.logAppLogInfo(applog, "@AfterMethod - Thread: " + threadId + " SUCCESS");
		} else {
			TestLogger.logAppLogInfo(applog, "@AfterMethod BaseCucumberSearchTest - Thread: " + threadId + "Method: " + method.getName() + " FAIL");
		}
		DriverFactory.getInstance().removeDriver();
	}

	@AfterSuite(alwaysRun = true)
	public void quit() throws IOException, InterruptedException {
		long threadId = Thread.currentThread().getId();
		TestLogger.logAppLogInfo(applog, "@AfterSuite -BaseCucumberSearchTest - Thread: " + threadId + "Test Suite Ended: " + start.getTime());
	}
}
