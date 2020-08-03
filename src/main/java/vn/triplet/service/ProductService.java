package vn.triplet.service;

import java.util.List;

import vn.triplet.model.Product;

public interface ProductService extends BaseService<Integer, Product>{

	List<Product> loadHotTrendProduct(int gender);
	
	List<Product> loadProductWithCategoryId(int categoryId, int productId);
	
	List<Product> loadProductWithListProductId(List<Integer> ids);

	
}
