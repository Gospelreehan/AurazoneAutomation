package com.aurazone.aurazone_automations;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InvalidPriceFilterTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://test.aurazone.shop/");
    }

    @Test
    public void test_InvalidPriceRange_FilterShouldNotApply() throws IOException {

        // Click Shop Now
        driver.findElement(By.xpath("//button[normalize-space()='Shop Now']")).click();

        // Wait for filter fields
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Min']")));

        // Enter invalid range
        driver.findElement(By.cssSelector("input[placeholder='Min']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Min']")).sendKeys("109");

        driver.findElement(By.cssSelector("input[placeholder='Max']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Max']")).sendKeys("100");

        // Click Apply Filters
        driver.findElement(By.xpath("//button[normalize-space()='Apply Filters']")).click();

        // Wait for loading to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(text(),'Loading')]")));

        // Count Products (Fixed XPath)
        int productCount = driver.findElements(
                By.xpath("//h3[@title]")).size();

        System.out.println("Product Count After Invalid Filter: " + productCount);

        // Assertion
        Assert.assertTrue(productCount > 0,
                "Filter incorrectly applied with invalid price range");

        // Take Screenshot
        takeScreenshot("InvalidPriceFilter");
    }

    public void takeScreenshot(String fileName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;

        File src = ts.getScreenshotAs(OutputType.FILE);

        File folder = new File("./screenshots/");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File trg = new File("./screenshots/" + fileName + ".png");

        FileUtils.copyFile(src, trg);
    }

    @AfterClass
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

    }

}