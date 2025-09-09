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
    private By dashboardHeader = By.xpath("//h1[contains(text(),'Dashboard')] | //div[@id='dashboard'] | //div[contains(@class,'dashboard')]");
    
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(dashboardHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public boolean isUserOnDashboard() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("/dashboard") || currentUrl.contains("/home") || isDashboardDisplayed();
    }
}