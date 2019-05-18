
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.OfferRepository;
import repositories.RestaurantRepository;
import security.UserAccount;
import domain.Offer;

@Service
@Transactional
public class OfferService {

	@Autowired
	private OfferRepository			offerRepository;
	@Autowired
	private RestaurantRepository	restaurantRepository;
	@Autowired
	private ActorService			actorS;


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

}
