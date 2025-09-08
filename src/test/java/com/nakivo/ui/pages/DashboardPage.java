package com.nakivo.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By dashboardTitle = By.xpath("//h1[contains(text(),'Dashboard')] | //div[@class='dashboard'] | //div[contains(@class,'main-content')]");
    
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(dashboardTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isUserOnDashboard() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("/dashboard") || currentUrl.contains("/home") || !currentUrl.contains("/login");
    }
}