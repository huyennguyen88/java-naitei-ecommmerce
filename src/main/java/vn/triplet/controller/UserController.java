package vn.triplet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class UserController {
	
	@RequestMapping(value = "/")
	public String rendering() { 
		return "views/index";
	}
	
}
