package vn.triplet.controller.web;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.triplet.helper.Constant;
import vn.triplet.helper.Getter;
import vn.triplet.model.Category;
import vn.triplet.model.Product;
import vn.triplet.service.CategoryService;
import vn.triplet.service.ProductService;

@Controller(value = "categories")
@RequestMapping(value = "categories")
public class CategoryController {

	@Value("${messages.product.null}")
	private String product_null;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/{id}/products") 
	public String category(
			@PathVariable int id,
			@RequestParam(name = "name", required = false) String productName,
			@RequestParam(name = "rating", required = false) Integer rating,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "fromprice", required = false) Integer fromprice,
			@RequestParam(name = "toprice", required = false) Integer toprice,
			HttpSession session,
			Model model) {
		
		
		List<Category> categories = categoryService.loadCategoriesChildrenIfHasOrLoadSameParentCategories(id);
		List<Object> objects = productService.loadProductWithFilter(id, productName, fromprice, toprice, rating, page);
		List<Product> products = (List<Product>)objects.get(0);
		int sumProductOfCategory = (int)objects.get(1);
		if (sumProductOfCategory == Constant.NONE_PRODUCT) {
			model.addAttribute("css", "warning");
			model.addAttribute("msg", product_null);
		}
		List<Product> recentlyViewedProducts = productService
				.loadProductWithListProductId((List<Integer>) session.getAttribute("recentlyViewedProductIds"));
		List<ImmutablePair<Integer, String>> recentlyPathSelected = Getter.getRecentlyPathSelected(categoryService.findById(id));

		for(ImmutablePair<Integer, String> r : recentlyPathSelected)
			System.out.println(r.getValue() + "-");
		
		model.addAttribute("categories", categories);
		model.addAttribute("sumProductOfCategory", sumProductOfCategory);
		model.addAttribute("products", products);
		model.addAttribute("recentlyViewedProducts", recentlyViewedProducts);
		model.addAttribute("recentlyPathSelected", recentlyPathSelected);
		System.out.println(products.size());

		return "views/web/product/products"; 
	}
	
	
}
