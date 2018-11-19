package com.framework.core;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;

//import com.google.gdata.util.common.html.HtmlToText;

public class TestLogger {

	private static boolean root=false;
	
	public static Logger getLogger(Class cls){
		if(root){
			return LogManager.getLogger(cls);
		}

		PropertyConfigurator.configure(GlobalConfig.LOG4J_PATH);
		root = true;
		return LogManager.getLogger(cls);
	}


    /**
     * warn Logger.
     *
     * @param  message
     */
    public static void logAppLogWarning(Logger applog, String message) {
    	applog.warn(message);
    }    
   
    /**
     * Error Logger.
     *
     * @param  message
     */
    public static void logAppLogError(Logger applog, String message) {
    	applog.error(message);
    }  
    
    /**
     * Info Logger.
     *
     * @param  message
     */
    public static void logAppLogInfo(Logger applog, String message) {
    	applog.info(message);
    }  
    
    /**
     * Fatal Logger.
     *
     * @param  message
     */
    public static void logAppLogFatal(Logger applog, String message) {
    	applog.fatal(message);
    }     
    
    /**
     * Log info.
     *
     * @param  message
     */
    public static void logRepInfo(String message) {
        if (message == null) {
            message = "";
        }
        message = "<li><font color='#00cd00'>" + message + "</font></li>";
    	Reporter.log((message), false);
    }

    /**
     * error Logger.
     *
     * @param  message
     */
    public static void logRepError(String message) {
        message = message.replaceAll("\\n", "<br/>");
        message = "<li><b><font color='#6600CC'>" + message + "</font></b></li>";
    	Reporter.log((message), false);
    }
     
    /**
     * warn Logger.
     *
     * @param  message
     */
    public static void logRepFatal(String message) {
    	
        message = "<span style=\"font-weight:bold;color:#cc0052;\">" + message + "</span>";
        
    	Reporter.log((message),true);
    	
    }   
    
    /**
     * warn Logger.
     *
     * @param  message
     */
    public static void logConsole(String message) {
    	System.out.println(message);
    }        
    public static String escape(final String message) {
        return message.replaceAll("\\n", "<br/>").replaceAll("<", "@@lt@@").replaceAll(">", "^^greaterThan^^");
    }

    public static String unEscape(String message) {
        message = message.replaceAll("<br/>", "\\n").replaceAll("@@lt@@", "<").replaceAll("\\^\\^gt\\^\\^", ">");

        //message = HtmlToText.htmlToPlainText(message);
        return message;
    }

    public static void logWebOutput(final String message, final boolean failed) {
        if (failed){
        	logRepError("Output: " + message + "<br/>" );
        } 
        else {
        	logRepInfo("Output: " + message + "<br/>" );
        }
    }

    public static void logWebStep(final String message, final boolean failed) {
        if (failed){
        	logRepError("<li>" + "<b>FailedStep</b>: " + message + "</li>");
        } 
        else {
        	logRepInfo("<li>" + " " + message + "</li>");
        }    	
    }    
    
	public static void main(String[] args) throws Exception {
		//LoginPage lp = new LoginPage();
		
	    Logger log = TestLogger.getLogger(TestLogger.class);
		log.info("logger is configured");
		log.info("logger is configured");
		log.info("logger is configured");
		
	}
}