package vn.triplet.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.RateDAO;
import vn.triplet.model.Rate;

public class RateDAOImpl extends GenericDAO<Integer, Rate> implements RateDAO {
	Logger logger = Logger.getLogger(RateDAOImpl.class);

	public RateDAOImpl() {
		super(Rate.class);
	}

	public RateDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rate> loadRatings(Integer productId) {

		return getSession().createQuery("from Rate where product.id=:productId").setParameter("productId", productId)
				.getResultList();
	}

}
