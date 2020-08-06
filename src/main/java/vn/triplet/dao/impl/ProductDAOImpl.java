package vn.triplet.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.SessionFactory;

import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.ProductDAO;
import vn.triplet.helper.Constant;
import vn.triplet.model.Product;

public class ProductDAOImpl extends GenericDAO<Integer, Product> implements ProductDAO{

	public ProductDAOImpl() {
		super(Product.class);
	}
	
	public ProductDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> loadHotTrendProduct(int gender) {
		List<Product> products = new ArrayList<Product>();
		List<Object[]> objects = (List<Object[]>)getSession().createNativeQuery(
				"SELECT products.id, products.name, products.description, products.image, products.quantity, products.price, products.category_id, products.rate_avg " +
				"FROM orderitems " +
				"INNER JOIN products " +
				"ON orderitems.product_id = products.id " +
				"WHERE (products.rate_avg > (select avg(rate_avg) from products) " +
				"AND products.quantity > 0 " +
				"AND products.delete_time is null) " +
				"GROUP BY orderitems.product_id " +
				"HAVING orderitems.product_id " + 
				"IN (SELECT id " + 
					"FROM tripletclothes.products " + 
					"WHERE image LIKE '%image-" + ((gender < 10)? "0" + gender : gender)  + "-%') " +
				"AND SUM(orderitems.quantity) > (SELECT AVG(quantity) " + 
												"FROM (SELECT SUM(quantity) AS 'quantity' " + 
												"FROM orderitems " + 
												"GROUP BY product_id) " + 
												"AS t) " +
				"ORDER BY products.rate_avg DESC, sum(orderitems.quantity) DESC " +
				"LIMIT 10" ).getResultList();
		for(Object[] o : objects) {
			Product p = new Product();
			p.setId((int)o[0]);
			p.setName(o[1].toString().trim());
			p.setDescription(o[2].toString().trim());
			p.setImage(o[3].toString().trim());
			p.setQuantity((int)o[4]);
			p.setPrice(new BigDecimal(o[5].toString().trim()));
//			p.setCategory(getSession().createQuery("from Category where id =" ).getResultList());
			p.setRate_avg((double)o[7]);
			products.add(p);
		}
		return products;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> loadProductWithCategoryId(int categoryId, int productId) {
		if(productId != Constant.NONE_PRODUCT_ID) {
			return getSession().createQuery("FROM Product WHERE id!=:productId AND category_id=:categoryId ORDER BY rate_avg DESC")
					.setParameter("productId", productId)
					.setParameter("categoryId", categoryId)
					.setMaxResults(4)
					.getResultList();
		}
		else if(categoryId == Constant.ROOT_PARENID) {
			return getSession().createQuery("FROM Product ORDER BY rate_avg DESC").getResultList();
		}
		else {
			return getSession().createQuery("FROM Product WHERE image LIKE :likeCategoryId ORDER BY rate_avg DESC")
					.setParameter("likeCategoryId", "%-" + ((categoryId < 10)? "0" + categoryId : categoryId) + "-%")
					.getResultList();
		}
	}

	@Override
	public List<Product> loadProductWithListProductId(List<Integer> ids) {
		List<Product> products = new LinkedList<Product>();
		for(int id : ids) {
			products.add(findById(id));
		}
		return products;
	}

	@Override
	public List<Object> loadProductWithFilter(
			int categoryId,
			String productName, 
			Integer fromprice, 
			Integer toprice,
			Integer rating, 
			Integer page) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Product> cr = builder.createQuery(Product.class);
		Root<Product> root = cr.from(Product.class);
		cr.select(root);
		
		List<Predicate> restrictions = new ArrayList<Predicate>();
		
		/*
		 * get product with ancestor id
		 */
		if(categoryId != Constant.ROOT_PARENID) {
			Predicate imageRestriction = builder.like(root.get("image"), "%-" + ((categoryId < 10)? "0" + categoryId : categoryId) + "-%");
			restrictions.add(imageRestriction);
		}
		
		/*
		 * get product with name
		 */
		if(productName != null) {
			String[] str = productName.split(" ");
			Predicate[] nameRestrictions = new Predicate[str.length];
			int i = 0;
			for(String s : str) {
				Predicate nameRestriction = builder.like(root.get("name"), "% " + s + " %");
				nameRestrictions[i ++] = nameRestriction;
			}
			Predicate allNameRestrictions = builder.or(nameRestrictions) ;
			restrictions.add(allNameRestrictions);
		}
		
		/*
		 * get product with price
		 */
		if(fromprice != null || toprice != null) {
			if(fromprice != null) {
				Predicate frompriceRestriction = builder.greaterThanOrEqualTo(root.get("price"), fromprice);
				restrictions.add(frompriceRestriction);
			}
			if(toprice != null) {
				Predicate topriceRestriction = builder.lessThanOrEqualTo(root.get("price"), toprice);
				restrictions.add(topriceRestriction);
			}
		}
		
		/*
		 * get product with rating
		 */
		if(rating != null) {
			Predicate ratingRestriction = builder.greaterThanOrEqualTo(root.get("rate_avg"), rating);
			restrictions.add(ratingRestriction);
		}
		
		if(restrictions != null) {
			Predicate[] restrictionsArray = new Predicate[restrictions.size()];
			int i = 0;
			for(Predicate restriction : restrictions) {
				restrictionsArray[i ++] = restriction;
			}
			Predicate allRestrictions = builder.and(restrictionsArray);
			cr.where(allRestrictions).orderBy(builder.desc(root.get("rate_avg")));
		}
		
		
		List<Object> objects = new ArrayList<Object>(); 
		objects.add(getSession().createQuery(cr)
					.setFirstResult((page - 1)*16)
					.setMaxResults(16)
					.getResultList());
		objects.add(getSession().createQuery(cr).getResultList().size());
		
		return objects;
	}
	
	
	
}
