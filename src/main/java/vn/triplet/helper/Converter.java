package vn.triplet.helper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import vn.triplet.model.Product;

public class Converter {
	
	/*
	 * FOR PRODUCT
	 */
	
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
		
		return result + " ₫";
	}
	
	public static List<String> splitInformationFromStringToArray(String string) {
		List<String> result = Arrays.asList(string.split("/"));
		return result;
	}
	
	public static Product parseInformationOfProduct(Product product) {
		product.setPriceString(convertPriceFromBigDecimalToString(product.getPrice()));
		product.setImages(splitInformationFromStringToArray(product.getImage()));
		product.setDescriptions(splitInformationFromStringToArray(product.getDescription()));
		return product;
	}
	
	public static List<Product> parseInformationOfProduct(List<Product> products) {
		for(Product product : products) {
			product.setPriceString(convertPriceFromBigDecimalToString(product.getPrice()));
			product.setImages(splitInformationFromStringToArray(product.getImage()));
			product.setDescriptions(splitInformationFromStringToArray(product.getDescription()));
		}
		return products;
	}
	
	/*
	 * 
	 */
}
