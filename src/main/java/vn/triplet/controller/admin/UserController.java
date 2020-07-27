package vn.triplet.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("/admin/users")
	public String index() {
		return "views/admin/user/users";
	}

}