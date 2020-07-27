package vn.triplet.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.triplet.service.UserService;

@Controller(value = "homeControllerOfWeb")
public class HomeController {
	@Autowired
	public UserService userService;

	@RequestMapping("/")
	public String index() {
		return "views/web/home/index";
	}
}
