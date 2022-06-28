package com.mln.login;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InvalidCredentials_UserName {

	@Test
	@Parameters({"username","password"})
	public void login_with_invalid_username(String strUserName, String strPassword){
		//Setting up the driver using WebDriverManager dependency
		WebDriverManager.chromedriver().setup();

		//Opening the chrome browser
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));



		//Navigating to the Application Url
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");

		//Capturing the EmailAddress Webelement 
		WebElement txtbxEmailAddress  = driver.findElement(By.id("email"));

		//Capturing the password Webelement 
		WebElement txtbxPassword = driver.findElement(By.id("passwd"));
		//Capturing the Signin Webelement 
		WebElement btnSignin = driver.findElement(By.id("SubmitLogin"));


		//Setting user Name & Password with values and clicking on sign in
		txtbxEmailAddress.sendKeys(strUserName);
		txtbxPassword.sendKeys(strPassword);
		btnSignin.click();	

		String strCurrentUrl = driver.getCurrentUrl();
		driver.quit();
		Assert.assertEquals(strCurrentUrl, "http://automationpractice.com/index.php?controller=authentication");


	}

}
