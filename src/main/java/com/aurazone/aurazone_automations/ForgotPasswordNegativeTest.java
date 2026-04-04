package com.aurazone.aurazone_automations;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ForgotPasswordNegativeTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://test.aurazone.shop/");
    }

    public void navigateToForgotPassword() {

        WebElement profileBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@aria-label='Open profile menu']")));

        profileBtn.click();
        

        WebElement profileOption = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[span[text()='Profile']]")));

        profileOption.click();

        WebElement login = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[normalize-space()='Log In to Continue']")));

        login.click();

        WebElement emailPasswordBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Email & Password']")));

        emailPasswordBtn.click();

        WebElement forgotPasswordBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Forgot Password?']")));

        forgotPasswordBtn.click();
    }


    @Test(priority = 1)
    public void testEmptyEmailForgotPassword() throws IOException {

        navigateToForgotPassword();

        WebElement continueBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Continue']")));

        continueBtn.click();

        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'User')]")));

        System.out.println("Empty Email Error: " + errorMsg.getText());

        Assert.assertTrue(errorMsg.isDisplayed(),
                "Error message not displayed for empty email");

        takeScreenshot("EmptyEmailBug");
    }


    @Test(priority = 0)
    public void testInvalidEmailForgotPassword() throws IOException, InterruptedException {

        navigateToForgotPassword();

        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@placeholder='Enter your email']")));

        emailField.sendKeys("abc");

        WebElement continueBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Continue']")));
        continueBtn.click();
      

        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'User')]")));

        System.out.println("Invalid Email Error: " + errorMsg.getText());

        Assert.assertTrue(errorMsg.isDisplayed(),
                "Error message not displayed for invalid email");

        takeScreenshot("InvalidEmailBug");
    }


    public void takeScreenshot(String fileName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;

        File src = ts.getScreenshotAs(OutputType.FILE);

        File dest = new File("./screenshots/" + fileName + ".png");

        FileUtils.copyFile(src, dest);
    }


    @AfterMethod
    public void teardown() {

        if (driver != null) {
            driver.quit();
        }
    }

}