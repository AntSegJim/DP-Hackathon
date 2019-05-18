
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RestaurantRepository;
import repositories.SocialProfileRepository;
import security.UserAccount;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	@Autowired
	private SocialProfileRepository	socialProfileRepository;
	@Autowired
	private RestaurantRepository	restaurantRepository;
	@Autowired
	private ActorService			actorS;


	public SocialProfile create() {
		final SocialProfile socialProfile = new SocialProfile();
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		socialProfile.setNameSocialNetwork("");
		socialProfile.setNickName("");
		socialProfile.setUrl("");
		socialProfile.setRestaurant(this.restaurantRepository.getRestaurantByUserAccount(user.getId()));

		return socialProfile;
	}

	public Collection<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

}
