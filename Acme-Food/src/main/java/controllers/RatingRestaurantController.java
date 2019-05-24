
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.RatingService;
import services.RestaurantService;

@Controller
@RequestMapping("/rating/restaurant")
public class RatingRestaurantController {

	@Autowired
	private RatingService		ratingService;
	@Autowired
	private RestaurantService	restaurantService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		result = new ModelAndView("rating/list");
		result.addObject("ratings", this.restaurantService.getRestaurantByUserAccount(LoginService.getPrincipal().getId()).getRatings());
		return result;

	}

}
