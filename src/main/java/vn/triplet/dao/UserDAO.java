package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.Order;
import vn.triplet.model.User;

public interface UserDAO extends BaseDAO<Integer, User> {

	List<User> loadUsers();

	User findByEmail(String email);

	boolean checkEmailExist(String email);
	
}
