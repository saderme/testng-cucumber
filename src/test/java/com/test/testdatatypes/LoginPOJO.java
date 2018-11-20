package com.test.testdatatypes;
import java.util.HashMap;
import java.util.Map;

public class LoginPOJO {

private String username;
private String password;
private String accountkey;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getAccountkey() {
return accountkey;
}

public void setAccountkey(String accountkey) {
this.accountkey = accountkey;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}