package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.Product;

public interface ProductDAO extends BaseDAO<Integer, Product> {
	
	List<Product> loadHotTrendProduct(int gender);
	
	List<Product> loadProductWithCategoryId(int categoryId, int productId);
	
	List<Product> loadProductWithListProductId(List<Integer> ids);
		
}
