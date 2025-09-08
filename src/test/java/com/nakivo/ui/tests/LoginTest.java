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
        // Step 1: Open the login page
        loginPage.navigateToLoginPage(LOGIN_URL);
        
        // Step 2: Enter username
        loginPage.enterUsername("user");
        
        // Step 3: Enter password
        loginPage.enterPassword("user");
        
        // Step 4: Click the Log In button
        loginPage.clickLoginButton();
        
        // Step 5: Verify user is redirected to dashboard
        Assert.assertTrue(loginPage.isOnDashboard(), 
            "User should be redirected to dashboard after successful login");
        Assert.assertFalse(loginPage.getCurrentUrl().contains("/login"), 
            "URL should not contain /login after successful login");
    }
    
    @Test(description = "Test Case 2: Unsuccessful login with invalid credentials")
    public void testUnsuccessfulLoginInvalidCredentials() {
        // Step 1: Open the login page
        loginPage.navigateToLoginPage(LOGIN_URL);
        
        // Step 2: Enter invalid username
        loginPage.enterUsername("wronguser");
        
        // Step 3: Enter invalid password
        loginPage.enterPassword("wrongpassword");
        
        // Step 4: Click the Log In button
        loginPage.clickLoginButton();
        
        // Step 5: Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for invalid credentials");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Invalid credentials"), 
            "Error message should contain 'Invalid credentials'");
        Assert.assertTrue(loginPage.getCurrentUrl().contains("/login"), 
            "User should remain on login page after failed login");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}