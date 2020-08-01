package vn.triplet.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.model.User;
import vn.triplet.service.UserService;

@PropertySource("classpath:messages.properties")
@Controller(value = "users")
@RequestMapping("/users")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Value("${messages.notAlow}")
	private String access_error;

	@Value("${messages.notFound}")
	private String not_found;

	@Value("${user.update.fail}")
	private String update_fail;

	@Value("${user.update.success}")
	private String update_success;

	@RequestMapping("/{id}")
	public String redering(@PathVariable("id") int id, Model model, HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		
		User user = loadCurrentUser(request);
		if (user != null) {
			model.addAttribute("user", user);
			return "views/web/user/user";
		}
		redirectAttributes.addFlashAttribute("msg", access_error);
		return "redirect:/";
	}

	@RequestMapping(value = "/users")
	public String saveOrUpdate(@ModelAttribute("user") User user, Model model,
			final RedirectAttributes redirectAttributes) {
		String typeCss = "error";
		String message = update_fail;

		if (userService.saveOrUpdate(user) != null) {
			message = update_success;
			typeCss = "success";
		}
		redirectAttributes.addFlashAttribute("css", typeCss);
		redirectAttributes.addFlashAttribute("msg", message);
		return "redirect:/users/" + user.getId();
	}

	@RequestMapping("/{id}/orders")
	public String AllOrdersOfUser(@PathVariable("id") int id, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		User user = loadCurrentUser(request);
		if(user==null) {
			redirectAttributes.addFlashAttribute("msg", not_found);
			return "redirect:/";
		}
		model.addAttribute("user", user);
		return "views/web/user/orders";
	}


}
