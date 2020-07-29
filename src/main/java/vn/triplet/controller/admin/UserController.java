package vn.triplet.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.model.User;
import vn.triplet.model.User.Role;
import vn.triplet.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = { "", "/" })
	public String index(Model model) {
		List<User> users = userService.loadUsers();
		model.addAttribute("users", users);
		return "views/admin/user/users";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		User user = userService.findById(id);
		if (user == null)
			return "views/admin/error/404";
		model.addAttribute("user", user);
		return "views/admin/user/user";

	}

	@GetMapping(value = "/{id}/delete")
	public String deleteUser(@PathVariable("id") Integer id, final RedirectAttributes redirectAttributes) {
		User user = userService.findById(id);
		String typeCss = "error";
		String message = "User not found!";
		if (user == null) {
			redirectAttributes.addFlashAttribute("css", typeCss);
			redirectAttributes.addFlashAttribute("msg", message);
			return "redirect:/admin/users";
		}
		typeCss = "error";
		message = "Fail to delete user!";
		if (userService.deleteUser(id)) {
			typeCss = "success";
			message = "User is deleted!";
		}
		redirectAttributes.addFlashAttribute("css", typeCss);
		redirectAttributes.addFlashAttribute("msg", message);
		return "redirect:/admin/users";

	}

	@GetMapping(value = "/add")
	public String newStudent(Model model) {
		User user = new User();
		user.setRole(Role.USER);
		model.addAttribute("userForm", user);
		model.addAttribute("status", "add");
		return "views/admin/user/user-form";
	}

	@RequestMapping(value = "/saveupdate")
	public String saveOrUpdate(@ModelAttribute("userForm") User user, Model model,
			final RedirectAttributes redirectAttributes) {
		String typeCss = "error";
		String message = "Fail to create user! Maybe new email is existed!";
		if (userService.saveOrUpdate(user) == null) {
			redirectAttributes.addFlashAttribute("css", typeCss);
			redirectAttributes.addFlashAttribute("msg", message);
			return "redirect:/admin/users";
		}
		typeCss = "success";
		message = "User is created successfully!!";
		redirectAttributes.addFlashAttribute("css", typeCss);
		redirectAttributes.addFlashAttribute("msg", message);
		return "redirect:/admin/users";
	}

	@GetMapping(value = "/{id}/edit")
	public String editUser(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		String typeCss = "error";
		String message = "User not found!";
		User user = userService.findById(id);
		if (user != null) {
			model.addAttribute("userForm", user);
			model.addAttribute("status", "update");
			return "views/admin/user/user-form";
		}
		redirectAttributes.addFlashAttribute("css", typeCss);
		redirectAttributes.addFlashAttribute("msg", message);
		return "redirect:/admin/users";

	}

}