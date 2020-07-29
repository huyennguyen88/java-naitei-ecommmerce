package vn.triplet.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.dao.OrderDAO;
import vn.triplet.dao.OrderItemDAO;
import vn.triplet.dao.ProductDAO;
import vn.triplet.model.Order;
import vn.triplet.model.OrderItem;
import vn.triplet.model.Product;
import vn.triplet.model.Order.Status;
import vn.triplet.model.User;
import vn.triplet.service.OrderService;

public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderItemDAO orderItemDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	public OrderItemDAO getOrderItemDAO() {
		return orderItemDAO;
	}

	public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
		this.orderItemDAO = orderItemDAO;
	}
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Override
	public Order findById(Serializable key) {
		try {
			return getOrderDAO().findById(key);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public Order saveOrUpdate(Order entity) {
		try {
			return getOrderDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public boolean delete(Order entity) {
		try {
			getOrderDAO().delete(entity);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean createOrder(Order order,HashMap<Integer,Integer> items, User user) {
		try {
			order.setStatus(Status.PENDING);
			order.setUser(user);
			Order newOrder = saveOrUpdate(order);
			java.util.Set<Integer> keySet = items.keySet();
			for (Integer key : keySet) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				Product product = getProductDAO().findById(key);
				orderItem.setProduct(product);
				orderItem.setQuantity(items.get(key));
				orderItem.setPrice_unit(product.getPrice());
				orderItem.setPrice_total(
						BigDecimal.valueOf(orderItem.getQuantity()).multiply(orderItem.getPrice_unit()));
				getOrderItemDAO().saveOrUpdate(orderItem);
			}
			return newOrder != null;// true: ok
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public boolean delete(Integer key) {
		try {
			Order order = getOrderDAO().findById(key);
			return delete(order);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

}
