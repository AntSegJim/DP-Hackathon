
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.RestaurantRepository;
import domain.Restaurant;

@Service
@Transactional
public class RestaurantService {

	@Autowired
	private RestaurantRepository	restaurantRepository;


	public Restaurant getRestaurantByUserAccount(final Integer userAccountId) {
		return this.restaurantRepository.getRestaurantByUserAccount(userAccountId);
	}

}
