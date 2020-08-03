package vn.triplet.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	@Type(type = "text")
	private String description;

	@Column(name = "image")
	@Type(type = "text")
	private String image;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "rate_avg")
	private double rate_avg;

	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date create_time;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date update_time;

	@Column(name = "delete_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date delete_time;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems;

	@OneToMany(mappedBy = "product")
	private List<Rate> rates;
	
	@Transient
	private String priceString;
	
	@Transient
	private List<String> images;
	
	@Transient
	private List<String> descriptions;
	
	public int getId() {
		return id;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
	
	public String getPriceString() {
		return priceString;
	}

	public void setPriceString(String priceString) {
		this.priceString = priceString;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", image=" + image + ", quantity=" + quantity + ", price="
				+ price + "]";
	}

}
