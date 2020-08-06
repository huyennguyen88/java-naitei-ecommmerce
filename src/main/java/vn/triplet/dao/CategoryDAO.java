package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.Category;

public interface CategoryDAO extends BaseDAO<Integer, Category> {
	
	public List<Category> loadCategoryWithParentId(int parentId);
	
	List<Category> loadCategoriesChildrenIfHasOrLoadSameParentCategories(int categoryId);
				
}
