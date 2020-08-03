package vn.triplet.controller.web;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.helper.Constant;
import vn.triplet.helper.Converter;
import vn.triplet.model.Category;
import vn.triplet.model.Product;
import vn.triplet.service.CategoryService;
import vn.triplet.model.User;
import vn.triplet.service.ProductService;
import vn.triplet.bean.UserInfo;
import vn.triplet.service.UserService;
import vn.triplet.validate.UserValidation;
import vn.triplet.bean.UserInfo;
import vn.triplet.model.User;
import vn.triplet.service.UserService;
import vn.triplet.validate.UserValidation;

@PropertySource("classpath:messages.properties")
@Controller(value = "home")
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;

	@Value("${messages.login}")
	private String msg_login;

	@Value("${messages.error_mail}")
	private String msg_error_mail;

	@Value("${messages.register}")
	private String msg_register;

	@Value("${Login_error}")
	private String Login_error;

	private static final int ADMIN = 0;
	private static final int USER = 1;

	@SuppressWarnings("unchecked")
	@RequestMapping("/")
	public String index(
			HttpSession session, 
			Model model) {
		logger.info("home page");
		List<Product> womenProducts = productService.loadHotTrendProduct(Constant.WOMAN_PRODUCTS);
		List<Product> menProducts = productService.loadHotTrendProduct(Constant.MAN_PRODUCTS);
		List<Product> recentlyViewedProducts = productService
				.loadProductWithListProductId((List<Integer>) session.getAttribute("recentlyViewedProductIds"));

		model.addAttribute("productsWomen", womenProducts);
		model.addAttribute("productsMen", menProducts);
		model.addAttribute("recentlyViewedProducts", recentlyViewedProducts);
		
		List<Category> rootCategories = categoryService.loadCategoryWithParentId(Constant.ROOT_PARENID);
		session.setAttribute("rootCategories", rootCategories);
		

		return "views/web/home/index";
	}

	@RequestMapping("/login")
	public String login() {
		return "views/web/layout/signin-modal";
	}

	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserInfo());
		return "views/web/layout/signup-modal";
	}

	@RequestMapping("/welcome")
	public String welcome(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpServletRequest request, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("login");
		User user = userService.findByEmailAndPassword(email, password.trim());
		if (user != null) {
			HttpSession session = request.getSession();
			redirectAttributes.addFlashAttribute("loginsuccess", msg_login);
			session.setAttribute("msg", user.getName().toUpperCase());
			session.setAttribute("currentUser", user.getId());
			
			if (user.getRole().toString().equals("ADMIN")) {
				session.setAttribute("roleUser", ADMIN);
				return "redirect:/dashboard";
			}
			session.setAttribute("roleUser", USER);
			
			if (session.getAttribute("cart") != null)
				return "redirect:/cart/";
			return "redirect:/";
		}
		model.addAttribute("error", Login_error);
		return "views/web/layout/signin-modal";
	}

	@RequestMapping("/registerProcess")
	public String register(@ModelAttribute("user") UserInfo userInfo, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.info("register");
		UserValidation userVali = new UserValidation();
		userVali.validate(userInfo, result);
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			return "views/web/layout/signup-modal";
		} else if (userService.createUser(userInfo.convertToUser()) == false) {
			model.addAttribute("error", msg_error_mail);
			return "views/web/layout/signup-modal";
		}
		userService.createUser(userInfo.convertToUser());
		redirectAttributes.addFlashAttribute("registersuccess", msg_register);
		return "views/web/layout/signin-modal";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		logger.info("logout");
		HttpSession session = request.getSession();
		session.setAttribute("message", "Logout success!");
		session.removeAttribute("currentUser");
		session.invalidate();
		return "views/web/layout/signin-modal";
	}

}
