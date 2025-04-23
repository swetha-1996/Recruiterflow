package pages;

import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.WaitUtils;

public class LoginPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@class='orangehrm-login-branding'] ")
    private WebElement brandingLogo;
    @FindBy(name = "username")
    private WebElement usernameField;
    @FindBy(name = "password")
    private WebElement passwordField;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginBtn;
    @FindBy(xpath = "//header//*[text()='Dashboard']")
    private WebElement dashboardHeader;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



    public void login(String username, String password) {
        // Wait for the elements to be visible before interacting with them
        WaitUtils.waitForElementToBeVisible(driver, brandingLogo, 10);
        WaitUtils.waitForElementToBeVisible(driver, usernameField, 10);
        usernameField.sendKeys(username);
        WaitUtils.waitForElementToBeVisible(driver, passwordField, 10);
        passwordField.sendKeys(password);
        loginBtn.click();
        WaitUtils.waitForElementToBeVisible(driver, dashboardHeader, 10);

        // Verify that the dashboard header is displayed after login
        Assert.assertTrue(dashboardHeader.isDisplayed(), "Dashboard header is not displayed");
    }
}
