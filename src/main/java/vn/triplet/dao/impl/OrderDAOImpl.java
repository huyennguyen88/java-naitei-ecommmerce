package vn.triplet.dao.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.OrderDAO;
import vn.triplet.model.Order;
import vn.triplet.model.User;

public class OrderDAOImpl extends GenericDAO<Integer, Order> implements OrderDAO {

	Logger logger = Logger.getLogger(OrderDAOImpl.class);
	@Override
	public void delete(Order entity) {
		// TODO Auto-generated method stub
		
	}
	public OrderDAOImpl() {
		super(Order.class);
	}

	public OrderDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	

}
