
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomizableSystemService;
import services.FinderService;
import services.FoodDishesService;
import domain.Finder;
import domain.FoodDishes;

@Controller
@RequestMapping("/finder/customer")
public class FinderCustomerController {

	@Autowired
	private FinderService				finderService;
	@Autowired
	private CustomizableSystemService	customizableSystemService;
	@Autowired
	private FoodDishesService			foodDishesService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		final ModelAndView result;
		final Finder finder = this.finderService.findOne();

		result = new ModelAndView("finder/show");
		result.addObject("restaurants", finder.getRestaurants());
		result.addObject("ñapa", this.customizableSystemService.getMaxResults());
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Finder finder = this.finderService.findOne();

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Finder f, final BindingResult binding) {
		ModelAndView result;
		final Finder finder;

		try {
			finder = this.finderService.reconstruct(f, binding);

			if (!binding.hasErrors()) {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:show.do");
			} else {
				result = new ModelAndView("finder/edit");
				result.addObject("finder", finder);
			}
		} catch (final Exception e) {
			result = new ModelAndView("finder/edit");
			result.addObject("finder", f);
		}

		return result;

	}

	@RequestMapping(value = "/watch-more", method = RequestMethod.GET)
	public ResponseEntity<String> getInfoRestaurant(@RequestParam final Integer restaurantId) {
		ResponseEntity<String> res;
		try {
			String string = "";
			final List<FoodDishes> platos = (List<FoodDishes>) this.foodDishesService.findFoodDishesByRestaurant(restaurantId);
			if (platos.size() > 0)
				for (int i = 0; i < platos.size(); i++) {
					string += platos.get(i).getName() + ",";
					final String[] imagenes = platos.get(i).getPictures().split(",");
					if (imagenes.length > 0)
						string += imagenes[0] + "|";
					else
						string += "No imagen|";
					if (i == 2)
						break;
				}
			else
				string = "Este restaurante no tiene platos para mostrar/This restaurant hasn't go any dish";
			res = new ResponseEntity<String>(string, HttpStatus.OK);
		} catch (final Exception e) {
			res = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return res;
	}

}
