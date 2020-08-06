package vn.triplet.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import vn.triplet.model.Product;

public interface ProductDAO extends BaseDAO<Integer, Product> {
	
	List<Product> loadHotTrendProduct(int gender);
	
	List<Product> loadProductWithCategoryId(int categoryId, int productId);
	
	List<Product> loadProductWithListProductId(List<Integer> ids);
	
	List<Object> loadProductWithFilter(int categoryId, String productName, Integer fromprice, Integer toprice, Integer rating, Integer page);

	
}
