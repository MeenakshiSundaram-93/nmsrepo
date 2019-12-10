package testcases;

import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.HomePage;
import pages.SearchPage;

public class HomePageTestCases extends BaseTest {

	@Test(priority = 1)
	public void validateEnteringWrongValuesInMandateFields() {
		test = extent.createTest("Validate the Homepage by entering wrong name in the field",
				"Enter the wrong value to validate error msg");
		HomePage homePage = new HomePage(driver, test);

		homePage.validateHomePage();

		homePage.enterWrongMandatoryFields();

		homePage.validateErrorMsg();
	}

	@Test(priority = 2)
	public void validateEnteringValuesInMandateFields() {
		test = extent.createTest("Validate the Homepage by entering Correct values in the field",
				"Enter the right value to validate error msgs");
		HomePage homePage = new HomePage(driver, test);
		SearchPage searchPage = new SearchPage(driver, test);

		homePage.validateHomePage();

		homePage.enterMandatoryFields();

		searchPage.validateSearchPage();
	}

}
