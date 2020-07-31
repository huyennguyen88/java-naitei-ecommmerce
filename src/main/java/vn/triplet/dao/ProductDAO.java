package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.Product;

public interface ProductDAO extends BaseDAO<Integer, Product> {
	
	List<Product> loadHotTrendProduct(int gender);
	
	List<Product> loadProductWithCategoryID(int categoryId, int productId);
		
}
