package vn.triplet.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.dao.ProductDAO;
import vn.triplet.model.Product;
import vn.triplet.service.ProductService;

public class ProductServiceImpl extends BaseServiceImpl implements ProductService{

	@Autowired
	private ProductDAO productDAO;
	
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public Product findById(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product saveOrUpdate(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Product entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> loadHotTrendProduct(int gender) {
		try{
			return productDAO.loadHotTrendProduct(gender);
		} catch (Exception e) {
			return null;
		}
	}


	
	
}
