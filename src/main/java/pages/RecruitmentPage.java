package pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.ScreenshotUtils;
import utils.WaitUtils;

public class RecruitmentPage {
    WebDriver driver;

    @FindBy(linkText = "Recruitment")
    private WebElement recruitmentTab;
    @FindBy(xpath = "//*[@class='orangehrm-header-container']//*[@type='button']")
    private WebElement addBtn;
    @FindBy(name = "firstName")
    private WebElement firstName;
    @FindBy(name = "lastName")
    private WebElement lastName;
    @FindBy(xpath = "//label[contains(text(), 'Email')]/following::input[1]")
    private WebElement email;
    @FindBy(xpath = "//input[@type='file']")
    private WebElement resumeUpload;
    @FindBy(xpath = "//label[contains(text(), 'Contact Number')]/following::input[1]")
    private WebElement contactNumber;
    @FindBy(xpath = "//*[@class='oxd-select-text oxd-select-text--active']")
    private WebElement vacancySelect;
    @FindBy(xpath = "//*[text()='Senior QA Lead']")
    private WebElement vacancyOptionSeniorQALead;
    @FindBy(css = "button[type='submit']")
    private WebElement saveBtn;
    @FindBy(xpath = "//*[text()='Successfully Saved']")
    private WebElement successMessage;
    @FindBy(xpath = "//p[contains(.,'Application Initiated')]")
    private WebElement applicationInitiatedStatus;
    @FindBy(xpath = "//button[contains(.,'Shortlist')]")
    private WebElement shortlistBtn;

    public RecruitmentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addCandidate(String fname, String lname, String emailId,String contactNo,String vacancy, String resumePath) {
        // Wait for the Add button to be visible and clickable
        WaitUtils.waitForElementToBeVisible(driver, addBtn, 15);
        addBtn.click();
        // Wait for the form fields to be visible before interacting with them
        WaitUtils.waitForElementToBeVisible(driver, firstName, 15);
        firstName.sendKeys(fname);
        lastName.sendKeys(lname);
        email.sendKeys(emailId);
        contactNumber.sendKeys(contactNo);
        vacancySelect.click();
        //vacancyOptionSeniorQALead.click();
        driver.findElement(By.xpath("//*[text()='" + vacancy + "']")).click(); // Dynamically select vacancy
        //contact
        //Vacancy
        resumeUpload.sendKeys(resumePath);
        saveBtn.click();
        WaitUtils.waitForElementToBeVisible(driver, successMessage, 15);

        // Verify that the success message is displayed after saving
        Assert.assertTrue(successMessage.isDisplayed(), "Success message is not displayed");
    }
    public void verifyCandidateAdded(JSONObject testCaseData) {
        WaitUtils.waitForElementToBeVisible(driver, firstName, 15);
        WaitUtils.waitForElementToBeVisible(driver, lastName, 15);
        WaitUtils.waitForElementToBeVisible(driver,applicationInitiatedStatus, 15);
        WaitUtils.waitForElementToBeClickable(driver, shortlistBtn, 15);

        String actualFname =firstName.getAttribute("value");
        String actualLname =lastName.getAttribute("value");

        Assert.assertEquals(actualFname, testCaseData.get("firstName").toString(), "First name does not match");
        Assert.assertEquals(actualLname, testCaseData.get("lastName").toString(), "Last name does not match");
    }

    public void takeScreenshot(String testName) {
        // Implement screenshot logic here
        // For example, using a method from the BaseTest class
        //BaseTest.takeScreenshot(driver, testName);
        ScreenshotUtils.captureScreenshot(driver, testName);
    }
}
