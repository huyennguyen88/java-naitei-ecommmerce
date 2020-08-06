package vn.triplet.service;

import java.util.List;

import vn.triplet.model.Category;

public interface CategoryService extends BaseService<Integer, Category>{
		
	public List<Category> loadCategoryWithParentId(int parentId);
		
	List<Category> loadCategoriesChildrenIfHasOrLoadSameParentCategories(int categoryId);

}
