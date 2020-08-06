package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.User;
import vn.triplet.model.User.Role;

public interface UserDAO extends BaseDAO<Integer, User> {

	List<User> loadUsers();

	User findByEmail(String email);

	boolean checkEmailExist(String email);
	
	List<User> loadUsers(Role role);
	
}
