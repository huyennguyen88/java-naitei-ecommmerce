package vn.triplet.service;

import java.util.List;

import vn.triplet.model.Order;
import vn.triplet.model.User;

public interface UserService extends BaseService<Integer, User> {

	List<User> loadUsers();

	User findByEmailAndPassword(String usermail, String password);

	boolean checkEmailExist(String email);

	boolean createUser(User user);

	boolean deleteUser(Integer id);
	
	
}
