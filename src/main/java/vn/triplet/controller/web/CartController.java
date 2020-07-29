package vn.triplet.controller.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.bean.CartItem;
import vn.triplet.bean.ProductInfo;
import vn.triplet.model.Product;
import vn.triplet.service.OrderItemService;
import vn.triplet.service.ProductService;
import java.util.List;

@PropertySource("classpath:messages.properties")
@Controller(value = "cartControllerofWeb")
@RequestMapping("cart")
public class CartController {
	private static final Logger logger = Logger.getLogger(CartController.class);
	@Autowired
	ProductService productService;
	int cartSize;
	@Value("${add_item_success}")
	private String add_item_success;
	@Value("${mess_reload}")
	private String mess_reload;
	@Value("${product_not_exist}")
	private String product_not_exist;

	@GetMapping
	public String index(HttpSession session, Model model) {
		logger.info("cart index");
		List<CartItem> items = new ArrayList<CartItem>();
		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
		if (cart != null) {
			java.util.Set<Integer> keySet = cart.keySet();
			for (Integer key : keySet) {
				ProductInfo product = new ProductInfo(productService.findById(key));
				items.add(new CartItem(cart.get(key), product.getPrice(), product.getImage(), product.getName(), key));
			}
			model.addAttribute("orderitems", items);
			return "views/web/carts/index";
		}
		return "redirect:/products";

	}

	@GetMapping("/add")
	public String add(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity,
			HttpSession session, final RedirectAttributes redirectAttributes, Model model) {
		logger.info("add items");
		ProductInfo product = new ProductInfo(productService.findById(productId));
		if (product == null) {
			redirectAttributes.addFlashAttribute("product_not_exist", product_not_exist);
			return "redirect:/products";
		}
		HashMap<Integer, Integer> cart = new HashMap<Integer, Integer>();
		if (session.getAttribute("cart") == null) {
			cart.put(productId, quantity);
		} else {
			cart = (HashMap<Integer, Integer>) session.getAttribute("cart");

			if (this.isExistItemInCart(cart, productId)) {
				cart.put(productId, quantity);
			} else {
				cart.put(productId, quantity);
			}
		}
		
		redirectAttributes.addFlashAttribute("add_item_success", add_item_success);
		setAttr(session,cart);
		return "redirect:/cart";
	}

	@GetMapping(value = "{productId}")
	public String removeItem(@PathVariable Integer productId, HttpSession session, Model model) {
		logger.info("remove items");

		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
		if (cart == null) {
			model.addAttribute("mess_reload", mess_reload);
			return "redirect:/cart";
		}
		cart.remove(productId);
		setAttr(session,cart);
		return "redirect:/cart";

	}

	@GetMapping(value = "/update/{productId}")
	public String updateQuantity(@PathVariable Integer productId, HttpSession session,
			@RequestParam(name = "quantity", required = false) Integer quantity,
			@RequestParam(name = "quantityIncDec", required = false) Integer quantityIncDec) {
		logger.info("update quantity of items");

		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
		// change quantity on input
		if (quantity != null) {
			if (quantity == 0) {
				cart.remove(productId);

			} else {
				cart.put(productId, quantity);
			}
		}
		// click +/- button
		if (quantityIncDec != null) {

			if (quantityIncDec == 1) {
				cart.replace(productId, quantity + 1);
			} else {
				if (cart.get(productId) == 1) {
					cart.remove(productId);

				} else
					cart.replace(productId, quantity - 1);
			}

		}
		setAttr(session,cart);
		return "redirect:/cart";
	}

	private double totalCart(HashMap<Integer, Integer> cart) {
		double total = 0;
		java.util.Set<Integer> keySet = cart.keySet();
		for (Integer key : keySet) {
			Product product = productService.findById(key);
			logger.info(cart.get(key));
			total += product.getPrice().doubleValue() * cart.get(key);
		}
		return total;
	}

	private boolean isExistItemInCart(HashMap<Integer, Integer> cart, Integer itemId) {
		java.util.Set<Integer> keySet = cart.keySet();
		for (Integer key : keySet) {
			if (key == itemId)
				return true;
		}
		return false;
	}
	private void setAttr(HttpSession session,HashMap<Integer, Integer> cart)
	{
		cartSize = cart.size();
		session.setAttribute("cart", cart);
		session.setAttribute("cartSize", cartSize);
		session.setAttribute("totalCart", this.totalCart(cart));

	}

}
