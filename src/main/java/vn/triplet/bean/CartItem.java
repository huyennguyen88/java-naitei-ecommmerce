package vn.triplet.bean;

import java.math.BigDecimal;
import java.util.Arrays;

import vn.triplet.helper.Converter;

public class CartItem {
	
	private int quantity;
	private int productId;
	private String image;
	private BigDecimal price;
	private String name;
	public CartItem() {

	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CartItem(int quantity,BigDecimal price,String image,String name,int productId) {
		this.quantity = quantity;
		this.name = name;
		this.image = Arrays.asList(image.split("/")).get(0).toString();
		this.price = price;
		this.productId=productId;
	}

	public int getQuantity() {
		return quantity;
	}

}
