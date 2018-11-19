package com.framework.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {
	private Properties configFile;
	private String configname;
	
	public ConfigurationReader(String fname) {
		configname = fname;

		try {
			//String path = "configuration.properties";
			FileInputStream input = new FileInputStream(configname);

			configFile = new Properties();
			configFile.load(input);

			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String keyName) {
		String keyValue;
		keyValue = configFile.getProperty(keyName);
		if(keyValue!= null) return keyValue;
		else throw new RuntimeException("Key: " + keyName + " not specified in the Configuration.properties file");		
	}
}
