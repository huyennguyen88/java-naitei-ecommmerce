package vn.triplet.service;

import java.util.List;

import vn.triplet.model.Product;

public interface ProductService extends BaseService<Integer, Product>{

	List<Product> loadHotTrendProduct(int gender);
	
	List<Product> loadProductWithCategoryID(int categoryId, int productId);
	
}
