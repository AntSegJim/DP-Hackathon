
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.RestaurantService;
import services.SocialProfileService;
import domain.Restaurant;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile/restaurant")
public class SocialProfileRestaurantController {

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private RestaurantService		restaurantService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<SocialProfile> socialProfiles;

		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());

		socialProfiles = this.socialProfileService.getSocialProfilesByRestaurant(r.getId());

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);
		return result;

	}

}
