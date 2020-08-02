package vn.triplet.controller.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.cj.protocol.Message;

import vn.triplet.helper.Converter;
import vn.triplet.model.Product;
import vn.triplet.service.ProductService;

@PropertySource("classpath:messages.properties")
@Controller(value = "products")
@RequestMapping(value = "products")
public class ProductController {
	
	@Value("${messages.product.null}")
	private String product_null;
	
	@Autowired
	private ProductService productService;
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "{id}")
	public String showProductDetails(
			@PathVariable int id,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			HttpSession session,
			Model model) {
		Product product = productService.findById(id);
		if(product == null) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", product_null);
			
			return "redirect:/";
		}
		List<Product> similarProducts = productService.loadProductWithCategoryId(product.getCategory().getId(), product.getId());
		LinkedList<Integer> recentlyViewedProductIds = (LinkedList<Integer>)session.getAttribute("recentlyViewedProductIds");
		List<Product> recentlyViewedProducts = productService.loadProductWithListProductId((List<Integer>)recentlyViewedProductIds);
		
		model.addAttribute("product", product);
		model.addAttribute("similarProducts", similarProducts);
		model.addAttribute("recentlyViewedProducts", recentlyViewedProducts);
		
		LinkedList<Integer> nextRecentlyViewedProductIds = getNextRecentlyViewedProductIds(recentlyViewedProductIds, product.getId());
		session.setAttribute("recentlyViewedProductIds", nextRecentlyViewedProductIds);
		return "views/web/single-product/product";
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private LinkedList<Integer> getNextRecentlyViewedProductIds(LinkedList<Integer> recentlyViewedProductIds, int productId) {
		LinkedList<Integer> nextRecentlyViewedProductIds = null;
		if(recentlyViewedProductIds == null) {
			nextRecentlyViewedProductIds = new LinkedList<Integer>();
		}
		else {
			nextRecentlyViewedProductIds = (LinkedList<Integer>) recentlyViewedProductIds.clone();
		}
		return addProductIdIntoNextRecentlyViewedProductIds(nextRecentlyViewedProductIds, productId);
		
	}
	
	@SuppressWarnings("unused")
	private LinkedList<Integer> addProductIdIntoNextRecentlyViewedProductIds(LinkedList<Integer> nextRecentlyViewedProductIds, int productId) {
		for(Integer id : nextRecentlyViewedProductIds) {
			if(id == productId) {
				nextRecentlyViewedProductIds.remove(id);
				break;
			}
		}
		nextRecentlyViewedProductIds.addFirst(productId);
		
		return nextRecentlyViewedProductIds;
	}
	
}
