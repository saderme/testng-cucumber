package com.test.dataproviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.test.testdatatypes.LoginPOJO;
import com.framework.core.GlobalConfig;

public class LoginJsonDataReader {
private final String loginFilePath = GlobalConfig.TESTDATA_RESOURCE_PATH + "login.json";
private List<LoginPOJO> loginList;

public LoginJsonDataReader(){
	loginList = getLoginData();
}

private List<LoginPOJO> getLoginData() {
	Gson gson = new Gson();
	BufferedReader bufferReader = null;
	try {
		bufferReader = new BufferedReader(new FileReader(loginFilePath));
		LoginPOJO[] login = gson.fromJson(bufferReader, LoginPOJO[].class);
		return Arrays.asList(login);
	}catch(FileNotFoundException e) {
		throw new RuntimeException("Json file not found at path : " + loginFilePath);
	}finally {
		try { if(bufferReader != null) bufferReader.close();}
		catch (IOException ignore) {}
	}
}
	
public final LoginPOJO getLoginDataByUserId(String userid){
		 return loginList.stream().filter(x -> x.getUsername().equalsIgnoreCase(userid)).findAny().get();
}

public final LoginPOJO getLoginDataByAccountKey(String accountkey){
for(LoginPOJO login : loginList) {
	if(login.getAccountkey().equalsIgnoreCase(accountkey)) return login;
}
return null;
}

}