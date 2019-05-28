
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import services.RestaurantService;
import domain.FoodDishes;
import domain.Offer;
import domain.Restaurant;

@Controller
@RequestMapping("/offer/customer")
public class OfferCustomerController extends AbstractController {

	@Autowired
	private OfferService		offerService;
	@Autowired
	private RestaurantService	restaurantService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listOffersCustomer(@RequestParam final int restaurantId) {
		final ModelAndView result;
		final Collection<Offer> offers;

		offers = this.offerService.getOffersByRestaurant(restaurantId);
		final Restaurant r = this.restaurantService.findOneSinAutenticar(restaurantId);
		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("restaurant", r);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showOffersCustomer(@RequestParam final Integer offerId, @RequestParam final Integer restaurantId) {
		ModelAndView result;
		final Offer offer;

		offer = this.offerService.findOneSinAutenticar(offerId);
		final Collection<FoodDishes> foodDisheses = offer.getFoodDisheses();
		final Restaurant r = this.restaurantService.findOneSinAutenticar(restaurantId);
		Assert.notNull(offer);

		result = new ModelAndView("offer/show");
		result.addObject("offer", offer);
		result.addObject("foodDisheses", foodDisheses);
		result.addObject("restaurant", r);
		return result;
	}

}
