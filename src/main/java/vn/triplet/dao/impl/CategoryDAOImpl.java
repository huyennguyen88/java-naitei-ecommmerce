package vn.triplet.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import vn.triplet.dao.CategoryDAO;
import vn.triplet.dao.GenericDAO;
import vn.triplet.helper.Constant;
import vn.triplet.model.Category;

public class CategoryDAOImpl extends GenericDAO<Integer, Category> implements CategoryDAO{
	
	private static final Logger logger = Logger.getLogger(ProductDAOImpl.class);

	public CategoryDAOImpl() {
		super(Category.class);
	}
	
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> loadCategoryWithParentId(int parentId) {
		if(parentId == Constant.ROOT_PARENID)
			return getSession().createQuery("from Category where parent_id is null")
					.getResultList();
		else
			return getSession().createQuery("from Category where parent_id =:parentId")
					.setParameter("parentId", parentId)
					.getResultList();
	}

	@Override
	public List<Category> loadCategoriesChildrenIfHasOrLoadSameParentCategories(int categoryId) {
		return (loadCategoryWithParentId(categoryId).size() == Constant.HAS_NOT_CHILDREN )? 
				loadCategoryWithParentId(findById(categoryId).getParent().getId()) : 
				loadCategoryWithParentId(categoryId);
	}


}
