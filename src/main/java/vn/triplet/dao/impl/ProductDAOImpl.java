package vn.triplet.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import vn.triplet.dao.GenericDAO;
import vn.triplet.dao.ProductDAO;
import vn.triplet.model.Product;

public class ProductDAOImpl extends GenericDAO<Integer, Product> implements ProductDAO{

	private static final Logger logger = Logger.getLogger(ProductDAOImpl.class);
	
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
					"WHERE image LIKE '%image-" + gender + "-%') " +
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
	
}
