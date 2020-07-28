package vn.triplet.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping("/login")
	public String login() {
		return "views/web/layout/signin-modal";
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		return "views/web/layout/signup-modal";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("message", "Logout success!");
		session.removeAttribute("currentUser");
		session.invalidate();
		return "views/web/layout/signin-modal";
	}
	
	
}
