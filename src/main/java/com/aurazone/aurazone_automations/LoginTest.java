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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://test.aurazone.shop/");
    }


    public void navigateToLogin() {

        WebElement profileBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@aria-label='Open profile menu']")));

        profileBtn.click();

        WebElement profileOption = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[span[text()='Profile']]")));

        profileOption.click();

        WebElement loginBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[normalize-space()='Log In to Continue']")));

        loginBtn.click();

        WebElement emailPasswordBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Email & Password']")));

        emailPasswordBtn.click();
    }


    @Test(priority = 1)
    public void testLoginWithInvalidPassword() throws IOException {

        navigateToLogin();

        WebElement email = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='email']")));

        email.sendKeys("reehanx777@gmail.com");

        WebElement password = driver.findElement(
                By.xpath("//input[@type='password']"));

        password.sendKeys("Reehanx@77");

        WebElement loginBtn = driver.findElement(
                By.xpath("//button[text()='Sign In']"));

        loginBtn.click();


        WebElement error = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Invalid password')]")));

        System.out.println("Invalid Password Popup: " + error.getText());

        Assert.assertTrue(error.isDisplayed());

        takeScreenshot("InvalidPasswordPopup");
    }


    @Test(priority = 2)
    public void testLoginWithverifiedAccount() throws IOException {

        navigateToLogin();

        WebElement email = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='email']")));

        email.sendKeys("reehanx777@gmail.com");

        WebElement password = driver.findElement(
                By.xpath("//input[@type='password']"));

        password.sendKeys("Reehan@77");

        WebElement loginBtn = driver.findElement(
                By.xpath("//button[text()='Sign In']"));

        loginBtn.click();


        WebElement verifyPopup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Please verify your email.')]")));

        System.out.println("Verify Email Popup: " + verifyPopup.getText());

        Assert.assertTrue(verifyPopup.isDisplayed());

        takeScreenshot("VerifyEmailPopup");
    }
    @Test(priority = 3)
    public void testLoginWithUnverifiedAccount() throws IOException {

        navigateToLogin();

        WebElement email = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='email']")));

        email.sendKeys("r7@gmail.com");

        WebElement password = driver.findElement(
                By.xpath("//input[@type='password']"));

        password.sendKeys("Reehan@77");

        WebElement loginBtn = driver.findElement(
                By.xpath("//button[text()='Sign In']"));

        loginBtn.click();


        WebElement nonuserpassPopup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Invalid email or password')]")));

        System.out.println("Verify Email Popup: " + nonuserpassPopup .getText());

        Assert.assertTrue(nonuserpassPopup .isDisplayed());

        takeScreenshot("nonuserpassPopup");
    }


    public void takeScreenshot(String fileName) throws IOException {

        File folder = new File("./screenshots/");
        if (!folder.exists()) {
            folder.mkdir();
        }

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