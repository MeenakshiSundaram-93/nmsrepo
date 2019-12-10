package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.google.common.collect.Ordering;

import basepage.BasePage;
import repository.Locator;

public class SearchPage extends BasePage{

	@FindBy(id = Locator.SEARCH_PAGE_HEADER_BOX)
	public WebElement searchPageBox;
	
	@FindBy(id = Locator.ONE_WAY_TRIP_RADIO_LABLE)
	public WebElement oneWayTripRadioBtn;
	
	@FindBy(css = Locator.LIST_OF_PRICE_VALUES)
	public List<WebElement> listOfPriceValues;
	
	@FindBy(css = Locator.ICON_ARROW_UP)
	public WebElement arrowUpDescendingOrder;
	
	@FindBy(id = Locator.FROM_CITY)
	public WebElement fromCity;
	
	@FindBy(id = Locator.TO_CITY)
	public WebElement toCity;
	
	@FindBy(id = Locator.SEARCH_PAGE_FARE_TRENDS)
	public WebElement searchPageFareTrends;
	
	public SearchPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}

	public void validateSearchPage() {
		waitForPageLoad(driver);
		isDisplayed(searchPageBox, "Search Header text is Displayed");
	}
	
	public void validateBasedOnSearchedValues(String fromCityName, String toCityName) {
		oneWayTripRadioBtn.isSelected();
		
		if(fromCity.getAttribute("value").equals(fromCityName) && toCity.getAttribute("value").equals(toCityName)) {
			logPass("Based on search values displayed");
		} else {
			logFail("Search value is mismatched");
		}
		
	}
	
	public void checkListOfValuesInDecendingOrder() {
		
		isDisplayed(arrowUpDescendingOrder, "Check the arrow is Up");
		
		List<WebElement> listOfValues = listOfPriceValues;
		System.out.println("Size are" + listOfValues.size());

		ArrayList<String> listStoreValues = new ArrayList<String>();
		for (WebElement value : listOfValues) {
			System.out.println("values are" + value.getText().replace(",", ""));
			listStoreValues.add(value.getText().replaceAll(",", ""));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,100)", "");
			waitUntilElementDisplayed(value);
		}

//		boolean isSorted = Ordering.natural().isOrdered(listStoreValues);
//		System.out.println(isSorted);
		boolean isSorted = sortedOrNot(listStoreValues);
		if(isSorted) {
			logPass("The List Values are sorted");
		} else {
			logFail("The List Values are not sorted");
		}
	}
	
	public boolean sortedOrNot(ArrayList<String> listOfPriceValues) {
		
		System.out.println("number of values "+ listOfPriceValues.size());
		try {
			for (int i = 0; i < listOfPriceValues.size() + 1; i++) {
				int temp = listOfPriceValues.get(i).compareTo(listOfPriceValues.get(i + 1));
				System.out.println("temp value" + temp);
				if (temp > 0) {
					System.out.println("FARE VALUE POSITION --" + i);
					return false;
				}
			}
		} catch (IndexOutOfBoundsException ie) {
			logInfo("Set of list was sorted");
		}
		return true; 
	   }

	
	
}
