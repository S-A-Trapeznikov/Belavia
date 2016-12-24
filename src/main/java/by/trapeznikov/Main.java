package by.trapeznikov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Main {

	private static final String BASE_URL = "https://belavia.by/home/";
	private static final String AIRPORT_FROM = "Минск (MSQ), BY";
	private static final String AIRPORT_WHERE = "Сочи (AER), RU";
	
	public static void main(String [] args) throws InterruptedException{
		
		WebDriver driver = driver();
		WebElement webElement;
		
		webElement = driver.findElement(By.id("OriginLocation"));
		String jS = " var s= document.getElementById('OriginLocation');s.type = 'visible';s.value = 'MSQ';";
		((JavascriptExecutor) driver).executeScript(jS, webElement); 
		driver.findElement(By.id("OriginLocation_Combobox")).sendKeys(AIRPORT_FROM);
		
		webElement = driver.findElement(By.id("DestinationLocation"));
		jS = " var s= document.getElementById('DestinationLocation');s.type = 'visible';s.value = 'AER';";
		((JavascriptExecutor) driver).executeScript(jS, webElement); 
		driver.findElement(By.id("DestinationLocation_Combobox")).sendKeys(AIRPORT_WHERE);
		
		webElement = driver.findElement(By.id("DepartureDate"));
		jS = " var s= document.getElementById('DepartureDate');s.type = 'visible';s.value = '2016-12-24';";
		((JavascriptExecutor) driver).executeScript(jS, webElement); 
		
		webElement = driver.findElement(By.id("DepartureDate_Datepicker"));
		jS = " var s= document.getElementById('DepartureDate_Datepicker');s.value = '2016-12-24';";
		((JavascriptExecutor) driver).executeScript(jS, webElement); 
		
		
		webElement = driver.findElement(By.id("ReturnDate"));
		jS = " var s= document.getElementById('ReturnDate');s.type = 'visible';s.value = '2016-12-30';";
		((JavascriptExecutor) driver).executeScript(jS, webElement); 
		
		webElement = driver.findElement(By.id("ReturnDate_Datepicker"));
		jS = " var s= document.getElementById('ReturnDate_Datepicker');s.value = '2016-12-30';";
		((JavascriptExecutor) driver).executeScript(jS, webElement); 
		
		driver.findElement(By.cssSelector("#step-2 button.btn-large.btn.btn-b2-green.ui-corner-all")).click();
		
		Thread.sleep(3000);;
				

//		List<WebElement> elements = driver.findElements(By.className("content"));
		List<WebElement> elementsDate = driver.findElements(By.cssSelector("#matrix span.date"));
		List<WebElement> elementsCost = driver.findElements(By.cssSelector("#matrix div.content"));
		
		
		ArrayList<String> dateOut = new ArrayList<String>(7);		
		ArrayList<String> dateIn = new ArrayList<String>(7);
		ArrayList<Float> cost = new ArrayList<Float>(49);
		
		int n = 1;
		
		for (WebElement element: elementsDate){
				if (n<=7)  { n+=1;
							dateOut.add(element.getText());} else
				if (n>7) { n+=1;
						   dateIn.add(element.getText());}				
			}
		
		n=0;
		
		for (WebElement element: elementsCost){
			if (element.getText().trim().equals("--")){ if (n==0)  { n+=1;} else
													    if (n==1)  { n=0;
														 		     cost.add(null);}
			} else {cost.add(parsePrice(element.getText()));}
			
		}
								
				
		Map<Float, String> flights = new TreeMap<Float, String>();
		
		String Date;
		String DateOut;
		int number=0;
		
		for (int i=0; i<7;i++){
			DateOut = dateOut.get(i);
			for (int j=0; j<7;j++) {
				Date =  DateOut + " " + dateIn.get(j) + " " ;
				if (cost.get(number)!=null) {flights.put(cost.get(number),Date);}
				number+=1;
				Date = null;
			}
		}
		
		System.out.println(flights.values().toArray()[0]+" "+flights.keySet().toArray()[0]);
	
	}
	
	
	private static WebDriver driver(){
		System.setProperty("webdriver.gecko.driver","c:\\Selenium\\drivers\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
  		driver.get(BASE_URL);
  		return driver;
	}
	
	private static float parsePrice(String cost){
		
		return Float.valueOf(cost.replaceAll(",",".").split(" EUR")[0]); 
		
		
	}

}
