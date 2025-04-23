package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import utils.EmailUtils;

import java.io.File;
import java.time.Duration;

public class BaseTest {

    public static WebDriver driver;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //driver.get(ConfigReader.get("baseUrl"));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseUrl"));

    }

    @AfterClass
    public void tearDown() {

        if (driver != null) driver.quit();
    }

    @AfterSuite
    public void tearDownSuite() {
        //EmailUtils.sendReport("C:\\OrangeHRM\\OrangeHRM\\test-output\\ExtentReport.html");
        EmailUtils.sendReport(System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "ExtentReport.html");
    }
}
