package Sample.test_sample;




import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * API - For Verification code generation from QR Code/Security Auth token
 * <dependency> <groupId>org.jboss.aerogear</groupId>
 * <artifactId>aerogear-otp-java</artifactId> <version>1.0.0.M8</version>
 * </dependency>
 */

public class test {

	public static void login_MFA(String Browser) throws InterruptedException {
		WebDriver driver = null;

		if ("CHROME".equalsIgnoreCase(Browser)) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("start-maximized");
			options.addArguments("--js-flags=--expose-gc");
			options.addArguments("--enable-precise-memory-info");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-default-apps");
			options.addArguments("--enable-automation");
			options.addArguments("test-type=browser");
			options.addArguments("disable-infobars");
			options.addArguments("disable-extensions");
			options.setExperimentalOption("useAutomationExtension", false);
			System.setProperty("webdriver.chrome.driver", ".\\src\\test\\java\\drivers\\chromedriver.exe");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();

		} else if ("IE".equalsIgnoreCase(Browser)) {
			try {
				Runtime.getRuntime().exec("taskkill /F /IM IEDriver.exe");
				Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.setProperty("webdriver.ie.driver", ".\\src\\test\\java\\drivers\\IEDriverServer.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			driver = new InternetExplorerDriver(ieCapabilities);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			driver.manage().window().maximize();
		} else if ("FIREFOX".equalsIgnoreCase(Browser)) {
			System.setProperty("webdriver.gecko.driver", ".\\src\\test\\java\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();

		} else if ("EDGE".equalsIgnoreCase(Browser)) {

			// DesiredCapabilities edgeCapabilities = DesiredCapabilities.edge();
			try {
				Runtime.getRuntime().exec("taskkill /F /IM msedgedriver.exe");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.setProperty("webdriver.edge.driver", ".\\src\\test\\java\\drivers\\msedgedriver.exe");

			// Start Edge Session
			driver = new EdgeDriver();
			driver.manage().window().maximize();

		}

		driver.manage().window().maximize();
		driver.get("https://login.salesforce.com");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("anil.vykuntham@salesforce.com");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("optum2021");
		driver.findElement(By.xpath("//*[@id=\"Login\"]")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(2000);

		WebElement ele = driver.findElement(By.xpath("//*[@id=\"tc\"]"));
		ele.sendKeys(test.getOTP());

		driver.findElement(By.xpath("//*[@id=\"save\"]")).click();


		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement ele1 = driver.findElement(By.xpath("//*[@id=\"tc\"]"));
		if (ele1.isDisplayed()) {
			Thread.sleep(2000);
			ele1.sendKeys(test.getOTP());
			driver.findElement(By.xpath("//*[@id=\"save\"]")).click();
		}
	}

	public static String getOTP() {
		String otpKeyStr = "BG4EK7ZLXORSQDFMQCU537BR253PWAC2"; // <- this 2FA secret key.

		Totp totp = new Totp(otpKeyStr);
		String twoFactorCode = totp.now(); // <- got 2FA coed at this time!
		System.out.println(twoFactorCode);

		return twoFactorCode;
	}

}

