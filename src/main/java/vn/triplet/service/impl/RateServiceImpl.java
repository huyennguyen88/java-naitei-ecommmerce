package vn.triplet.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.bean.RateInfo;
import vn.triplet.dao.ProductDAO;
import vn.triplet.dao.RateDAO;
import vn.triplet.model.Product;
import vn.triplet.model.Rate;
import vn.triplet.model.User;
import vn.triplet.model.Rate.Value;
import vn.triplet.service.RateService;

public class RateServiceImpl extends BaseServiceImpl implements RateService {

	private static final Logger logger = Logger.getLogger(RateServiceImpl.class);

	@Autowired
	private RateDAO rateDAO;

	public RateDAO getRateDAO() {
		return rateDAO;
	}

	public void setRateDAO(RateDAO rateDAO) {
		this.rateDAO = rateDAO;
	}

	@Override
	public Rate saveOrUpdate(Rate entity) {
		try {
			return getRateDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public List<Rate> loadRatings(Integer productId) {
		return getRateDAO().loadRatings(productId);
	}

	@Override
	public Rate findById(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Rate entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createReview(RateInfo rate, Product product,Integer userId) {
		try {
			logger.info("save service");
			User user = getUserDAO().findById(userId);
			Rate newRating = new Rate();
			newRating.setUser(user);
			newRating.setProduct(product);
			newRating.setContent(rate.getContent());
			setValueRate(rate, newRating);
			return getRateDAO().saveOrUpdate(newRating) != null;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	private void setValueRate(RateInfo rating, Rate newRating) {
		switch (rating.getValue()) {
		case 1: {
			newRating.setValue(Value.ONE);
			break;
		}
		case 2: {
			newRating.setValue(Value.TWO);
			break;
		}
		case 3: {
			newRating.setValue(Value.THREE);
			break;
		}
		case 4: {
			newRating.setValue(Value.FOUR);
			break;
		}
		case 5: {
			newRating.setValue(Value.FIVE);
			break;
		}
		}
	}

}
