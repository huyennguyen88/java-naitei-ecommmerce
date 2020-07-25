package vn.triplet.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.triplet.service.impl.UserServiceImpl;
import vn.triplet.model.User;
import vn.triplet.service.UserService;
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

	@Override
	public User findByEmailAndPassword(String usermail, String password) {
		try {
			User user= getUserDAO().findByEmail(usermail.trim());
			logger.info(user);
			//if(passwordEncoder.matches(password,user.getPassword_digest()))
			if(password.equals(user.getPassword_digest()))
			{
				return user;
			}
				return null;
			
		}catch(Exception e)
		{
			return null;
		}
			

	}

}
