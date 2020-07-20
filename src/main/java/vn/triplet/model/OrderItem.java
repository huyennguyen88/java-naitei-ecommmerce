package vn.triplet.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orderitems")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "price_unit", nullable = false)
	private BigDecimal price_unit;
	
	@Column(name = "price_total", nullable = false)
	private BigDecimal price_total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice_unit() {
		return price_unit;
	}

	public void setPrice_unit(BigDecimal price_unit) {
		this.price_unit = price_unit;
	}

	public BigDecimal getPrice_total() {
		return price_total;
	}

	public void setPrice_total(BigDecimal price_total) {
		this.price_total = price_total;
	}
	
}
