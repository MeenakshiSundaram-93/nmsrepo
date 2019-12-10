package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.aventstack.extentreports.ExtentTest;

import basepage.BasePage;
import repository.Locator;
import utilities.PropUtils;

public class HomePage extends BasePage{

	@FindBy(css = Locator.HOME_PAGE_HEADER_TEXT)
	public WebElement homePageHeaderText;
	
	@FindBy(id = Locator.FROM_PLACE)
	public WebElement fromPlace;
	
	@FindBy(id = Locator.TO_PLACE)
	public WebElement toPlace;
	
	@FindBy(id = Locator.AUTO_SUGGEST_DROPDOWN)
	public WebElement autoSuggestDropdown;
	
	@FindBy(id = Locator.ONE_WAY_BUTTON)
	public WebElement oneWayButton;
	
	@FindBy(id = Locator.DEPARTURE_DATE_PICKER)
	public WebElement departureDatePicker;
	
	@FindBy(css = Locator.DATE_PICKER_CONTAINER)
	public WebElement datePickerContainer;
	
	@FindBy(id = Locator.SEARCH_BUTTON)
	public WebElement searchButton;
	
	@FindBy(css = Locator.ERROR_MSG)
	public WebElement errorMsg;
	
	public HomePage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);

	}
	
	public void selectCityAutoSuggestOptionFromDropDown(String cityName) {
			
        isDisplayed(autoSuggestDropdown, "Auto Suggestion is displayed");
        List<WebElement> optionsToSelect = autoSuggestDropdown.findElements(By.tagName("li"));
        for(WebElement option : optionsToSelect){
            if(option.getText().contains(cityName)) {
                System.out.println("Trying to select: "+cityName);
                option.click();
                break;
            }
        }
	}
	
	
	public void selectAutoSuggestOptionFromDropDownByKeys(String cityName) {
		
		isDisplayed(autoSuggestDropdown, "Auto Suggestion is displayed");
		autoSuggestDropdown.sendKeys(cityName);
        autoSuggestDropdown.sendKeys(Keys.ARROW_DOWN);
        autoSuggestDropdown.sendKeys(Keys.ENTER);
	}
	
	public void validateHomePage() {
		
		waitForPageLoad(driver);
		isDisplayed(homePageHeaderText, "Home Page Header Text");
		if(getText(homePageHeaderText).trim().equals("Domestic And International Flights")) {
			logPass("Header Page Text is present");
		} else {
			logFail("Header text is not present in the page");
		}
		
		
	}
	
	public void enterMandatoryFields() {

		isDisplayedThenActionClick(oneWayButton, "Oney Way Button is Displayed and Clicked");
		isDisplayedThenEnterText(fromPlace, "Click From City",
				PropUtils.getPropValue(dataParameterProp, "FROMCITYNAME"));
		selectCityAutoSuggestOptionFromDropDown(PropUtils.getPropValue(dataParameterProp, "FROMCITYNAME"));

		isDisplayedThenEnterText(toPlace, "Click To City",
				PropUtils.getPropValue(dataParameterProp, "DESTINATIONCITYNAME"));
		selectCityAutoSuggestOptionFromDropDown(PropUtils.getPropValue(dataParameterProp, "DESTINATIONCITYNAME"));
		try {
			pickDate(PropUtils.getPropValue(dataParameterProp, "DEPARTUREDATE"), departureDatePicker,
					datePickerContainer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isDisplayedThenActionClick(searchButton, "Click Search Button");
	}
	
	public void enterWrongMandatoryFields() {
		
		isDisplayedThenActionClick(oneWayButton, "Oney Way Button is Displayed and Clicked");
		isDisplayedThenEnterText(fromPlace, "Click From City", fakerAPI().address().cityPrefix());
//		waitForElementTobeClickable(toPlace, 10);
		isDisplayedThenEnterText(toPlace, "Click To City", fakerAPI().address().cityPrefix());
		try {
			pickDate(PropUtils.getPropValue(dataParameterProp, "DEPARTUREDATE"), departureDatePicker, datePickerContainer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isDisplayedThenActionClick(searchButton, "Click Search Button");
	}

	
	public void validateErrorMsg() {
		
		isDisplayed(errorMsg, "Validate Error Message");
	}
	
	
}
