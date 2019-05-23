
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FoodDishesService;
import domain.FoodDishes;

@Controller
@RequestMapping("/foodDishes/customer")
public class FoodDishCustomerController extends AbstractController {

	@Autowired
	private FoodDishesService	foodDishesService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer idRestaurant) {
		final ModelAndView result;
		final Collection<FoodDishes> foodDishes;

		foodDishes = this.foodDishesService.findFoodDishesByRestaurant(idRestaurant);
		Assert.notNull(foodDishes);

		result = new ModelAndView("foodDishes/list");
		result.addObject("foodDishes", foodDishes);
		result.addObject("idRestaurant", idRestaurant);

		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer idFoodDish) {
		final ModelAndView result;
		final FoodDishes foodDish;

		foodDish = this.foodDishesService.findOne(idFoodDish);
		Assert.notNull(foodDish);

		result = new ModelAndView("foodDishes/show");
		result.addObject("foodDish", foodDish);
		result.addObject("idRestaurant", foodDish.getRestaurant().getId());

		return result;

	}

}
