package com.framework.core;

import java.io.IOException;

import com.framework.utilities.ConfigurationReader;

/**
 * @author Mahomed Sader
 * Retrieves all the test config details from the config file.
 * Config file path is specified in PropertyReader and name is passed from BaseTestPlan  
 */
public class GlobalConfig {

    public static int DEFAULT_IMPLICIT_WAIT_TIMEOUT = 5;
    public static int DEFAULT_EXPLICIT_WAIT_TIME_OUT = 15;
    public static String DEFAULT_PAGE_LOAD_TIMEOUT = "90";
    public static int WEB_SESSION_TIMEOUT = 30 * 1000;
    public static String IMPLICIT_WAIT_TIMEOUT;
    public static long EXPLICIT_WAIT_TIME_OUT; 
    public static String DRIVER_BIN_PATH;   
    public static String LOG4J_PATH;   
    public static String DEFAULT_OS; 
    public static String DEFAULT_BROWSER; 
    public static String EXTENT_REPORT_PATH_WIN; 
    public static String EXTENT_REPORT_PATH_MAC;
    public static String EXTENT_REPORT_FILENAME; 
    public static String EXTENT_REPORT_PATH; 
    public static String EXTENT_CONFIG_PATH; 
    public static String TEST_DATA_PATH;
    public static String SCREENSHOT_PATH;
    public static String APP_URL;   
    public static String PAGE_LOAD_TIMEOUT;  
    public static String TESTDATA_RESOURCE_PATH;    

    public static boolean SOFT_ASSERT = false;  //set via testng parameter
    public static String HEADLESS;   
    
    public GlobalConfig(String configFile) {
      	ConfigurationReader cr = new ConfigurationReader(configFile);
    	GlobalConfig.APP_URL = cr.getProperty("applicationUrl");
 		GlobalConfig.IMPLICIT_WAIT_TIMEOUT = cr.getProperty("implicitwait");
 		GlobalConfig.EXPLICIT_WAIT_TIME_OUT =  Long.valueOf(cr.getProperty("explicitwait")).longValue();
 		GlobalConfig.PAGE_LOAD_TIMEOUT = cr.getProperty("pageloadtime");
 		GlobalConfig.DEFAULT_OS = cr.getProperty("defaultOS");	
 		GlobalConfig.DEFAULT_BROWSER = cr.getProperty("browser");		
 		GlobalConfig.EXTENT_CONFIG_PATH = cr.getProperty("extentconfigpath");	
 		GlobalConfig.DRIVER_BIN_PATH = cr.getProperty("driverbinpath");
 		GlobalConfig.LOG4J_PATH = cr.getProperty("log4jpath");
 		GlobalConfig.EXTENT_REPORT_PATH = cr.getProperty("extentreportpath");
  		GlobalConfig.EXTENT_REPORT_PATH_WIN = cr.getProperty("extentreportpathWin");
 		GlobalConfig.EXTENT_REPORT_PATH_MAC = cr.getProperty("extentreportpathMac");
 		GlobalConfig.EXTENT_REPORT_FILENAME = cr.getProperty("extentreportpathFileName");
 		GlobalConfig.TEST_DATA_PATH = cr.getProperty("testdatapath");
 		GlobalConfig.SCREENSHOT_PATH = cr.getProperty("screenshotpath");
 		GlobalConfig.TESTDATA_RESOURCE_PATH = cr.getProperty("testDataResourcePath");
 	    
 		GlobalConfig.HEADLESS = "false";
    }
    
    public static String getTestDataPath(){
    	return TEST_DATA_PATH;
    }
    
    public static void setSoftAssert(String softassert){
    	if (softassert.equalsIgnoreCase("true")){
    		System.out.println("Setting SoftAssert to true");
     		SOFT_ASSERT = true;
    	} else
    	{  SOFT_ASSERT = false;	
    	System.out.println("Setting SoftAssert to false");}
    }
    
    public static void setHeadless(String headless){
    		HEADLESS = headless;
    } 
    
	public static void configureDriverPath() throws IOException {
		if(DEFAULT_OS.startsWith("Linux")) {
			String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/geckodriver";
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
			String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/chromedriver";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}
		if(DEFAULT_OS.startsWith("Mac")) {
			String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/geckodriver";
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
			String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/chromedriver";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}
		if(DEFAULT_OS.startsWith("Windows")) {
			String firefoxDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
			String chromeDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}
	}

     
}