package com.nakivo.ui.tests;

import com.nakivo.ui.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private static final String LOGIN_URL = "https://localhost:4443/c/login";
    
    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }
    
    @Test(description = "Test Case 1: Successful login")
    public void testSuccessfulLogin() {
        loginPage.navigateToLoginPage(LOGIN_URL);
        loginPage.enterUsername("user");
        loginPage.enterPassword("user");
        loginPage.clickLoginButton();
        
        Assert.assertTrue(loginPage.isOnDashboardPage(), 
            "User should be redirected to dashboard page after successful login");
    }
    
    @Test(description = "Test Case 2: Unsuccessful login with invalid credentials")
    public void testUnsuccessfulLoginInvalidCredentials() {
        loginPage.navigateToLoginPage(LOGIN_URL);
        loginPage.enterUsername("wronguser");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLoginButton();
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for invalid credentials");
        
        String errorText = loginPage.getErrorMessageText();
        Assert.assertTrue(errorText.contains("Invalid credentials") || 
            errorText.toLowerCase().contains("invalid") || 
            errorText.toLowerCase().contains("incorrect"),
            "Error message should contain 'Invalid credentials' or similar text");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}