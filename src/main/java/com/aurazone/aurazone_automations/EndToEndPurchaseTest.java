package com.aurazone.aurazone_automations;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EndToEndPurchaseTest {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;

        driver.get("https://test.aurazone.shop/");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void verifyHomepageLoads() {

        String title = driver.getTitle();
        System.out.println("Homepage Title: " + title);

        Assert.assertFalse(title.isEmpty(),
                "Homepage did not load properly");

        takeScreenshot("01_Homepage");
    }

    @Test(priority = 2)
    public void navigateToProductsPage() {

        WebElement shopNow = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Shop Now']")));

        shopNow.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[@title]")));

        int products = driver.findElements(
                By.xpath("//h3[@title]")).size();

        System.out.println("Products Found: " + products);

        Assert.assertTrue(products > 0,
                "Products not displayed");

        takeScreenshot("02_ProductPage");
    }

    @Test(priority = 3)
    public void selectProduct() {

        WebElement product = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//h3[@title])[4]")));

        String productName = product.getText();
        System.out.println("Selected Product: " + productName);

        product.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(),'Add to Cart')]")));

        takeScreenshot("03_ProductDetail");
    }

    @Test(priority = 4)
    public void addToCart() {

        WebElement addToCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Add to Cart')]")));

        js.executeScript("arguments[0].scrollIntoView(true);", addToCart);

        addToCart.click();

        // Wait for cart icon update
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'cart')]")));

        System.out.println("Product Added To Cart");

        takeScreenshot("04_AddToCart");
    }

    @Test(priority = 5)
    public void goToCart() {

        WebElement cartIcon = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@aria-label='cart' or contains(@href,'cart')]")));

        cartIcon.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("cart"),
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Cart')]"))
        ));

        System.out.println("Cart Page Loaded");

        takeScreenshot("05_CartPage");
    }

    @Test(priority = 6)
    public void proceedToCheckout() throws InterruptedException {
    	 Thread.sleep(4500);

        WebElement checkoutBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Checkout')]")));

        js.executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);

//        Thread.sleep(4500);

        checkoutBtn.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("checkout"),
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input"))
        ));

        System.out.println("Checkout Page Loaded");

        takeScreenshot("06_CheckoutPage");
    }

    @Test(priority = 7)
    public void fillDeliveryAddress() {

    	WebElement nameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[contains(@placeholder," +
                                 "'Enter your full name')]")));
        nameField.clear();
        nameField.sendKeys("Reehan X");

        // type email
        WebElement emailField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'you@example.com')]"));
        emailField.clear();
        emailField.sendKeys("reehan@example.com");

        // type phone number
        WebElement phoneField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'98765 43210')]"));
        phoneField.clear();
        phoneField.sendKeys("9876543210");

        // type street address
        WebElement streetField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'123 Main Street')]"));
        streetField.clear();
        streetField.sendKeys("Anna Nagar");

        // type apartment (optional field)
        WebElement apartmentField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'Apartment')]"));
        apartmentField.clear();
        apartmentField.sendKeys("Flat 12B");

        // type city
        WebElement cityField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'Mumbai')]"));
        cityField.clear();
        cityField.sendKeys("Chennai");

        // type state
        WebElement stateField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'Maharashtra')]"));
        stateField.clear();
        stateField.sendKeys("Tamil Nadu");

        // type pin code
        WebElement pinField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'400001')]"));
        pinField.clear();
        pinField.sendKeys("600001");

        System.out.println("All address fields filled");

        takeScreenshot("07_Address");

    }

    @Test(priority = 8)
    public void selectCashOnDelivery() {

        WebElement cod = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'Cash') or contains(text(),'COD')]")));

        js.executeScript("arguments[0].scrollIntoView(true);", cod);

        cod.click();

        System.out.println("COD Selected");

        takeScreenshot("08_COD");
    }

    @Test(priority = 9)
    public void placeOrder() {

        WebElement placeOrder = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Place Order') or contains(text(),'Confirm')]")));

        js.executeScript("arguments[0].scrollIntoView(true);", placeOrder);

        placeOrder.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Order') or contains(text(),'Thank')]")));

        System.out.println("Order Placed Successfully");

        takeScreenshot("09_OrderPlaced");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("order") ||
                        driver.getPageSource().contains("Order"),
                "Order not placed successfully"
        );
    }

    private void fillField(By locator, String value) {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));

        field.clear();
        field.sendKeys(value);
    }

    private void takeScreenshot(String fileName) {

        try {

            File folder = new File("./screenshots");

            if (!folder.exists()) {
                folder.mkdir();
            }

            TakesScreenshot ts = (TakesScreenshot) driver;

            File src = ts.getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(src,
                    new File("./screenshots/" + fileName + ".png"));

        } catch (IOException e) {

            System.out.println("Screenshot Failed");
        }
    }
}