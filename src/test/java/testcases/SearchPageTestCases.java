package testcases;

import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.SearchPage;
import utilities.PropUtils;

public class SearchPageTestCases extends BaseTest {

	@Test(priority = 3)
	public void validateSearchPageValues() {

		test = extent.createTest("Validate the Search Page values", "Check search page values");

		SearchPage searchPage = new SearchPage(driver, test);

		searchPage.validateSearchPage();

		searchPage.validateBasedOnSearchedValues(PropUtils.getPropValue(dataParameterProp, "FROMCITYNAME"),
				PropUtils.getPropValue(dataParameterProp, "DESTINATIONCITYNAME"));

	}

	@Test(priority = 4)
	public void validateSearchPagePricesValuesInDescendingOrder() {

		test = extent.createTest("Validate the Search Page with list of Price values in Descending order",
				"Check Price values in descending order");

		SearchPage searchPage = new SearchPage(driver, test);

		searchPage.checkListOfValuesInDecendingOrder();

	}
}
