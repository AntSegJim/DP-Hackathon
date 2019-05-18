
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.OfferRepository;
import domain.Offer;
import domain.Restaurant;

@Service
@Transactional
public class OfferService {

	@Autowired
	private OfferRepository	offerRepository;


	public Offer create() {

		final Offer offer = new Offer();
		offer.setTitle("");
		offer.setTotalPrice(0);
		offer.setRestaurant(new Restaurant());

		return offer;

	}

}
