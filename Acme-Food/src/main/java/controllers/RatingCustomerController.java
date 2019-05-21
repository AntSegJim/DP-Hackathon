
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RatingService;
import services.RestaurantService;
import domain.Rating;
import domain.Restaurant;

@Controller
@RequestMapping("/rating/customer")
public class RatingCustomerController {

	@Autowired
	private RatingService		ratingService;
	@Autowired
	private RestaurantService	restaurantService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Rating> ratings = this.ratingService.getAllMyRatings();

		result = new ModelAndView("rating/list");
		result.addObject("ratings", ratings);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer ratingId) {
		final ModelAndView result;
		final Rating rating = this.ratingService.findOne(ratingId);

		result = new ModelAndView("rating/show");
		result.addObject("rating", rating);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Rating rating = this.ratingService.create();

		result = new ModelAndView("rating/edit");
		result.addObject("rating", rating);
		final Collection<Restaurant> restaurants = this.restaurantService.getAllRestaurantWhereIHaveDoneAOrder();
		restaurants.removeAll(this.restaurantService.getAllMyRatings());
		result.addObject("restaurants", restaurants);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer ratingId) {
		final ModelAndView result;
		final Rating rating = this.ratingService.findOne(ratingId);

		result = new ModelAndView("rating/edit");
		result.addObject("rating", rating);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Rating r, final BindingResult binding) {
		ModelAndView result;
		final Rating rating;

		try {
			rating = this.ratingService.reconstruct(r, binding);

			if (!binding.hasErrors()) {
				this.ratingService.save(rating);
				result = new ModelAndView("redirect:list.do");
			} else if (r.getId() == 0) {
				result = new ModelAndView("rating/edit");
				result.addObject("rating", rating);
				final Collection<Restaurant> restaurants = this.restaurantService.getAllRestaurantWhereIHaveDoneAOrder();
				restaurants.removeAll(this.restaurantService.getAllMyRatings());
				result.addObject("restaurants", restaurants);
			} else {
				result = new ModelAndView("rating/edit");
				result.addObject("rating", rating);
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;

	}

}
