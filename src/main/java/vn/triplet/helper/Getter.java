package vn.triplet.helper;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import vn.triplet.model.Category;
import vn.triplet.model.Product;

public class Getter {
	@SuppressWarnings("unchecked")
	public static List<ImmutablePair<Integer, String>> getRecentlyPathSelected(Category category) {
		LinkedList<ImmutablePair<Integer, String>> recentlyPathSelected = new LinkedList<ImmutablePair<Integer, String>>();
		while(category != null) {
			ImmutablePair<Integer, String> pair = new ImmutablePair<>(category.getId(), category.getName());
			recentlyPathSelected.addFirst(pair);
			category = category.getParent();
		}
		return recentlyPathSelected;
	}
	
	public static List<ImmutablePair<Integer, String>> getRecentlyPathSelected(Product product) {
		List<ImmutablePair<Integer, String>> recentlyPathSelected = getRecentlyPathSelected(product.getCategory());
		ImmutablePair<Integer, String> pair = new ImmutablePair<>(product.getId(), product.getName());
		recentlyPathSelected.add(pair);
		return recentlyPathSelected;
	}
}

