package by.trapeznikov.steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import by.trapeznikov.driver.DriverSingleton;
import by.trapeznikov.pages.MainPage;


public class Steps {
	
private WebDriver driver;
	
	private final Logger logger = LogManager.getRootLogger();
	
	public void initBrowser(){
							
		driver = DriverSingleton.getDriver();
	}
	
	public void closeDriver(){
		
		driver.quit();
		driver = null;
	}
	
	public String minCostFlight(){
		
		MainPage mainPage =new MainPage(driver);
		mainPage.openPage();
		return mainPage.minCostFlight();
		
		
	}

}
