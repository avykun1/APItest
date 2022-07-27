package apitester;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;


import com.relevantcodes.extentreports.*;




public class Driver extends AutomationCore{
	public static WebDriver driver = null;
	public static Properties CONFIG =null;
	public static Properties OR =null;
	public static Boolean isLoggedIn = false;
	public Alert alert;
	public static String strTestResult;
	public static int intTestStep=0;
	public static int intInternalCount =0;
	Random rand = new Random(); 
	public static Xls_Reader datasheet= new Xls_Reader("C:\\Users\\avykun1\\Desktop\\Attachments.xlsx");
	
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	public Driver(){
		
			
		if(driver==null){
		// initialize the properties file
		CONFIG = new Properties();
		Properties OR = new Properties();
		try{
			// prop
			FileInputStream fs = new FileInputStream("C:\\Users\\avykun1\\Desktop\\desktop-old\\Folders\\OMMS-SF\\com.optum.OMMS_SF\\src\\test\\resources\\com\\salesforce\\qa\\CONFIG\\config.properties");
			CONFIG.load(fs);
			
			// OR
			fs = new FileInputStream("C:\\Users\\avykun1\\maven\\employerproject\\src\\test\\java\\externalwebportal\\employer\\config\\OR.properties");
			OR.load(fs);
			}catch(Exception e){
				// error
				return;
		}
		
		System.out.println(CONFIG.getProperty("browser"));
		if(CONFIG.getProperty("browser").equals("Mozilla"))
			  driver=new FirefoxDriver();
		else if(CONFIG.getProperty("browser").equals("IE")){
			System.setProperty("webdriver.ie.driver", CONFIG.getProperty("iedriver_exe"));
			  //driver=new InternetExplorerDriver();
			  DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			  ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			  driver = new InternetExplorerDriver(ieCapabilities);
		}
		else if(CONFIG.getProperty("browser").equals("Chrome")){
			System.out.println("In Driver Page--------------");
//			log.debug("Opening "+CONFIG.getProperty("browser")+ " browser.");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\avykun1\\Desktop\\DMR\\DMR_Automation-master\\SeleniumServers\\chromedriver.exe");
			
			ChromeOptions options_chrome = new ChromeOptions();
			
			options_chrome.addArguments("enable-automation");
			options_chrome.addArguments("--window-size=1920,1080");
			options_chrome.addArguments("--no-sandbox");
			options_chrome.addArguments("--disable-extensions");
			options_chrome.addArguments("--dns-prefetch-disable");
			options_chrome.addArguments("--disable-gpu");
//			options_chrome.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		    options_chrome.setExperimentalOption("useAutomationExtension", false); 
		    driver = new ChromeDriver(options_chrome);
		}
		// implicit wait for the whole app
//		log.debug("Instantiating Test data sheet");
//		datasheet = new Xls_Reader(System.getProperty("user.dir")+"//src//test//resources//com//salesforce//qa//testdata//OMMS_SF_Test_Data.xlsx");
		
//		log.debug("Maximizing the browser window.");
		driver.manage().window().maximize();
		
//		log.debug("Navigating to Salesforce URL.");
		driver.get(CONFIG.getProperty("APP_URL"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
		
}

	/*public void openBrowser(String bType){
		
			
		if(bType.equals("Mozilla"))
			driver=new FirefoxDriver();
		else if(bType.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("chromedriver_exe"));
			driver=new ChromeDriver();
		}
		else if (bType.equals("IE")){
			
			System.out.println(CONFIG.getProperty("IEbrowser_exe"));
			System.setProperty("webdriver.ie.driver", CONFIG.getProperty("IEdriver_exe"));
			driver= new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
*/
	
	public void acceptAlert(){
		alert = driver.switchTo().alert();
				alert.accept();
		
	}
	public void navigate(String urlKey){
		
			driver.get(CONFIG.getProperty(urlKey));
		
	}
	
	public void click(String locatorKey){
		getElement(OR.getProperty(locatorKey)).click();
	}
	
	public void type(String locatorKey,String data){
		getElement(OR.getProperty(locatorKey)).sendKeys(data);
		
	}
	// finding element and returning it
	public WebElement getElement(String locatorKey){
		WebElement e=null;
//		try{
//		if(locatorKey.endsWith("_id"))
//			e = driver.findElement(By.id(CONFIG.getProperty(locatorKey)));
//		else if(locatorKey.endsWith("_name"))
//			e = driver.findElement(By.name(CONFIG.getProperty(locatorKey)));
//		else if(locatorKey.endsWith("_xpath"))
			e = driver.findElement(By.xpath(locatorKey));
//		else{
//			reportFailure("Locator not correct - " + locatorKey);
//			Assert.fail("Locator not correct - " + locatorKey);
//		}
		
//		}catch(Exception ex){
//			// fail the test and report the error
//			reportFailure(ex.getMessage());
//			ex.printStackTrace();
//			Assert.fail("Failed the test - "+ex.getMessage());
//		}
		return e;
	}
	
	
	
	public void switchtoframe(String locatorKey){
		switchtodefault();
		System.out.println(OR.getProperty(locatorKey));
		int index = Integer.parseInt(OR.getProperty(locatorKey));
	driver.switchTo().frame(index);
	}
	
	
	public void switchtodefault(){
		
	driver.switchTo().defaultContent();
	}
	
	public void selectListItemByText(String locatorKey, String text)
	{
		try
		{
			Select sel = new Select(driver.findElement(By.xpath(OR.getProperty(locatorKey))));
			sel.selectByVisibleText(text);
			
			}
		catch(StaleElementReferenceException se)
        {
			//log.info("In StaleElementReferenceException block");
			se.printStackTrace();}
	}
	
	
	public void selectRandomListItem(String locaterKey)
	{
		try
		{Select sel = new Select(driver.findElement(By.xpath(OR.getProperty(locaterKey))));
		int listitemcount=sel.getOptions().size();
		
		int index = rand.nextInt(listitemcount);	//This will give index value from 0 to no of list items.
		//int index = rand.nextInt((max - min) + 1) + min;	//This will give index value from 1 to no of list items.
		System.out.println("Random index value to be selected from CSP list"+index);
		if (index!=0)
		sel.selectByIndex(index);
		else
			sel.selectByIndex(index+1);
		
		}
		catch(StaleElementReferenceException se)
        {
			
			se.printStackTrace();
			
			}
			
			
        }
	/***********************Validations***************************/
	public boolean verifyTitle(){
		return false;		
	}
	
	public boolean isElementPresent(String locatorKey){
		List<WebElement> elementList=null;
		if(locatorKey.endsWith("_id"))
			elementList = driver.findElements(By.id(CONFIG.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_name"))
			elementList = driver.findElements(By.name(CONFIG.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_xpath"))
			elementList = driver.findElements(By.xpath(CONFIG.getProperty(locatorKey)));
		else{
			reportFailure("Locator not correct - " + locatorKey);
			Assert.fail("Locator not correct - " + locatorKey);
		}
		
		if(elementList.size()==0)
			return false;	
		else
			return true;
	}
	
	public boolean verifyText(String locatorKey,String expectedTextKey){
		String actualText=getElement(OR.getProperty(locatorKey)).getText().trim();
		String expectedText=CONFIG.getProperty(expectedTextKey);
		if(actualText.equals(expectedText))
			return true;
		else if (actualText.contains(expectedTextKey))
			   return true;
			else
			   return false;
		
	}
	
	public String getText(String locatorKey){
		String actualText=getElement(OR.getProperty(locatorKey)).getText().trim();
		
		if(actualText!=null)
			return actualText;
		else 
			return null;
		
	}
	
	public String getMemberIDfromText(String locatorKey){
		String actualText=getElement(OR.getProperty(locatorKey)).getText().trim();
		String Membercode = actualText.substring(actualText.length()-10, actualText.length());
		
		if(Membercode!=null)
			return Membercode;
		else 
			return null;
		
	}
	public void enterDate(String datelocaterKey, String datevalue){
		
		String[] val=datevalue.split("/");
		for (int i=0; i<3; i++){
		String locaterKey =	datelocaterKey+i+"]";
		driver.findElement(By.xpath(OR.getProperty(locaterKey))).sendKeys(val[i]);
		}
		
	}
	
	public void enterZip(String ZiplocaterKey, String Zipvalue){
		String locaterKey = null;
		String[] val=Zipvalue.split("-");
		for (int i=0; i<2; i++){
			if (val[i]!=null){
		locaterKey =	ZiplocaterKey+i+"]";
		driver.findElement(By.xpath(OR.getProperty(locaterKey))).sendKeys(val[i]);
			}
			else
			driver.findElement(By.xpath(OR.getProperty(locaterKey))).sendKeys("0000");
			
		}
	}
	
public void enterSSN(String SSNlocaterKey, String SSNvalue){
	String[] val=SSNvalue.split("-");
	for (int i=0; i<3; i++){
	String locaterKey =	SSNlocaterKey+i+"]";
	driver.findElement(By.xpath(OR.getProperty(locaterKey))).sendKeys(val[i]);
	}
	}


public void enterPrefixandSelectGender(String PrefixlocaterKey, String GenderlocaterKey, String Gendervalue){
	
	Select prefixlist = new Select(driver.findElement(By.xpath(OR.getProperty(PrefixlocaterKey))));
	Select genderlist = new Select(driver.findElement(By.xpath(OR.getProperty(GenderlocaterKey))));
	if(Gendervalue=="M"){
		prefixlist.selectByVisibleText("Mr");
		genderlist.selectByVisibleText("Male");}
		else{
			prefixlist.selectByVisibleText("Ms");
			genderlist.selectByVisibleText("Female");}	
	
	}
	

public void enterAddress(String AddresslocaterKey, String Addressvalue){
	String locaterKey = null;
	String[] val=Addressvalue.split(";");
	for (int i=0; i<2; i++){
		if (val[i]!=null){
	locaterKey =	AddresslocaterKey+i+"]";
	driver.findElement(By.xpath(OR.getProperty(locaterKey))).sendKeys(val[i]);
		}
		else
		driver.findElement(By.xpath(OR.getProperty(locaterKey))).sendKeys("");
		
	}
}
	
	public void findAllLinks(WebDriver driver) throws Exception
	 
	  {
	 
		 List<WebElement> links = driver.findElements(By.tagName("a"));
		 
		 System.out.println("Total links are "+links.size());
	 
		  for(int i=0;i<links.size();i++)
			{
				
				WebElement ele= links.get(i);
								
				String linktext = links.get(i).getText();
				if(linktext!=null)
				{
				System.out.println(i+".  **********Link Text**********");
				System.out.println(links.get(i).getText());
				String url=ele.getAttribute("href");
				System.out.println(i+".  **********URL for the link**********");
				System.out.println(url);
				isLinkBroken(url);
				}
				
				}
			
		 
	  }
	 
		public void isLinkBroken(String linkUrl) throws Exception
	 
		{
			{
		        try 
		        {
		           URL url = new URL(linkUrl);
		           
		           HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
		           
		           httpURLConnect.setConnectTimeout(3000);
		           
		           httpURLConnect.connect();
		           
		           if(httpURLConnect.getResponseCode()==200)
		           {
		               System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage());
		            }
		          if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)  
		           {
		               System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
		            }
		        } catch (Exception e) {
		           
		        }

			
		}
	 
		}

	
	
	/*****************************Reporting********************************/
	
		public static String fnStrNumber(int intNumber) {
			if (intNumber < 10)
				return "0" + intNumber;
			else
				return "" + intNumber;
		}
		
		
		
				
	public static void reportPass(String msg){
		test.log(LogStatus.PASS, msg);
	}
	
	public static void reportFailure(String msg){
		test.log(LogStatus.FAIL, msg);
		takeScreenShot();
		Assert.fail(msg);
	}
	
	public static void fnTestResult(String methodname, String strExcepted, String strResult, String msg) {
		try {
			Driver.intTestStep += 1;

			String userMust = "	User must be able to ";
			if (strResult.equalsIgnoreCase("Passed")) 
			{
				takeScreenShot();
				System.out.println("Step:" + fnStrNumber(intTestStep));
				System.out.println("Expected:" + userMust + strExcepted);
				System.out.println("Actual:" + "		User is able to " + strExcepted);
				System.out.println("Result:" + "		Passed");
						
				Reporter.log(strExcepted);
				try
				{
					reportPass(msg);
				}
				catch(Exception ex)
				{
					
				}	
				
			} else {
				
				System.out.println("Step:" + fnStrNumber(intTestStep) + userMust + strExcepted
						+ "		User is not able to " + strExcepted + "		Failed");
				
				Reporter.log(strExcepted);
				
				try
				{
					reportFailure(msg);
				}
				catch(Exception ex)
				{
					
				}
				Assert.fail("Step:" + fnStrNumber(intTestStep) + userMust + strExcepted
						+ "		User is not able to " + strExcepted + "		Failed");
			}
		} catch (Exception e) {
			System.out.println("Exception Details" + e);
			e.printStackTrace();
		}
		intInternalCount = 0; // To Reset internal Step Counter.
	}
	
	public static void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//put screenshot file in reports
		test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		
	}
	
}
