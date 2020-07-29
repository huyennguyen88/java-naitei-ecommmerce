package vn.triplet.controller.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mchange.v2.cfg.PropertiesConfigSource.Parse;

import vn.triplet.bean.CartItem;
import vn.triplet.bean.ProductInfo;
import vn.triplet.model.Order;
import vn.triplet.model.User;
import vn.triplet.service.OrderItemService;
import vn.triplet.service.OrderService;
import vn.triplet.service.ProductService;
import vn.triplet.service.UserService;

@PropertySource("classpath:messages.properties")
@Controller
@RequestMapping("orders")
public class OrderController {
	@Autowired
	ProductService productService;
	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@Value("${cart_empty}")
	private String cart_empty;
	
	@Value("${order_success}")
	private String order_success;
	
	private static final Logger logger = Logger.getLogger(OrderController.class);

	@GetMapping("/add")
	public String newOrder(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {
		List<CartItem> items=new ArrayList<CartItem>();
		HashMap<Integer, Integer> cart=(HashMap<Integer, Integer>) session.getAttribute("cart");
		java.util.Set<Integer> keySet = cart.keySet();
		for (Integer key : keySet) {
			ProductInfo product = new ProductInfo(productService.findById(key));
			items.add(new CartItem(cart.get(key),product.getPrice(),product.getImage(),product.getName(),key));
		}
		model.addAttribute("orderitems",items);
		if (cart == null || cart.isEmpty() == true) {
			redirectAttributes.addFlashAttribute("cart_empty", cart_empty);
			return "redirect:/cart";
		}
		if (session.getAttribute("currentUser") == null)
			return "redirect:/login";
		Order order = new Order();
		model.addAttribute("order", order);
		return "views/web/orders/new";
	}

	@PostMapping("/save")
	public String saveOrUpdate(@ModelAttribute("order") Order order, BindingResult result, Model model,
			HttpSession session,final RedirectAttributes redirectAttributes) {
		logger.info("save order");
		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
		User user = userService.findById(Integer.parseInt(session.getAttribute("currentUser").toString()));
	
		double total =(double) session.getAttribute("totalCart");
		order.setTotal(BigDecimal.valueOf((double)session.getAttribute("totalCart")));
		if(orderService.createOrder(order,cart, user))
		{
			redirectAttributes.addFlashAttribute("order_success",order_success);
			session.removeAttribute("cart");
			session.removeAttribute("cartSize");
			session.removeAttribute("totalCart");		
		}
		return "redirect:/";
	}

}
