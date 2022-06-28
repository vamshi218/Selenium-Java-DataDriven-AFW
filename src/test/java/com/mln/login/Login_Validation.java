package com.mln.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


import io.github.bonigarcia.wdm.WebDriverManager;
 

public class Login_Validation {
	
	int TestNo = 0;
	WebDriver driver;
	ExtentReports Reports;
	ExtentHtmlReporter html ;
	ExtentTest Testcase;
	
	//ReporterType reporterType;
	String[][] strLoginCredentials = {
			{"lakme88@gmail.com","123","invalid"},
			{"lakme8@gmail.com","Password@123","invalid"},
			{"lakme8@gmail.com","123","invalid"},
			{"lakme88@gmail.com","Password@123","valid"}
	};
	
	@BeforeSuite
	public void StartReporting(){
		html = new ExtentHtmlReporter("Extent.html");
		Reports =new ExtentReports();
		Reports.attachReporter(html);
		
	}
	
	@AfterSuite
	public void EndReporting(){
		Reports.flush();
		
	}
	
	@BeforeTest
	public void openBrowser(){

		//Setting up the driver using WebDriverManager dependency
		WebDriverManager.chromedriver().setup();
		
	
		
		//Opening the chrome browser
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	@AfterTest
	public void CloseBrowser(){
		WebDriverManager.chromedriver().setup();

		//closing the chrome browser
		driver.close();		
		driver.quit();

	}

	@Test(dataProvider ="login data_exceldata")
	public void login_with_validation(String strUserName, String strPassword,String Validity) throws IOException{
		Testcase = Reports.createTest("login_TestCase_TC1.0"+ "-" +TestNo);
		TestNo =TestNo+1;

		//Navigating to the Application URL
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

		if (Validity.equalsIgnoreCase("valid")){
			Assert.assertEquals(strCurrentUrl, "http://automationpractice.com/index.php?controller=my-account");
			Testcase.log(Status.PASS, strCurrentUrl);
			String strAccountName = driver
					.findElement(By.xpath("//a[(@title ='View my customer account')]/span"))
					.getText();
			//driver.quit();
			Testcase.log(Status.PASS, strAccountName);
			Assert.assertEquals(strAccountName,"srinath Mohan");
			Testcase.log(Status.PASS, strAccountName);
		}else if (Validity.equalsIgnoreCase("invalid")){
			//driver.quit();
			Assert.assertEquals(strCurrentUrl, "http://automationpractice.com/index.php?controller=authentication");
			Testcase.log(Status.FAIL, strCurrentUrl);
			TakesScreenshot ss = (TakesScreenshot) driver;
			File srcFile = ss.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(TestNo +".png"));
			Testcase.addScreenCaptureFromPath(TestNo +".png");
		}


	}

	@DataProvider(name = "login data_classdata")
	private String[][] login_data_provider_classdata(){

		return strLoginCredentials;

	}

	@DataProvider(name = "login data_exceldata")
	private String[][] login_data_provider_exceldata() throws IOException{

		//return getExcelData();
		return gcellData();

	}

	private String[][] getExcelData() throws IOException{

		FileInputStream excelFile = new FileInputStream("C:\\My Folder\\Srinath\\Learnings\\Selenium\\Selenium-Java-DataDriven-AFW\\src\\test\\resources\\Login_Validation.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(excelFile);
		XSSFSheet sheet = wb.getSheet("Data");

		int iRowCount=	sheet.getLastRowNum();
		int iColCount = sheet.getRow(iRowCount).getLastCellNum();
		//System.out.println(iRowCount);
		//System.out.println(iColCount);

		String[][] ExcelData = new String[iRowCount][iColCount];
		for (int iRow =1 ; iRow <=iRowCount; iRow++){			
			for (int iCol =0 ; iCol <iColCount; iCol++){
				ExcelData[iRow-1][iCol] = sheet.getRow(iRow).getCell(iCol).getStringCellValue();
				//System.out.println(ExcelData[iRow-1][iCol]);
			}		

		}
		return ExcelData;

	}


	private String[][] gcellData() throws IOException{

		FileInputStream excelFile = new FileInputStream("C:\\My Folder\\Srinath\\Learnings\\Selenium\\Selenium-Java-DataDriven-AFW\\src\\test\\resources\\Login_Validation.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(excelFile);
		XSSFSheet sheet = wb.getSheet("Data");
		int iRowCount=	sheet.getLastRowNum();
		int iColCount = sheet.getRow(iRowCount).getLastCellNum();

		Iterator<Row> rowIterator = sheet.rowIterator();		
		String[][] ExcelData = new String[iRowCount][iColCount];
		int iRowNo =0;
		while(rowIterator.hasNext()){
			Row RowValue = rowIterator.next();
			Iterator<Cell> cellIterator= RowValue.iterator();
			
			int iColNo =0;
			if (iRowNo >0){
			while(cellIterator.hasNext()){
				ExcelData[iRowNo-1][iColNo] = cellIterator.next().toString();				
				iColNo++;
			}
			}
			iRowNo++;
		}
	
	
		return ExcelData;

	}


}


