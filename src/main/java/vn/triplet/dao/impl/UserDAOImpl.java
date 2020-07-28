package vn.triplet.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.UserDAO;
import vn.triplet.model.User;

public class UserDAOImpl extends GenericDAO<Integer, User> implements UserDAO {
	Logger logger = Logger.getLogger(UserDAOImpl.class);
	public UserDAOImpl() {
		super(User.class);
	}

	public UserDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public User saveOrUpdate(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> loadUsers() {
		return getSession().createQuery("from User").getResultList();
	}

	@Override
	public User findByEmail(String email) {
		return (User)getSession().createQuery("from User where email=: email")
				.setParameter("email", email).uniqueResult();
	}
}
