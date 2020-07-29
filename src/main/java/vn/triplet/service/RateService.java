package vn.triplet.service;
import java.util.List;

import vn.triplet.bean.RateInfo;
import vn.triplet.model.Product;
import vn.triplet.model.Rate;
public interface RateService extends BaseService<Integer,Rate>{
	List<Rate> loadRatings(Integer productId);
	boolean createReview(RateInfo rate,Product product,Integer userId);

}
