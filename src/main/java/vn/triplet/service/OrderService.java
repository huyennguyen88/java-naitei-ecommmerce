package vn.triplet.service;

import java.util.List;
import java.util.HashMap;

import vn.triplet.model.Order;
import vn.triplet.model.User;

public interface OrderService extends BaseService<Integer, Order> {
	boolean delete(Integer key);

	boolean createOrder(Order order,HashMap<Integer,Integer> items,User user);
	
	List<Order> loadOrders();
	
	List<Order> loadOrdersByStatus(int stt);

}
