package stepdefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.application.runners.BBOSBaseCucumberRunnerLoginTest;
import com.application.runners.BBOSBaseCucumberRunnerSearchTest;
import com.application.runners.BaseCucumberTestNGRunner;
import com.framework.utilities.DriverFactory;

import cucumber.api.java.en.When;

public class SearchText extends BBOSBaseCucumberRunnerSearchTest {

	@When("^I type \"(.*?)\"$")
	public void searchText(String text) throws Throwable {
		driver = DriverFactory.getInstance().getDriver();
		WebElement searchBox = driver.findElement(By.cssSelector("input[name='q']"));
		explicitWait(searchBox);
		searchBox.sendKeys(text);

	}

}
