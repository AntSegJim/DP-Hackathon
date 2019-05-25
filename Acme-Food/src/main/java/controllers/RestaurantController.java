
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomizableSystemService;
import services.RestaurantService;
import domain.Restaurant;
import forms.RegistrationFormRestaurant;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController extends AbstractController {

	@Autowired
	private RestaurantService			restaurantService;

	@Autowired
	private CustomizableSystemService	customizableService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Restaurant> restaurants;

		restaurants = this.restaurantService.findAll();

		result = new ModelAndView("restaurant/list");
		result.addObject("restaurants", restaurants);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;

		RegistrationFormRestaurant registrationForm = new RegistrationFormRestaurant();

		registrationForm = registrationForm.createToRestaurant();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("restaurant/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormRestaurant registrationForm, final BindingResult binding) {
		ModelAndView result;
		Restaurant restaurant = null;

		try {

			restaurant = this.restaurantService.reconstruct(registrationForm, binding);

			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {

				this.restaurantService.save(restaurant);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("restaurant/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {

			result = new ModelAndView("restaurant/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}

}
