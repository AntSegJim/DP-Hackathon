
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
import services.FoodDishesService;
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
	@Autowired
	private FoodDishesService	foodDishesService;


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
		ModelAndView result;
		try {
			final Offer offer;

			offer = this.offerService.findOne(offerId);
			final Collection<FoodDishes> foodDisheses = offer.getFoodDisheses();
			Assert.notNull(offer);

			result = new ModelAndView("offer/show");
			result.addObject("offer", offer);
			result.addObject("foodDisheses", foodDisheses);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Offer offer;
		final Collection<FoodDishes> foodDisheses;

		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());

		foodDisheses = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
		offer = this.offerService.create();
		Assert.notNull(offer);
		Assert.notNull(foodDisheses);

		result = new ModelAndView("offer/edit");
		result.addObject("offer", offer);
		result.addObject("foodDisheses", foodDisheses);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int offerId) {
		ModelAndView result;
		final Offer offer;
		final Collection<FoodDishes> foodDisheses;
		try {
			final UserAccount user = LoginService.getPrincipal();
			final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());
			foodDisheses = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
			offer = this.offerService.findOne(offerId);

			Assert.notNull(offer);
			Assert.notNull(foodDisheses);

			result = new ModelAndView("offer/edit");
			result.addObject("offer", offer);
			result.addObject("foodDisheses", foodDisheses);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Offer offer, final BindingResult binding) {
		ModelAndView result;
		Offer o;
		try {
			o = this.offerService.reconstruct(offer, binding);
			if (!binding.hasErrors()) {
				this.offerService.save(o);
				result = new ModelAndView("redirect:list.do");
			} else {
				final Collection<FoodDishes> foodDisheses;
				final UserAccount user = LoginService.getPrincipal();
				final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());
				foodDisheses = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
				result = new ModelAndView("offer/edit");
				result.addObject("offer", offer);
				result.addObject("foodDisheses", foodDisheses);
			}
		} catch (final ValidationException opps) {
			final Collection<FoodDishes> foodDisheses;
			final UserAccount user = LoginService.getPrincipal();
			final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());
			foodDisheses = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
			result = new ModelAndView("offer/edit");
			result.addObject("offer", offer);
			result.addObject("foodDisheses", foodDisheses);
		} catch (final Exception e) {
			final Collection<FoodDishes> foodDisheses;
			final UserAccount user = LoginService.getPrincipal();
			final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());
			foodDisheses = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
			result = new ModelAndView("offer/edit");
			result.addObject("exception", e);
			result.addObject("offer", offer);
			result.addObject("foodDisheses", foodDisheses);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Offer offer, final BindingResult binding) {
		ModelAndView result;
		try {
			final Offer o = this.offerService.reconstruct(offer, binding);
			if (!binding.hasErrors()) {
				this.offerService.delete(o);
				result = new ModelAndView("redirect:list.do");
			} else {
				final Collection<FoodDishes> foodDisheses;
				final UserAccount user = LoginService.getPrincipal();
				final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());
				foodDisheses = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
				result = new ModelAndView("offer/edit");
				result.addObject("offer", offer);
				result.addObject("foodDisheses", foodDisheses);
			}
		} catch (final Exception e) {
			final Collection<FoodDishes> foodDisheses;
			final UserAccount user = LoginService.getPrincipal();
			final Restaurant r = this.restaurantService.getRestaurantByUserAccount(user.getId());
			foodDisheses = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
			result = new ModelAndView("offer/edit");
			result.addObject("exception", e);
			result.addObject("offer", offer);
			result.addObject("foodDisheses", foodDisheses);
		}
		return result;
	}

}
