package by.trapeznikov.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import by.trapeznikov.auxiliary.Auxiliary;

public class MainPage extends AbstractPage{
	
	private final String BASE_URL = "https://belavia.by/home/";
	private static final String AIRPORT_FROM = "Минск (MSQ), BY";
	private static final String AIRPORT_WHERE = "Сочи (AER), RU";
	private static final String JS_RETURN_DATE = " var s= document.getElementById('ReturnDate');s.type = 'visible';s.value = '2016-12-30';";
	private static final String JS_RETURN_DATE_PICKER =" var s= document.getElementById('ReturnDate_Datepicker');s.value = '2016-12-30';";
	private static final String JS_DEPATURE_DATE =" var s= document.getElementById('DepartureDate');s.type = 'visible';s.value = '2016-12-25';";
	private static final String JS_DEPATURE_DATE_PICKER =" var s= document.getElementById('DepartureDate_Datepicker');s.value = '2016-12-25';";
	private static final String JS_DESTINATION_LOCATION = " var s= document.getElementById('DestinationLocation');s.type = 'visible';s.value = 'AER';";
	private static final String JS_ORIGIN_LOCATION =" var s= document.getElementById('OriginLocation');s.type = 'visible';s.value = 'MSQ';";
	
	
	
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
		
	@FindBy(css = "#matrix span.date")
	List<WebElement> Dates;
	
	@FindBy(css = "#matrix div.content")
	List<WebElement> Costs;
	
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
	
	public String minCostFlight(){
		
		fieldOriginLocation();
		fieldDestinationLocation();
		fieldDepartureDate();
		fieldReturnDate();
		clickButtonFind();
				
		return minCostOfFligths(datesOut(),datesIn(),cost());
		
	}

	private void fieldReturnDate() {
				
		((JavascriptExecutor) driver).executeScript(JS_RETURN_DATE, ReturnDate); 
		((JavascriptExecutor) driver).executeScript(JS_RETURN_DATE_PICKER, ReturnDateDatepicker); 
		
	}

	private void fieldDepartureDate() {
		
		((JavascriptExecutor) driver).executeScript(JS_DEPATURE_DATE, DepartureDate); 
		((JavascriptExecutor) driver).executeScript(JS_DEPATURE_DATE_PICKER, DepartureDateDatepicker);
		
	}

	private void fieldDestinationLocation() {
		
		((JavascriptExecutor) driver).executeScript(JS_DESTINATION_LOCATION, DestinationLocation); 
		DestinationLocationCombobox.sendKeys(AIRPORT_WHERE);
		
	}

	private void fieldOriginLocation() {
			
		((JavascriptExecutor) driver).executeScript(JS_ORIGIN_LOCATION, OriginLocation); 
		OriginLocationCombobox.sendKeys(AIRPORT_FROM);
	}
	
	private void clickButtonFind(){
		buttonFind.click();
	}

	private ArrayList<String> datesOut(){
						
		ArrayList<String> dateOut = new ArrayList<String>(7);		
		
		int n = 1;
		
		for (WebElement element: Dates){
				if (n<=7)  { n+=1;
							dateOut.add(element.getText());} 
						   			
			}
		return dateOut;
	}
	
	private ArrayList<String> datesIn(){
		
		ArrayList<String> dateIn = new ArrayList<String>(7);
				
		int n = 1;
		
		for (WebElement element: Dates){
				if (n<=7)  { n+=1;} else
				if (n>7)   { n+=1;
						   dateIn.add(element.getText());}				
			}
		return dateIn;
	}

	private ArrayList<Float> cost(){
		
		ArrayList<Float> cost = new ArrayList<Float>(49);
		
		int n=0;
		
		for (WebElement element: Costs){
			if (element.getText().trim().equals("--")){ if (n==0)  { n+=1;} else
													    if (n==1)  { n=0;
														 		     cost.add(null);}
			} else {cost.add(Auxiliary.parsePrice(element.getText()));}
			
		}
		
		return cost;
		
	}

	private String minCostOfFligths(ArrayList<String> datesOut, ArrayList<String> datesIn, ArrayList<Float> cost){
		
		Map<Float, String> flights = new TreeMap<Float, String>();
		
		String Date;
		String DateOut;
		int number=0;
		
		for (int i=0; i<7;i++){
			DateOut = datesOut.get(i);
			for (int j=0; j<7;j++) {
				Date =  DateOut + " " + datesIn.get(j) + " " ;
				if (cost.get(number)!=null) {flights.put(cost.get(number),Date);}
				number+=1;
				Date = null;
			}
		}
		
		return Auxiliary.writeFirstElementMapConsole(flights);
	
	}
		
}


