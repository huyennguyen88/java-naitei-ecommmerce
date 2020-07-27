package vn.triplet.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.triplet.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	public UserService userService;

	@RequestMapping("/")
	public String rendering() {
		logger.info("home page");
		userService.loadUsers();
		return "views/index";
	}

}
