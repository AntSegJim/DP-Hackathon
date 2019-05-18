
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OfferRepository;
import repositories.RestaurantRepository;
import security.LoginService;
import security.UserAccount;
import domain.Offer;
import domain.Restaurant;

@Service
@Transactional
public class OfferService {

	@Autowired
	private OfferRepository			offerRepository;
	@Autowired
	private RestaurantRepository	restaurantRepository;
	@Autowired
	private ActorService			actorS;
	@Autowired
	private Validator				validator;


	public Offer create() {
		final Offer offer = new Offer();
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		offer.setTitle("");
		offer.setTotalPrice(0);
		offer.setRestaurant(this.restaurantRepository.getRestaurantByUserAccount(user.getId()));

		return offer;
	}

	public Collection<Offer> findAll() {
		return this.offerRepository.findAll();
	}

	public Offer findOne(final int offerId) {
		final Offer offer = this.offerRepository.findOne(offerId);
		final UserAccount userAccount = LoginService.getPrincipal();
		final Restaurant restaurant = this.restaurantRepository.getRestaurantByUserAccount(userAccount.getId());
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT"));
		Assert.isTrue(offer.getRestaurant() == restaurant);
		return offer;
	}

	public Offer save(final Offer offer) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT"));
		Assert.isTrue(offer.getRestaurant().equals(this.restaurantRepository.getRestaurantByUserAccount(userAccount.getId())));
		final Offer offerSave = this.offerRepository.save(offer);
		return offerSave;
	}

	public void delete(final Offer offer) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT"));
		Assert.isTrue(offer.getRestaurant().equals(this.restaurantRepository.getRestaurantByUserAccount(userAccount.getId())));
	}

	public Offer reconstruct(final Offer offer, final BindingResult binding) {
		Offer res;
		if (offer.getId() == 0) {
			res = offer;
			final UserAccount user = LoginService.getPrincipal();
			final Restaurant restaurant = this.restaurantRepository.getRestaurantByUserAccount(user.getId());
			res.setRestaurant(restaurant);
			this.validator.validate(res, binding);
		} else {
			res = this.offerRepository.findOne(offer.getId());
			final Offer o = new Offer();
			o.setId(res.getId());
			o.setVersion(res.getVersion());
			o.setTitle(offer.getTitle());
			o.setTotalPrice(offer.getTotalPrice());
			o.setRestaurant(res.getRestaurant());
			this.validator.validate(o, binding);
			res = o;
		}
		return res;
	}

	public Collection<Offer> getOffersByRestaurant(final int restaurantId) {
		return this.offerRepository.getOffersByRestaurant(restaurantId);
	}

}
