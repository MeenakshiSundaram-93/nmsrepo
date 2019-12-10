package basepage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.github.javafaker.Faker;

import basetest.BaseTest;

public class BasePage extends BaseTest {

	
	public BasePage(WebDriver driver, ExtentTest test) {
		BaseTest.driver = driver;
		BaseTest.test = test;
	}

	public void waitUntilElementDisplayed(final WebElement webElement) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		ExpectedCondition<Boolean> elementIsDisplayed = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver arg0) {
				try {
					webElement.isDisplayed();
					return true;
				} catch (NoSuchElementException e) {
					return false;
				} catch (StaleElementReferenceException f) {
					return false;
				}
			}
		};
		wait.until(elementIsDisplayed);
	}

	public boolean waitForElementTobeClickable(WebElement element, long timeOut) {

		WebElement isElementClickable = null;

		try {

			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			isElementClickable = wait.until(ExpectedConditions.elementToBeClickable(element));
			logInfo("element present");
		} catch (Exception e) {
			logInfo(" Exception in the wait for 10 seconds for the  Element To Appear" + e);
		}

		return isElementClickable.isDisplayed();
	}

	public void isDisplayed(WebElement locator, String message) {
		try {
			if (locator != null) {
				waitUntilElementDisplayed(locator);
				if (locator.isDisplayed()) {
					logPass(message + " is Displayed");
				} else {
					logFail(message + " is Not Displayed");
				}
			}

			else {
				logInfo("Locator is Null");
			}
		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}

	public void isDisplayedThenClick(WebElement locator, String message) {
		try {
			waitForElementTobeClickable(locator, 5);
			if (locator.isDisplayed()) {
				locator.click();
				logPass(message + " id Displayed and Clicked on it.");
			} else {
				logFail(message + " is Not Displayed");
			}
		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}

	public void Click(WebElement locator, String message) {
		try {
			waitForElementTobeClickable(locator, 5);
			System.out.println(locator);
			locator.click();
			logPass(message + " id Clicked on it.");
		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}

	public void isDisplayedThenEnterText(WebElement locator, String message, String sendText) {
		try {
			waitUntilElementDisplayed(locator);
			System.out.println("element present ----- ");
			isDisplayed(locator, message);
			locator.click();
			locator.sendKeys(sendText);
		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}

	public void isDisplayedThenClearText(WebElement locator, String message) {
		try {
			waitUntilElementDisplayed(locator);
			System.out.println("element present ----- ");
			isDisplayed(locator, message);
			locator.clear();

		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}
	
	public void isDisplayedThenActionClick(WebElement locator, String message) {
		try {
			waitUntilElementDisplayed(locator);
			if (locator.isDisplayed()) {
				actionClick(locator);
				logPass(message + " id Displayed and Clicked on it.");
			} else {
				logFail(message + " is Not Displayed");
			}
		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}

	public void actionClick(WebElement locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOf(locator));
			Actions act = new Actions(driver);
			act.moveToElement(locator).click(locator).build().perform();
		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}

	public void checkElementPresenceAndClick(WebElement locator, String elementText) {
		if (locator.isDisplayed()) {
			actionClick(locator);
			logPass(elementText + " clicked successfully");
		} else {
			logFail(elementText + " not clickable");
		}
	}

	public String getText(WebElement locator) {
		try {
			String text = "";

			if (locator.isDisplayed()) {
				waitUntilElementDisplayed(locator);
				text = locator.getText().trim();
			}
			return text;
		} catch (Exception ex) {
			logFail(ex.getMessage());
			return null;
		}
	}

	public void validateIsPageDisplayed(WebElement leftPanel, WebElement mainFrame) {
		if (getText(leftPanel).equals(getText(mainFrame))) {
			logPass("Expected Page is Displayed");
		} else {
			logFail("Expected Page is not displayed");
		}
	}

	public void verifyText(WebElement locator, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.textToBePresentInElement(locator, text));
			Assert.assertEquals(locator.getText().trim(), text);
			logInfo("Text Present");
		} catch (Exception ex) {
			logFail(ex.getMessage());
		}
	}

	public boolean waitToCheckElementIsDisplayed(final By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		try {
			if (wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed() == true) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException e) {
			logInfo(" Exception in the wait for element To Appear" + e);
			return false;
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			return false;
		}
	}

	public boolean waitForTextToAppear(String textToAppear, long timeOut) {

		boolean isTextPresent = false;
		WebElement bodyElement = null;

		try {
			bodyElement = driver.findElement(By.tagName("body"));
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			isTextPresent = wait.until(ExpectedConditions.textToBePresentInElement(bodyElement, textToAppear));
			logInfo(textToAppear + "is present in the page = " + isTextPresent);
		} catch (Exception e) {
			logInfo(" Exception in the wait for Text To Appear" + e);
		}

		return isTextPresent;
	}
	
	public enum MonthEnum {
		JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
	}

	public void pickDate(String date, WebElement datePicker, WebElement calendarDialogBoxLocator) throws Exception {

		int monthsDiff;
		Date dateFormat = new SimpleDateFormat("yyyyMMdd").parse(date);
	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat);
		
		Calendar today = new GregorianCalendar();
        today.setTime(new Date());
        
		int month = calendar.get(Calendar.MONTH);
		if (month == 11) {
			monthsDiff = 1;
		} else {
			System.out.println("Today Month differnce:"+today.get(Calendar.MONTH)+"Given Month:"+calendar.get(Calendar.MONTH));
			monthsDiff = (today.get(Calendar.MONTH)+1-12) 
	                + (calendar.get(Calendar.MONTH)+1);
			System.out.println("Moth differnce"+monthsDiff);
			pickMonth(month, calendarDialogBoxLocator, monthsDiff);
		}
		show(datePicker);
		pickDay(date, calendarDialogBoxLocator);
	}

	private void show(WebElement element) throws Exception {

		isDisplayedThenActionClick(element, "Date Picker");
	}

	
	private void pickMonth(int month, WebElement calendarDialogBox, int monthDiff) {

		if (monthDiff > 1) {
			for(int i = 0; i < monthDiff; i++) {
				nextMonth(calendarDialogBox);
			}
		} else {
			logFail("Change to upcoming date to view the flights");
		}
		
	}

	private void pickDay(String day, WebElement calendarDialogBox) throws Exception {
		System.out.println("int day" + day);
		WebElement selectDay = calendarDialogBox.findElement(By.id("fare_" + day));
		waitForElementTobeClickable(selectDay, 1);
		isDisplayedThenActionClick(selectDay, "Day");

	}

	private void nextMonth(WebElement calendarDialogBox) {

		calendarDialogBox.findElement(By.cssSelector("span.DayPicker-NavButton--next")).click();

	}
	
	public Faker fakerAPI() {
		Faker faker = new Faker();
		return faker;
	}
	
	public void waitForPageLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }
	
}
