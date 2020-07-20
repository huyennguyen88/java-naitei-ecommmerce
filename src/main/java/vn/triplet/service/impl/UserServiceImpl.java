package vn.triplet.service.impl;

import java.io.Serializable;
import java.util.List;

import vn.triplet.model.User;
import vn.triplet.service.UserService;

public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Override
	public User findById(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveOrUpdate(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(User entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> loadUsers() {
		return getUserDAO().loadUsers();
	}

}
