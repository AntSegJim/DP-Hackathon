
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RestaurantRepository;
import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.Restaurant;
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

	public SocialProfile findOne(final int socialProfileId) {
		final SocialProfile socialProfile = this.socialProfileRepository.findOne(socialProfileId);
		final UserAccount userAccount = LoginService.getPrincipal();
		final Restaurant restaurant = this.restaurantRepository.getRestaurantByUserAccount(userAccount.getId());
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT"));
		Assert.isTrue(socialProfile.getRestaurant() == restaurant);
		return socialProfile;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT"));
		Assert.isTrue(socialProfile.getRestaurant().equals(this.restaurantRepository.getRestaurantByUserAccount(userAccount.getId())));
		final SocialProfile socialProfileSave = this.socialProfileRepository.save(socialProfile);
		return socialProfileSave;
	}

}
