package vn.triplet.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.OrderItemDAO;
import vn.triplet.model.OrderItem;

public class OrderItemDAOImpl extends GenericDAO<Integer, OrderItem> implements OrderItemDAO {
	Logger logger = Logger.getLogger(OrderItemDAOImpl.class);
	@Override
	public void delete(OrderItem entity) {
		// TODO Auto-generated method stub
		
	}
	public OrderItemDAOImpl() {
		super(OrderItem.class);
	}

	public OrderItemDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
}
