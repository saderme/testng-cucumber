package com.application.runners;

import java.io.File;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cucumber.listener.Reporter;
import com.framework.core.GlobalConfig;
import com.framework.core.TestLogger;
import com.framework.utilities.DriverFactory;
import com.framework.utilities.JavaScriptHelper;
import com.framework.utilities.WaitHelper;
import com.framework.utilities.WebUIHelper;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		strict = true, monochrome = true, 
		features = "src/test/resources/features", 
		glue = "com.application.stepdefinitions", 
		plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-extent/report.html"},
		tags = { "@Loginpojo2" }
		)

public class CucumberExtentRunnerLoginTest extends AbstractTestNGCucumberTests {

	public static Properties config = null;
	public GlobalConfig gc = new GlobalConfig(System.getProperty("user.dir") + "//src//test//resources//config//qaconfig.properties");

	public WebDriver driver;
	public WaitHelper wHelper;
	public JavaScriptHelper jsHelper;
	public WebUIHelper uiHelper;
	
	private static final Logger applog = TestLogger.getLogger(CucumberExtentRunnerLoginTest.class);
	private Date start;
	private Date end;
	
	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		//This property is passed by maven in the pom.xml
		//System.out.println(System.getProperty("browserbymvn"));
		long threadId = Thread.currentThread().getId();
		start = new Date();
		TestLogger.logAppLogInfo(applog, "@BeforeSuite  BBOSBaseCucumberRunnerPOJOLoginTest - Thread: " + threadId + " Test Suite Started: " + start.getTime());
	}
	
	@BeforeClass
/*	public static void setup() {
	    ExtentProperties extentProperties = ExtentProperties.INSTANCE;
	    extentProperties.setReportPath("target/cucumber-extent/report.html");
	}*/
	
	@AfterClass(alwaysRun = true)
	public void takeScreenshot() throws IOException {
		long threadId = Thread.currentThread().getId();
		TestLogger.logAppLogInfo(applog, "@AfterClass   BBOSBaseCucumberRunnerPOJOLoginTest - Thread: " + threadId);
/*		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		//File trgtFile = new File(System.getProperty("user.dir") + "//screenshots/screenshot.png");
		File trgtFile = new File(System.getProperty("user.dir") + GlobalConfig.SCREENSHOT_PATH);
		trgtFile.getParentFile().mkdir();
		trgtFile.createNewFile();
		Files.copy(scrFile, trgtFile);*/
	}
	
	 @AfterClass
	 public static void writeExtentReport() {
		 Reporter.loadXMLConfig(new File(GlobalConfig.EXTENT_CONFIG_PATH));
	 }

	@BeforeMethod(alwaysRun = true)
	@Parameters({"headless" , "softassert"})
	public void before(String headless, String softassert) throws Exception {
		long threadId = Thread.currentThread().getId();
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		TestLogger.logAppLogInfo(applog, "@BeforeMethod  BBOSBaseCucumberRunnerPOJOLoginTest - Thread: " + threadId + " Process: " + processName);

		GlobalConfig.setHeadless(headless);
		GlobalConfig.setSoftAssert(softassert);
	
		driver = DriverFactory.getInstance().getDriver();
		wHelper = new WaitHelper(driver);
		jsHelper = new JavaScriptHelper(driver);
		uiHelper = new WebUIHelper(driver);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(GlobalConfig.DEFAULT_IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Long.valueOf(GlobalConfig.PAGE_LOAD_TIMEOUT).longValue(),TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		//driver.get(GlobalConfig.APP_URL);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownr(final Method method, ITestResult result) throws IOException {
		long threadId = Thread.currentThread().getId();
		System.out.println("@AfterMethod   BBOSBaseCucumberRunnerPOJOLoginTest " + method.getName());
		if (result.isSuccess()) {
			TestLogger.logAppLogInfo(applog, "@AfterMethod - Thread: " + threadId + " SUCCESS");
/*			File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String failureImageFileName = result.getMethod().getMethodName()
					+ new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
			//File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);
			File failureImageFile = new File(System.getProperty("user.dir") + GlobalConfig.SCREENSHOT_PATH + failureImageFileName);
			failureImageFile.getParentFile().mkdir();
			failureImageFile.createNewFile();
			Files.copy(imageFile, failureImageFile);*/
		} else {
			TestLogger.logAppLogInfo(applog, "@AfterMethod  BBOSBaseCucumberRunnerPOJOLoginTest - Thread: " + threadId + "Method: " + method.getName() + " FAIL");
		}
		DriverFactory.getInstance().removeDriver();
	}

	@AfterSuite(alwaysRun = true)
	public void quit() throws IOException, InterruptedException {
		long threadId = Thread.currentThread().getId();
		end = new Date();
		TestLogger.logAppLogInfo(applog, "@AfterSuite   BBOSBaseCucumberRunnerPOJOLoginTest - Thread: " + threadId + "Test Suite Ended: " + end.getTime());
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
}
