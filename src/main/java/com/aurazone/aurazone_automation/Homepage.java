package com.aurazone.aurazone_automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepage {
	WebDriver driver ;
	public Homepage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements( driver,this);
	}
	@FindBy(xpath = "//button[normalize-space()='Shop Now']")
	WebElement shopnow_bt;
	@FindBy(css = "input[placeholder='Max']")
	WebElement maxfilter;
	@FindBy(css = "input[placeholder='Min']")
	WebElement minfilter;
	@FindBy(xpath = "//button[normalize-space()='Apply Filters']")
	WebElement applyfilers_btn;
	public void clickshopnow() {
		shopnow_bt.click();
	}
	public void minvalue(int minval) {
		minfilter.sendKeys(String.valueOf(minval));
	}
	public void maxvalue(int maxval) {
		maxfilter.sendKeys(String.valueOf(maxval));
	}
	public void clickapplyfilter() {
	applyfilers_btn.click();
	}
}
