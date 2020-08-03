
package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.Order;
import vn.triplet.model.Order.Status;

public interface OrderDAO extends BaseDAO<Integer, Order> {
	List<Order> loadOrders();
	
	List<Order> loadOrdersByStatus(Status status);
	
}
