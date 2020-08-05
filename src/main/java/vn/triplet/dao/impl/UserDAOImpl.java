package vn.triplet.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.UserDAO;
import vn.triplet.model.Order;
import vn.triplet.model.User;

public class UserDAOImpl extends GenericDAO<Integer, User> implements UserDAO {
	Logger logger = Logger.getLogger(UserDAOImpl.class);

	public UserDAOImpl() {
		super(User.class);
	}

	public UserDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<User> loadUsers() {
		return getSession().createQuery("from User").getResultList();
	}

	@Override
	public User findByEmail(String email) {
		return (User) getSession().createQuery("from User where email=: email").setParameter("email", email)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkEmailExist(String email) {
		User user = (User) getSession().createQuery("from User where email=: email").setParameter("email", email)
				.uniqueResult();
		if (user != null)
			return true;
		return false;
	}
	
}
