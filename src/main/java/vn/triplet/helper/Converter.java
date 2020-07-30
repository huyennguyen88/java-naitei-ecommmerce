package vn.triplet.helper;

import java.math.BigDecimal;
import java.util.List;

import vn.triplet.model.Product;

public class Converter {
	
	public static String convertPriceFromBigDecimalToString(BigDecimal d) {
		String result = "";
		String temp = d.toString();
		
		for(int i = 0; i < temp.length() % 3; i ++) {
			result += temp.charAt(i);
		}
		if(temp.length() % 3 != 0)
			result += ".";
		for(int i = temp.length() % 3; i < temp.length(); i ++) {
			result += temp.charAt(i);
			if((i - temp.length() % 3 + 1) % 3 == 0 && i != temp.length() - 1)
				result += ".";
		}
		
		return result;
	}
	
	public static String[] convertPriceFromBigDecimalToString(List<Product> products) {
		String[] price = new String[products.size()];
		int i = 0;
		
		for(Product product : products) {
			price[i ++] = Converter.convertPriceFromBigDecimalToString(product.getPrice());
		}
		
		return price;
	}
	
}
