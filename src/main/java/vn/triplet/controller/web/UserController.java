package vn.triplet.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.bean.UserInfo;
import vn.triplet.model.User;
import vn.triplet.service.UserService;

@Controller(value = "user")
public class UserController{
	
	@Autowired
	private UserService userService;
	
	private static final int ADMIN = 0;
	private static final int USER = 1;
	@RequestMapping("/profile")
	public String redering(Model model) {
		return "views/web/profile";
	}
	
	@RequestMapping("/welcome")
	public String welcome(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpServletRequest request, Model model,final RedirectAttributes redirectAttributes ){
		User user= userService.findByEmailAndPassword(email, password.trim());
		if(user!=null)
		{
			HttpSession session = request.getSession();
			redirectAttributes.addFlashAttribute("msg",user.getName().toUpperCase());
			session.setAttribute("currentUser", user.getId());
			if (user.getRole().toString().equals("ADMIN"))
			{
				session.setAttribute("roleUser", ADMIN);
			}	
			else
			{
				session.setAttribute("roleUser", USER);
			}
				
			return "redirect:/";
		}
		else 
		{
			model.addAttribute("error", "Invalid Your Email and Password");
		}
		return "views/web/layout/signin-modal";
	}
	
	@RequestMapping("/registerProcess")
	public String register(@ModelAttribute("userInfo") UserInfo userInfo, BindingResult result, Model model)
	{
		
		return "";
	}
	
	
	
	
}
