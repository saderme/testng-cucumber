package com.framework.utilities;

import java.io.IOException;

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


	public DriverFactory() {
	}

	public static DriverFactory getInstance() {
		return instance;
	}

    protected WebDriver initialValue()
    {
		return new ChromeDriver();
    }
  

    public void removeDriver() // Quits the driver and closes the browser
    {
    	ThreadDriver.get().quit();
    	ThreadDriver.remove();
    }
    
	public static void configureDriverPath() throws IOException {
		if(GlobalConfig.DEFAULT_OS.startsWith("Linux")) {
			String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/geckodriver";
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
			String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/chromedriver";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}
		if(GlobalConfig.DEFAULT_OS.startsWith("Mac")) {
			String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/geckodriver";
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
			String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/chromedriver";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}
		if(GlobalConfig.DEFAULT_OS.startsWith("WIN")) {
			String firefoxDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
			String chromeDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}
	}    
	
	public WebDriver getDriver() {
		WebDriver driver = ThreadDriver.get();
		if (driver == null) {
			
			try {
				configureDriverPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
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
				driver = new ChromeDriver(options);
				//WebDriverManager.chromedriver().setup();
				ThreadDriver.set(new ChromeDriver(options));
				break;
			case "firefox":
				//WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				ThreadDriver.set(driver);
				break;
			default:
				//WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				ThreadDriver.set(driver);
			}
		}
		return ThreadDriver.get();
	}

	public WebDriver getDriver(String browser) {
		WebDriver driver = ThreadDriver.get();
		if (driver == null) {
			
			try {
				configureDriverPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
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
				//WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);
				ThreadDriver.set(new ChromeDriver(options));
				break;
			case "firefox":
				//WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				ThreadDriver.set(driver);
				break;
			default:
				//WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				ThreadDriver.set(driver);
			}
		}
		return ThreadDriver.get();
	}

	public void destroyDriver() {
		if (ThreadDriver.get() != null) {
			ThreadDriver.get().quit();
			ThreadDriver = null;
		}
	}


}
