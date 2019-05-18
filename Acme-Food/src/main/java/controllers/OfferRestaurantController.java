
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.OfferService;
import services.RestaurantService;
import domain.FoodDishes;
import domain.Offer;
import domain.Restaurant;

@Controller
@RequestMapping("/offer/restaurant")
public class OfferRestaurantController {

	@Autowired
	private OfferService		offerService;
	@Autowired
	private RestaurantService	restaurantService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Offer> offers;

		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());

		offers = this.offerService.getOffersByRestaurant(r.getId());

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer offerId) {
		final ModelAndView result;
		final Offer offer;

		offer = this.offerService.findOne(offerId);
		final Collection<FoodDishes> foodDisheses = offer.getFoodDisheses();
		Assert.notNull(offer);

		result = new ModelAndView("offer/show");
		result.addObject("offer", offer);
		result.addObject("foodDisheses", foodDisheses);
		return result;

	}

}
