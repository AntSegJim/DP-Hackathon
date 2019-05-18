
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomizableSystemService;
import services.FinderService;
import domain.Finder;

@Controller
@RequestMapping("/finder/customer")
public class FinderCustomerController {

	@Autowired
	private FinderService				finderService;
	@Autowired
	private CustomizableSystemService	customizableSystemService;


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

}
