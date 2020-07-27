package vn.triplet.service;

import java.util.List;

import vn.triplet.model.User;

public interface UserService extends BaseService<Integer, User>{
	
	List<User> loadUsers();
	
}
