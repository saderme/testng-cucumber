package com.framework.utilities;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.framework.core.GlobalConfig;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static DriverFactory instance = new DriverFactory();
	private ThreadLocal<WebDriver> ThreadDriver = new ThreadLocal<WebDriver>();

	// private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	public DriverFactory() {
	}

	public static DriverFactory getInstance() {
		return instance;
	}

    protected WebDriver initialValue()
    {
		return new ChromeDriver();
    }
  
/*    public WebDriver getDriver() // call this method to get the driver object and launch the browser
    {
       return ThreadDriver.get();
    }*/

    public void removeDriver() // Quits the driver and closes the browser
    {
    	ThreadDriver.get().quit();
    	ThreadDriver.remove();
    }
    
	/*
	 * public void initialize() { if (ThreadDriver.get() == null)
	 * createNewDriverInstance(); }
	 */

	/*
	 * public static void createNewDriverInstance() {
	 * 
	 * switch (GlobalConfig.DEFAULT_BROWSER) { case "chrome": DesiredCapabilities
	 * caps = DesiredCapabilities.chrome(); caps.setPlatform(Platform.ANY);
	 * ChromeOptions options = new ChromeOptions(); //
	 * options.addArguments("--headless"); options.addArguments("--disable-gpu");
	 * options.addArguments("--no-sandbox");
	 * options.addArguments("--disable-dev-shm-usage");
	 * options.setExperimentalOption("useAutomationExtension", false);
	 * WebDriverManager.chromedriver().setup(); driver.set(new
	 * ChromeDriver(options)); break; case "firefox":
	 * WebDriverManager.firefoxdriver().setup(); driver.set(new FirefoxDriver());
	 * break; default: WebDriverManager.chromedriver().setup(); driver.set(new
	 * ChromeDriver()); } }
	 */

	/*
	 * public static WebDriver getNewDriverBrowserInstance(String browser) {
	 * 
	 * switch (browser) { case "chrome": DesiredCapabilities caps =
	 * DesiredCapabilities.chrome(); caps.setPlatform(Platform.ANY); ChromeOptions
	 * options = new ChromeOptions(); // options.addArguments("--headless");
	 * options.addArguments("--disable-gpu"); options.addArguments("--no-sandbox");
	 * options.addArguments("--disable-dev-shm-usage");
	 * options.setExperimentalOption("useAutomationExtension", false);
	 * WebDriverManager.chromedriver().setup(); driver.set(new
	 * ChromeDriver(options)); break; case "firefox":
	 * WebDriverManager.firefoxdriver().setup(); driver.set(new FirefoxDriver());
	 * break; default: WebDriverManager.chromedriver().setup(); driver.set(new
	 * ChromeDriver()); } return driver.get(); }
	 * 
	 * public static WebDriver getDriver() { if (driver.get() == null) {
	 * createNewDriverInstance(); } return driver.get(); }
	 */

	public WebDriver getDriver() {
		WebDriver driver = ThreadDriver.get();
		if (driver == null) {
			switch (GlobalConfig.DEFAULT_BROWSER) {
			case "chrome":
				DesiredCapabilities caps = DesiredCapabilities.chrome();
				caps.setPlatform(Platform.ANY);
				ChromeOptions options = new ChromeOptions();
				if (GlobalConfig.HEADLESS.equalsIgnoreCase("true")){
				  options.addArguments("--headless");
				}
				options.addArguments("--disable-gpu");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.setExperimentalOption("useAutomationExtension", false);
				WebDriverManager.chromedriver().setup();
				ThreadDriver.set(new ChromeDriver(options));
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				ThreadDriver.set(driver);
				break;
			default:
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				ThreadDriver.set(driver);
			}
		}
		return ThreadDriver.get();
	}

	public WebDriver getDriver(String browser) {
		WebDriver driver = ThreadDriver.get();
		if (driver == null) {
			switch (browser) {
			case "chrome":
				DesiredCapabilities caps = DesiredCapabilities.chrome();
				caps.setPlatform(Platform.ANY);
				ChromeOptions options = new ChromeOptions();
				if (GlobalConfig.HEADLESS.equalsIgnoreCase("true")){
					  options.addArguments("--headless");
					}
				options.addArguments("--disable-gpu");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.setExperimentalOption("useAutomationExtension", false);
				WebDriverManager.chromedriver().setup();
				ThreadDriver.set(new ChromeDriver(options));
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				ThreadDriver.set(driver);
				break;
			default:
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				ThreadDriver.set(driver);
			}
		}
		return ThreadDriver.get();
	}
	/*
	 * public static WebDriver createDriver(String browser) { if (driver == null) {
	 * switch (browser) { case "chrome": DesiredCapabilities caps =
	 * DesiredCapabilities.chrome(); caps.setPlatform(Platform.ANY);
	 * WebDriverManager.chromedriver().setup(); driver = new ChromeDriver(); break;
	 * case "firefox": WebDriverManager.chromedriver().setup(); driver = new
	 * ChromeDriver(); break; default: WebDriverManager.firefoxdriver().setup();
	 * driver = new FirefoxDriver(); } } return driver; }
	 */

	public void destroyDriver() {
		if (ThreadDriver.get() != null) {
			ThreadDriver.get().quit();
			ThreadDriver = null;
		}
	}

	/*
	 * public static void closeDriver() { if (driver != null) { driver.quit();
	 * driver = null; } }
	 */
}
