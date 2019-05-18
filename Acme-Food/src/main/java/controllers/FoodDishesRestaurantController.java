
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
import services.ActorService;
import services.FoodDishesService;
import domain.FoodDishes;
import domain.Restaurant;

@Controller
@RequestMapping("/foodDishes/restaurant")
public class FoodDishesRestaurantController {

	@Autowired
	private FoodDishesService	foodDishesService;
	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<FoodDishes> foodDishes;

		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = (Restaurant) this.actorService.getActorByUserAccount(user.getId());

		foodDishes = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
		Assert.notNull(foodDishes);

		result = new ModelAndView("foodDishes/list");
		result.addObject("foodDishes", foodDishes);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer foodDishesId) {
		final ModelAndView result;
		final FoodDishes foodDishe;

		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = (Restaurant) this.actorService.getActorByUserAccount(user.getId());

		foodDishe = this.foodDishesService.findOne(foodDishesId);
		Assert.notNull(foodDishe);

		result = new ModelAndView("foodDishes/show");
		result.addObject("foodDishe", foodDishe);
		return result;

	}

}
