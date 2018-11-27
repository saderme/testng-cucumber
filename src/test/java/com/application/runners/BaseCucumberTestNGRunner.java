package com.application.runners;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

import com.framework.core.GlobalConfig;
import com.framework.core.TestLogger;
import com.framework.utilities.DriverFactory;
import com.google.common.io.Files;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		strict = true, monochrome = true, 
		features = "src/test/resources/features", 
		glue = "stepdefinition", 
		plugin = {"pretty", "html:target/cucumber-html-report" }, 
		tags = { "@Login" }
)


public class BaseCucumberTestNGRunner extends AbstractTestNGCucumberTests {

	public static Properties config = null;
	public static WebDriver driver = null;
	public GlobalConfig gc;

	private static final Logger applog = TestLogger.getLogger(BaseCucumberTestNGRunner.class);
	
	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("@BeforeSuite - BaseCucumberTestNGRunner");
		gc = new GlobalConfig(System.getProperty("user.dir") + "//src//test//resources//config//bbosconfig.properties");

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
		System.out.println("@AfterClass - BaseCucumberTestNGRunner");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File trgtFile = new File(System.getProperty("user.dir") + "//screenshots/screenshot.png");
		trgtFile.getParentFile().mkdir();
		trgtFile.createNewFile();
		Files.copy(scrFile, trgtFile);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void before() {
		TestLogger.logAppLogInfo(applog, "@BeforeMethod ");
		long threadId = Thread.currentThread().getId();
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("Started in thread: " + threadId + ", in JVM: " + processName);
		
		driver = DriverFactory.getInstance().getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(GlobalConfig.DEFAULT_IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Long.valueOf(GlobalConfig.PAGE_LOAD_TIMEOUT).longValue(),
				TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.get(GlobalConfig.APP_URL);

	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDownr(ITestResult result) throws IOException {
		System.out.println("@AfterMethod - BaseCucumberTestNGRunner");
		if (result.isSuccess()) {
			File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String failureImageFileName = result.getMethod().getMethodName()
					+ new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
			File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);
			failureImageFile.getParentFile().mkdir();
			failureImageFile.createNewFile();
			Files.copy(imageFile, failureImageFile);
		}
	}

	@AfterSuite(alwaysRun = true)
	public void quit() throws IOException, InterruptedException {
		System.out.println("@AfterSuite - BaseCucumberTestNGRunner");
		driver.quit();
	}
}
