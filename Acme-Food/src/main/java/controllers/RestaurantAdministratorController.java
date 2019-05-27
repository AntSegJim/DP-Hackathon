/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.RestaurantService;
import domain.Restaurant;

@Controller
@RequestMapping("/restaurant/administrator")
public class RestaurantAdministratorController extends AbstractController {

	@Autowired
	private RestaurantService		restaurantService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Restaurant> restaurants;

		restaurants = this.restaurantService.getRestaurantBan();

		result = new ModelAndView("restaurant/list");
		result.addObject("restaurants", restaurants);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView restaurant(@RequestParam final Integer restaurantId) {
		ModelAndView result;

		final Restaurant restaurant = this.restaurantService.findOneAdmin(restaurantId);
		final String lang = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("restaurant/show");
		result.addObject("restaurant", restaurant);
		result.addObject("lang", lang);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.POST, params = "save")
	public ModelAndView editRestaurant(@ModelAttribute("restaurant") final Restaurant restaurant, final BindingResult binding) {
		ModelAndView result;
		Restaurant res;
		try {
			res = this.restaurantService.reconstruct(restaurant, binding);
			if (!binding.hasErrors()) {
				this.restaurantService.save(res);

				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("profile/editRestaurant");
				result.addObject("restaurant", restaurant);

			}

		} catch (final Exception e) {

			result = new ModelAndView("profile/editRestaurant");
			result.addObject("restaurant", restaurant);
			result.addObject("exception", e);

		}
		return result;

	}
}
