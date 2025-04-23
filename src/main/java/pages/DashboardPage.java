package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.WaitUtils;

public class DashboardPage {

    WebDriver driver;

    @FindBy(xpath = "//*[text()='Recruitment']")
    private WebElement recruitmentOption;

    @FindBy(xpath = "//header//*[text()='Recruitment']")
    private WebElement recruitmentHeader;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickRecruitment() {
        // Wait for the Recruitment option to be visible and clickable
        WaitUtils.waitForElementToBeVisible(driver, recruitmentOption, 10);
        recruitmentOption.click();
        WaitUtils.waitForElementToBeVisible(driver, recruitmentHeader, 60);
        // Verify that the Recruitment header is displayed after clicking
        Assert.assertTrue(recruitmentHeader.isDisplayed(), "Recruitment header is not displayed");
    }
}
