package vn.triplet.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.model.User;
import vn.triplet.service.UserService;

public abstract class BaseController {

	@Autowired
	private UserService userService;

	User loadCurrentUser(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Object userId =  session.getAttribute("currentUser");
		if(userId!=null) {
			User user = userService.findById((int) userId);
			if (user != null) return user;
		}
		return null;
	}
}
