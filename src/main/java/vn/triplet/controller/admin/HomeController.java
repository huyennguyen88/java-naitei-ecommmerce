package vn.triplet.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "homeControllerOfAmin")
public class HomeController {
	@RequestMapping("/admin")
	public String index() {
		return "views/admin/home/index";
	}
}
