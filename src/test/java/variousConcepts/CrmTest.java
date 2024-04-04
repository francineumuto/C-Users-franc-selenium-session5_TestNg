package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CrmTest {

	WebDriver driver;
	String browser;
	String Url;
	String userName = "demo@codefios.com";
	String password = "abc123";

	// test or mock date
	String dashboardHeaderText = "Dashboard";
	String userNameAlertMsg = "please enter your user name";
	String passwordAlertMsg = "please enter your password";
	String newCustomerHeaderText = "New customer";
	String fullName = "selenium";
	String company ="Techfios";
	String email ="abc123@techfios.com";
    String phoneNo ="0123456789";
			

	// by type

	By USER_NAME_FIELD = By.xpath("//*[@id=\"user_name\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGN_BUTTON_FIELD = By.xpath("//*[@id=\"login_submit\"]");
	By DASHBORD_HEADER_FIELD = By.xpath("/html/body/div[1]/aside[1]/div/nav/ul[2]/li[1]/a");
	By CUSOMER_MENU_FIELD = By.xpath("/html/body/div[1]/aside[1]/div/nav/ul[2]/li[2]/a/span");
	By ADD_CUSOMER_MENU_FIELD = By.xpath("//*[@id=\"customers\"]/li[2]/a/span");
	By ADD_CUSOMER_HEADER_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div[1]/div[1]/div/div/header/div");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
	By EMAIL_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[3]/div/input");
	By PHONE_FIELD = By.xpath("//*[@id=\"phone\"]");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@name='company_name']");

	public void readConfig() {

		// inputStream//bufferReader//fileReader//Scanner

		try {

			FileInputStream input = new FileInputStream("src\\main\\java\\config\\conf.properties");

			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			Url = prop.getProperty("Url");

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver = new EdgeDriver();

		} else {
			System.out.println(" please insert a valid browser");
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("url");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(priority = 2)
	public void LoginTest() {

		// by type

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.findElements(DASHBORD_HEADER_FIELD), "dashboardHeaderText", "Dashboard page not found");

	}

	@Test(priority = 1)
	public void validateAlertMsg() {

		driver.findElement(SIGN_BUTTON_FIELD).click();
		String actualUserNameAlertMsg = driver.switchTo().alert().getText();
		Assert.assertEquals(actualUserNameAlertMsg, userNameAlertMsg);
		driver.switchTo().alert().accept();

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(SIGN_BUTTON_FIELD).click();
		String actualPasswordAlertMsg = driver.switchTo().alert().getText();

		Assert.assertEquals(actualPasswordAlertMsg, passwordAlertMsg);
		driver.switchTo().alert().accept();

	}
	@Test
	public void addCustomer() {
		
		LoginTest();
		
		driver.findElement(CUSOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSOMER_HEADER_FIELD).click();
		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName);
		
		Select sel = new Select(driver.findElement(ADD_CUSOMER_HEADER_FIELD));
		sel.selectByVisibleText(company);
		driver.findElement(EMAIL_FIELD).sendKeys(email);
		driver.findElement(PHONE_FIELD).sendKeys(phoneNo);
		
	}
	
	private void selectFromDrowDown(By field, String visibleText) {
		Select sel = new Select(driver.findElement(field));
		sel.selectByVisibleText(visibleText);
		
		
	}
	
	public int generateRandomNum(int boundryNum) {
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(999);
		return generatedNum;
	}

	@AfterMethod
	public void tearDown() {

		driver.close();
		driver.quit();

	}
}
