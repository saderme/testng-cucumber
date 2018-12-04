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

	private static final String USER_DIR = "user.dir";
	private static final String MAC_GECKO_PATH = "";
	private static final String MAC_CHROME_PATH = "";
	private static final String LINUX_GECKO_PATH = "";
	private static final String LINUX_CHROME_PATH = "";
	private static final String WIN_GECKO_PATH = "";
	private static final String WIN_CHROME_PATH = "";
	
	private static final String GECKO_DRIVER = "webdriver.gecko.driver";
	private static final String CHROME_DRIVER = "webdriver.chrome.driver";
	
	private static final String MAC = "Mac";
	private static final String LINUX = "Linux";
	private static final String WIN = "Win";

	private static final String CHROME = "chrome";
	private static final String FIREFOX = "firefox";
	
	private static DriverFactory instance = new DriverFactory();
	private ThreadLocal<WebDriver> ThreadDriver = new ThreadLocal<WebDriver>();


	public DriverFactory() {
	}

	public static DriverFactory getInstance() {
		return instance;
	}

    protected WebDriver initialValue()  {
		return new ChromeDriver();
    }

    public WebDriver getDriver()  {
		return getDriver("");
    }
   
	public WebDriver getDriver(String browser) {
		
		if (ThreadDriver.get() == null) setDriverWith(browser);

		return ThreadDriver.get();
	}
	
    public void setDriverWith(String browser)  {
    	
		try {
			configureDriverPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		browser = (browser.isEmpty()) ? GlobalConfig.DEFAULT_BROWSER:browser;
		
		if (browser.equalsIgnoreCase(CHROME)) {
			setDriverForChromeBrowserWithArguments();
		} else if (browser.equalsIgnoreCase(CHROME)) {
			ThreadDriver.set(new FirefoxDriver());
		} else {
			ThreadDriver.set(new ChromeDriver());
		}
    }
    
    private void setDriverForChromeBrowserWithArguments() {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setPlatform(Platform.ANY);
		ThreadDriver.set(new ChromeDriver(setChromeOptions()));
    }
    
    private ChromeOptions setChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		if (GlobalConfig.HEADLESS.equalsIgnoreCase("true")){
		  options.addArguments("--headless");
		}
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.setExperimentalOption("useAutomationExtension", false);
		return options;
    }

	public static void configureDriverPath() throws IOException {
		String firefoxDriverPath = "";
		String chromeDriverPath = "";
		
		if(GlobalConfig.DEFAULT_OS.startsWith(LINUX)) {
			firefoxDriverPath = System.getProperty(USER_DIR) + LINUX_GECKO_PATH;
			chromeDriverPath = System.getProperty(USER_DIR) + LINUX_CHROME_PATH;
		}
		if(GlobalConfig.DEFAULT_OS.startsWith(MAC)) {
			firefoxDriverPath = System.getProperty(USER_DIR) + MAC_GECKO_PATH;
			chromeDriverPath = System.getProperty(USER_DIR) + MAC_CHROME_PATH;
		}
		if(GlobalConfig.DEFAULT_OS.startsWith(WIN)) {
			firefoxDriverPath = System.getProperty(USER_DIR) + WIN_GECKO_PATH;
			chromeDriverPath = System.getProperty(USER_DIR) + WIN_CHROME_PATH;
		}
		
		System.setProperty(GECKO_DRIVER, firefoxDriverPath);
		System.setProperty(CHROME_DRIVER, chromeDriverPath);	
	}    
	
/*	public void destroyDriver() {
		if (ThreadDriver.get() != null) {
			ThreadDriver.get().quit();
			ThreadDriver = null;
		}
	}*/

    public void removeDriver() // Quits the driver and closes the browser
    {
    	ThreadDriver.get().quit();
    	ThreadDriver.remove();
    }

}
