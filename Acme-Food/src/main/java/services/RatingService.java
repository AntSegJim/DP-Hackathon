
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RatingRepository;
import security.LoginService;
import domain.Rating;
import domain.Restaurant;

@Service
@Transactional
public class RatingService {

	@Autowired
	private RatingRepository	ratingRepository;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private Validator			validator;


	public Rating create() {
		final Rating rating = new Rating();
		rating.setComment("");
		rating.setValoration(0);
		rating.setRestaurant(new Restaurant());
		return rating;
	}

	public Rating findOne(final int ratingId) {
		return this.ratingRepository.findOne(ratingId);
	}

	public Collection<Rating> findAll() {
		return this.ratingRepository.findAll();
	}

	public Collection<Rating> getAllMyRatings() {
		return this.ratingRepository.getAllMyRatings(this.customerService.getCustomerUserAccount(LoginService.getPrincipal().getId()).getId());
	}

	public Rating save(final Rating r) {
		Rating saved;
		if (r.getId() == 0)
			saved = this.ratingRepository.save(r);
		else {
			final Rating savedRating = this.ratingRepository.findOne(r.getId());
			savedRating.setComment(r.getComment());
			savedRating.setValoration(r.getValoration());
			saved = this.ratingRepository.save(savedRating);
		}
		return saved;
	}

	//RECONSTRUCT
	public Rating reconstruct(final Rating rating, final BindingResult binding) {
		Rating res;
		if (rating.getId() == 0) {
			res = rating;
			res.setCustomer(this.customerService.getCustomerUserAccount(LoginService.getPrincipal().getId()));
			this.validator.validate(res, binding);
		} else {
			res = this.ratingRepository.findOne(rating.getId());
			final Rating copy = new Rating();
			copy.setId(res.getId());
			copy.setVersion(res.getVersion());
			copy.setComment(rating.getComment());
			copy.setValoration(rating.getValoration());
			copy.setCustomer(res.getCustomer());
			copy.setRestaurant(rating.getRestaurant());

			this.validator.validate(copy, binding);
			res = copy;
		}
		return res;
	}

}
