
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	@Autowired
	private Validator				validator;


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

	public void delete(final SocialProfile socialProfile) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT"));
		Assert.isTrue(socialProfile.getRestaurant().equals(this.restaurantRepository.getRestaurantByUserAccount(userAccount.getId())));
	}

	public SocialProfile reconstruct(final SocialProfile socialProfile, final BindingResult binding) {
		SocialProfile res;
		if (socialProfile.getId() == 0) {
			res = socialProfile;
			final UserAccount user = LoginService.getPrincipal();
			final Restaurant restaurant = this.restaurantRepository.getRestaurantByUserAccount(user.getId());
			res.setRestaurant(restaurant);
			this.validator.validate(res, binding);
		} else {
			res = this.socialProfileRepository.findOne(socialProfile.getId());
			final SocialProfile sp = new SocialProfile();
			sp.setId(res.getId());
			sp.setVersion(res.getVersion());
			sp.setNameSocialNetwork(socialProfile.getNameSocialNetwork());
			sp.setNickName(socialProfile.getNickName());
			sp.setUrl(socialProfile.getUrl());
			sp.setRestaurant(res.getRestaurant());
			this.validator.validate(sp, binding);
			res = sp;
		}
		return res;
	}

}
