package vn.triplet.controller.web;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.triplet.helper.Constant;
import vn.triplet.helper.Converter;
import vn.triplet.model.Product;
import vn.triplet.service.ProductService;
import vn.triplet.bean.UserInfo;
import vn.triplet.service.UserService;

@Controller(value = "home")
public class HomeController {

	@Autowired
	private ProductService productService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/")
	public String index(
			HttpSession session,
			Model model) {
		List<Product> womenProducts = productService.loadHotTrendProduct(Constant.WOMAN_PRODUCTS);
		List<Product> menProducts = productService.loadHotTrendProduct(Constant.MAN_PRODUCTS);
		List<Product> recentlyViewedProducts = productService.loadProductWithListProductId((List<Integer>) session.getAttribute("recentlyViewedProductIds"));
		
		model.addAttribute("productsWomen", womenProducts);
		model.addAttribute("productsMen", menProducts);
		model.addAttribute("recentlyViewedProducts", recentlyViewedProducts);
		
		return "views/web/home/index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "views/web/layout/signin-modal";
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user",new UserInfo());
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
