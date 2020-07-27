package vn.triplet.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ShopController {
	
	@RequestMapping("/shop")
	public String shop() {
		return "views/web/shop/shop";
	}
	
	@RequestMapping("/single")
	public String single() {
		return "views/web/single-product/single-product";
	}
}
