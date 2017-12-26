package by.pht.rent.dao.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTypeTransformUnil {
	
	public static int convertId(String id) {
		int idInt = 0;	
		try {
			idInt = Integer.parseInt(id);
			
		}catch (NumberFormatException e) {
			System.out.println("wrong value id");
		}		
		return idInt;
	}
	
	public static double convertPrice(String price) {
		
		double priceDouble = 0;		
		try {
			priceDouble = Double.parseDouble(price);			
		}catch (NumberFormatException e) {
			System.out.println("wrong value for price");
		}		
		return priceDouble;	
	}
	
	public static Date convertDate(String dateString) {
		
		Date date = null;			
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");
			date = sdf.parse(dateString);			
		}catch (NumberFormatException | ParseException e) {
			System.out.println("wrong value for date");
		}		
		return date;	
	}

}
