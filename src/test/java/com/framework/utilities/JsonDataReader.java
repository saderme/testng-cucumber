package com.framework.utilities;

import java.io.FileInputStream;
import java.util.Properties;

import com.test.dataproviders.LoginJsonDataReader;

public class JsonDataReader {

	 private static LoginJsonDataReader jsonLoginDataReader;
	 
	 private JsonDataReader() {
	 }
	 
	 public static LoginJsonDataReader getLoginJsonReader(){
	 return (jsonLoginDataReader == null) ? new LoginJsonDataReader() : jsonLoginDataReader;
	 }
}
