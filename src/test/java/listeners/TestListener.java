package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtils;

import static base.BaseTest.driver;
//import utils.ExtentManager;

public class TestListener implements ITestListener {
    public static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");

    @Override
    public void onTestStart(ITestResult result) {
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Swetha TD");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("Java Version", "17");
        extent.setSystemInfo("TestNG Version", "7.4.0");
        // Create a new test in the report
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        System.out.println("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass(result.getName());
        String screenshotPath = ScreenshotUtils.captureScreenshot(driver, "CandidateAdded");
        extentTest.get().addScreenCaptureFromPath(screenshotPath);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getName());
        extentTest.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
