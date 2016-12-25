package by.trapeznikov;

import by.trapeznikov.steps.Steps;

public class BelaviaRunner {
	
	public static void main(String [] args) {
	
		Steps steps = new Steps();
		steps.initBrowser();
		System.out.println(steps.minCostFlight());
		
	}
	

}
