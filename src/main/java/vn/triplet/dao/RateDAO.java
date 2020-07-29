package vn.triplet.dao;

import java.util.List;

import vn.triplet.model.Rate;

public interface RateDAO extends BaseDAO<Integer,Rate>{
	List<Rate> loadRatings(Integer productId);

}
