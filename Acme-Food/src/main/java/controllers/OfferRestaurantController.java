
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.OfferService;
import services.RestaurantService;
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
		//Assert.notNull(offers);

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		return result;

	}

}
