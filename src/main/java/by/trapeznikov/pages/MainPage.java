package by.trapeznikov.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends AbstractPage{
	
	private final String BASE_URL = "https://belavia.by/home/";
	private static final String AIRPORT_FROM = "Минск (MSQ), BY";
	private static final String AIRPORT_WHERE = "Сочи (AER), RU";
	private final Logger logger = LogManager.getRootLogger();

	@FindBy(id = "OriginLocation")
	WebElement OriginLocation;
	
	@FindBy(id = "OriginLocation_Combobox")
	WebElement OriginLocationCombobox;
	
	@FindBy(id = "DestinationLocation")
	WebElement DestinationLocation;
	
	@FindBy(id = "DestinationLocation_Combobox")
	WebElement DestinationLocationCombobox;
	
	@FindBy(id = "ReturnDate")
	WebElement ReturnDate;
	
	@FindBy(id = "ReturnDate_Datepicker")
	WebElement ReturnDateDatepicker;
	
	@FindBy(id = "DepartureDate")
	WebElement DepartureDate;
	
	@FindBy(id = "DepartureDate_Datepicker")
	WebElement DepartureDateDatepicker;
		
	@FindBy(css = "#step-2 button.btn-large.btn.btn-b2-green.ui-corner-all")
	WebElement buttonFind;

	
	public MainPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);

	}

	@Override
	public void openPage() {

		driver.navigate().to(BASE_URL);
		logger.info("Main Page open");

	}
	
	public String findFlights(){
		
		fieldOriginLocation();
		fieldDestinationLocation();
		fieldDepartureDate();
		fieldReturnDate();
		
	}

	private void fieldReturnDate() {
		// TODO Auto-generated method stub
		
	}

	private void fieldDepartureDate() {
		
		WebElement webElement = driver.findElement(By.id("DepartureDate"));
		String jS = " var s= document.getElementById('DepartureDate');s.type = 'visible';s.value = '2016-12-24';";
		((JavascriptExecutor) driver).executeScript(jS, webElement); 
		
		webElement = driver.findElement(By.id("DepartureDate_Datepicker"));
		jS = " var s= document.getElementById('DepartureDate_Datepicker');s.value = '2016-12-24';";
		((JavascriptExecutor) driver).executeScript(jS, webElement);
		
	}

	private void fieldDestinationLocation() {
		String jS = " var s= document.getElementById('DestinationLocation');s.type = 'visible';s.value = 'AER';";
		((JavascriptExecutor) driver).executeScript(jS, DestinationLocation); 
		DestinationLocationCombobox.sendKeys(AIRPORT_WHERE);
		
	}

	private void fieldOriginLocation() {
	
		String jS = " var s= document.getElementById('OriginLocation');s.type = 'visible';s.value = 'MSQ';";
		((JavascriptExecutor) driver).executeScript(jS, OriginLocation); 
		OriginLocationCombobox.sendKeys(AIRPORT_FROM);
	}
	
	

}


