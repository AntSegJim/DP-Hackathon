
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NotificationService;
import domain.Notification;

@Controller
@RequestMapping("/notification/administrator")
public class NotificationAdministratorController {

	@Autowired
	private NotificationService	notificationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Notification notification;

		notification = this.notificationService.create();
		Assert.notNull(notification);

		result = new ModelAndView("notification/edit");
		result.addObject("notification", notification);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer notificationId) {
		ModelAndView result;
		try {
			final Notification notification;

			notification = this.notificationService.findOne(notificationId);
			Assert.notNull(notification);

			result = new ModelAndView("notification/edit");
			result.addObject("notification", notification);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../notification/actor/list.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Notification notification, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.notificationService.save(notification);
				result = new ModelAndView("redirect:../../notification/actor/list.do");
			} else {
				result = new ModelAndView("notification/edit");
				result.addObject("notification", notification);
			}
		} catch (final Exception e) {
			result = new ModelAndView("notification/edit");
			result.addObject("exception", e);
			result.addObject("notification", notification);
		}

		return result;
	}

}
