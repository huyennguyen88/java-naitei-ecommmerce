package vn.triplet.service;

import java.util.List;

import vn.triplet.model.User;
import vn.triplet.model.User.Role;

public interface UserService extends BaseService<Integer, User> {

	List<User> loadUsers();

	User findByEmailAndPassword(String usermail, String password);

	boolean checkEmailExist(String email);

	boolean createUser(User user);

	boolean deleteUser(Integer id);
	
	List<User> loadUsers(Role role);
}
