package vn.triplet.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.dao.CategoryDAO;
import vn.triplet.model.Category;
import vn.triplet.service.CategoryService;

public class CategoryServiceImpl implements CategoryService{

	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public Category findById(Serializable key) {
		try {
			return categoryDAO.findById(key);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Category saveOrUpdate(Category entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Category entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Category> loadCategoryWithParentId(int parentId) {
		try {
			return categoryDAO.loadCategoryWithParentId(parentId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public List<Category> loadCategoriesChildrenIfHasOrLoadSameParentCategories(int categoryId) {
		try {
			return categoryDAO.loadCategoriesChildrenIfHasOrLoadSameParentCategories(categoryId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	
	
}
