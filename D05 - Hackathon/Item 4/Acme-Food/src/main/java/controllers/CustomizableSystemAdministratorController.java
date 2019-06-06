
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomizableSystemService;
import domain.CustomizableSystem;

@Controller
@RequestMapping("/customizableSystem/administrator")
public class CustomizableSystemAdministratorController extends AbstractController {

	@Autowired
	private CustomizableSystemService	customizableSystem;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView show() {
		final ModelAndView result;
		final CustomizableSystem customizable;

		customizable = this.customizableSystem.findAll().iterator().next();
		Assert.notNull(customizable);

		result = new ModelAndView("customizable/edit");
		result.addObject("customizable", customizable);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("customizable") @Valid final CustomizableSystem customizable, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.customizableSystem.save(customizable);
				result = new ModelAndView("redirect:../../");
			} else {
				result = new ModelAndView("customizable/edit");
				result.addObject("customizable", customizable);
			}
		} catch (final Exception e) {
			result = new ModelAndView("customizable/edit");
			result.addObject("exception", e);
			result.addObject("customizable", customizable);
		}

		return result;
	}

}
