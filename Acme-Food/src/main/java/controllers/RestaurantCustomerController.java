
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RestaurantService;
import domain.Restaurant;

@Controller
@RequestMapping("/restaurant/customer")
public class RestaurantCustomerController {

	@Autowired
	private RestaurantService	restaurantService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Restaurant> restaurants;

		restaurants = this.restaurantService.getRestaurantWithFood();

		result = new ModelAndView("restaurant/list");
		result.addObject("restaurants", restaurants);
		return result;
	}
}
