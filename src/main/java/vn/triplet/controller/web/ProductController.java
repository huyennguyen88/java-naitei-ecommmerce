package vn.triplet.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	@GetMapping(value = "{id}")
	public String showProductDetails(
			@PathVariable int id,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			Model model) {
		Product product = productService.findById(id);
		if(product == null) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", product_null);
			
			return "redirect:/";
		}
		List<Product> similarProducts = productService.loadProductWithCategoryID(product.getCategory().getId(), product.getId());
		
		model.addAttribute("product", product);
		model.addAttribute("similarProducts", similarProducts);
		
		return "views/web/single-product/product";
	}

}
