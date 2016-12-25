package by.trapeznikov.auxiliary;

import java.util.Map;

public final class Auxiliary {
	
	public static float parsePrice(String cost){
		
		return Float.valueOf(cost.replaceAll(",",".").split(" EUR")[0]); 
				
	}
	
	public static String writeFirstElementMapConsole(Map<Float, String> map){
		
		String s;
		return s = map.values().toArray()[0]+" "+map.keySet().toArray()[0];
	
	}

}
