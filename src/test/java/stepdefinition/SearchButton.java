package stepdefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.application.runners.BBOSBaseCucumberRunnerLoginTest;
import com.application.runners.BBOSBaseCucumberRunnerSearchTest;
import com.application.runners.BaseCucumberTestNGRunner;
import com.framework.utilities.DriverFactory;

import cucumber.api.java.en.Then;

public class SearchButton extends BBOSBaseCucumberRunnerSearchTest {

	@Then("^I click search button$")
	public void searchButton() throws Throwable {
		driver = DriverFactory.getInstance().getDriver();
		WebElement searchBox = driver.findElement(By.cssSelector("input[name='q']"));
		explicitWait(searchBox);
		searchBox.sendKeys(Keys.ENTER);

	}

}
