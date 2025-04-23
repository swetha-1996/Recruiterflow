package tests;

import base.BaseTest;
import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import org.json.simple.JSONObject;
import pages.RecruitmentPage;

import static utils.JsonUtils.readJson;

public class addCandidateTest extends BaseTest {
    @Test
    public void addCandidate() {
       // test = extent.createTest("Add Candidate Test");
        LoginPage LP = new LoginPage(driver);
        DashboardPage DP = new DashboardPage(driver);
        RecruitmentPage RP = new RecruitmentPage(driver);
        //JSONParser jsonParser = new JSONParser();
        JSONObject candidateData = readJson("C:\\OrangeHRM\\OrangeHRM\\src\\main\\resources\\testdata/add_candidate.json");
        JSONObject testCaseData = (JSONObject) candidateData.get("AddCandidateTest");

        //Login to the OrangeHRM Web App
        LP.login(ConfigReader.get("username"), ConfigReader.get("password"));
        //Click on the Recruitment option
        DP.clickRecruitment();
        //Click on the Add Candidate button
        RP.addCandidate(testCaseData.get("firstName").toString(), testCaseData.get("lastName").toString(), testCaseData.get("email").toString(),testCaseData.get("contactNo").toString(),testCaseData.get("vacancy").toString() ,testCaseData.get("resumePath").toString());
        //Verify that the candidate is added successfully
        RP.verifyCandidateAdded(testCaseData);

    }
}