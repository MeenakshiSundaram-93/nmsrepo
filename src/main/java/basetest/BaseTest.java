package basetest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utilities.PropUtils;

public class BaseTest {

	public static WebDriver driver;
	public static ExtentTest test;
	public static ExtentReports extent;
	public ExtentHtmlReporter htmlReporter;
	public static String resultFile;
	public static String indexFile;
	
	public File inputDataFile = PropUtils.getPropFile(System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "configuration" + System.getProperty("file.separator"),
			"DataParameter.properties");
			public Properties dataParameterProp = PropUtils.getProps(inputDataFile);
	
	public void logInfo(String msg) {
	test.info(MarkupHelper.createLabel(msg, ExtentColor.BLUE));
	}

	public void logFail(String msg) {
	test.fail(MarkupHelper.createLabel(msg, ExtentColor.RED));
	Assert.assertFalse(true, msg);
	}

	public void logPass(String msg) {
	test.pass(MarkupHelper.createLabel(msg, ExtentColor.GREEN));
	}
	
	
	public static void createIndexFile() {
		// Creating Index file
		System.out.println("Inside Index file");
		File source = new File(resultFile);
		File destination = new File(indexFile);
		try {
			FileUtils.copyFile(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeSuite
	public void initReport() {
		indexFile = System.getProperty("user.dir") + System.getProperty("file.separator") + "reports"
				+ System.getProperty("file.separator") + "index.html";
		if (extent == null) {
			resultFile = System.getProperty("user.dir") + System.getProperty("file.separator") + "reports"
					+ System.getProperty("file.separator")
					+ new SimpleDateFormat("MMM-dd-yyyy_hh-mm").format(new Date()) + "_" + "AutomationResults.html";
			htmlReporter = new ExtentHtmlReporter(resultFile);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);

			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setDocumentTitle("Automation Testing Report");
			try {
				htmlReporter.config().setReportName("Regression Testing");

			} catch (NullPointerException e) {
				htmlReporter.config().setReportName("Automation Testing");
			}
			try {
				htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			} catch (NullPointerException e) {

				htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			}
			try {

				htmlReporter.config().setTheme(Theme.DARK);

			} catch (NullPointerException e) {
				htmlReporter.config().setTheme(Theme.STANDARD);
			}
		}
	}

	@BeforeTest
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches", "--disable-extensions");
		options.addArguments("chrome.switches", "test-type");
		options.addArguments("chrome.switches", "start-maximized");
		options.addArguments("chrome.switches", "no-sandbox");
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("chrome.switches", "--incognito");
		driver = new ChromeDriver(options);
		driver.get("https://www.goibibo.com/");

	}

	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {

		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				test.fail(MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
						ExtentColor.RED));
				test.fail(result.getThrowable());

			} else if (result.getStatus() == ITestResult.SUCCESS) {

				test.pass(MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
			} else {
				test.skip(MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
				test.skip(result.getThrowable());
			}
		} catch (Exception ex) {
			test.fail(ex.getMessage());
		}
	}

	@AfterSuite(alwaysRun = true)
	public void tearDownReports() {
		driver.close();
		driver.quit();
		try {
			extent.flush();
			createIndexFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
