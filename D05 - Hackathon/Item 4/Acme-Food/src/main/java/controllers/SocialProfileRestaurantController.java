
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.RestaurantService;
import services.SocialProfileService;
import domain.Restaurant;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile/restaurant")
public class SocialProfileRestaurantController extends AbstractController {

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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();
		Assert.notNull(socialProfile);

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView result;
		final SocialProfile socialProfile;
		try {
			socialProfile = this.socialProfileService.findOne(socialProfileId);
			Assert.notNull(socialProfile);
			result = new ModelAndView("socialProfile/edit");
			result.addObject("socialProfile", socialProfile);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		SocialProfile sp;
		try {
			sp = this.socialProfileService.reconstruct(socialProfile, binding);
			if (!binding.hasErrors()) {
				this.socialProfileService.save(sp);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("socialProfile/edit");
				result.addObject("socialProfile", socialProfile);
			}
		} catch (final ValidationException opps) {
			result = new ModelAndView("socialProfile/edit");
			result.addObject("socialProfile", socialProfile);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		try {
			final SocialProfile sp = this.socialProfileService.reconstruct(socialProfile, binding);
			if (!binding.hasErrors()) {
				this.socialProfileService.delete(sp);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("socialProfile/edit");
				result.addObject("socialProfile", socialProfile);
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

}
