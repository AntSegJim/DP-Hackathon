
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		result.addObject("ratings", this.restaurantService.getAllTheRatingsOfMyRestaurant());
		return result;

	}

	//	@RequestMapping(value = "/show", method = RequestMethod.GET)
	//	public ModelAndView show(@RequestParam final Integer ratingId) {
	//		final ModelAndView result;
	//		final Rating rating = this.ratingService.findOne(ratingId);
	//
	//		result = new ModelAndView("rating/show");
	//		result.addObject("rating", rating);
	//		return result;
	//
	//	}

}
