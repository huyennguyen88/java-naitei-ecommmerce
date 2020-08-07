package vn.triplet.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.OrderDAO;
import vn.triplet.model.Order;
import vn.triplet.model.Order.Status;

public class OrderDAOImpl extends GenericDAO<Integer, Order> implements OrderDAO {

	Logger logger = Logger.getLogger(OrderDAOImpl.class);

	public OrderDAOImpl() {
		super(Order.class);
	}

	public OrderDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> loadOrders() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from Order").getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> loadOrdersByStatus(Status status) {
		return (List<Order>) getSession().createQuery("FROM Order where status =: status ")
				.setParameter("status", status).getResultList();
	}

	@Override
	public BigDecimal getTotalRevenue() {
		return (BigDecimal) getSession()
				.createQuery("SELECT SUM(total) FROM Order " + "WHERE status =: status ")
				.setParameter("status", Status.ACCEPTED).uniqueResult();
	}

}
