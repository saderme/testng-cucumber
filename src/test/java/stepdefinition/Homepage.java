package stepdefinition;

import org.testng.Assert;

import com.application.runners.BBOSBaseCucumberRunnerLoginTest;
import com.application.runners.BBOSBaseCucumberRunnerSearchTest;
import com.application.runners.BaseCucumberTestNGRunner;
import com.framework.utilities.DriverFactory;

import cucumber.api.java.en.Given;

public class Homepage extends BBOSBaseCucumberRunnerSearchTest {

	@Given("^I am on \"(.*?)\" search page$")
	public void googlePage(String text) throws Throwable {
		driver = DriverFactory.getInstance().getDriver();
		String title = driver.getTitle();
			if(text == "google") {
				Assert.assertEquals(title, "Google");
			} else if(text == "cucumber") {
				Assert.assertEquals(title, "cucumber - Google Search");
			} else if(text == "junit") {
				Assert.assertEquals(title, "junit - Google Search");
			}   		
	}

}
