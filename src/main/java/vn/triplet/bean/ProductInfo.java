package vn.triplet.bean;

import java.math.BigDecimal;
import java.util.Arrays;

import vn.triplet.model.Product;
import vn.triplet.helper.Converter;

public class ProductInfo {

	private int id;
	private String name;
	private String description;
	private String image;
	private BigDecimal price;
	private double rate_avg;
	private int quantity;

	public int getId() {
		return id;
	}

	public ProductInfo() {

	}

	public ProductInfo(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.image = Arrays.asList(product.getImage().split("/")).get(0).toString();
		this.price = product.getPrice();
		this.rate_avg = product.getRate_avg();
		this.quantity = product.getQuantity();
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public double getRate_avg() {
		return rate_avg;
	}

	public void setRate_avg(double rate_avg) {
		this.rate_avg = rate_avg;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
