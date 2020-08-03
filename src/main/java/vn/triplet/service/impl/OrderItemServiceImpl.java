package vn.triplet.service.impl;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.dao.OrderItemDAO;
import vn.triplet.dao.ProductDAO;
import vn.triplet.model.OrderItem;
import vn.triplet.service.OrderItemService;

public class OrderItemServiceImpl extends BaseServiceImpl implements OrderItemService {

	private static final Logger logger = Logger.getLogger(OrderItemServiceImpl.class);
	@Autowired
	private OrderItemDAO orderItemDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public OrderItemDAO getOrderItemDAO() {
		return orderItemDAO;
	}

	public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
		this.orderItemDAO = orderItemDAO;
	}

	@Override
	public OrderItem findById(Serializable key) {
		try {
			return getOrderItemDAO().findById(key);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public OrderItem saveOrUpdate(OrderItem entity) {
		try {
			return getOrderItemDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public boolean delete(OrderItem entity) {
		try {
			getOrderItemDAO().delete(entity);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	
}
