
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CustomizableSystemService;
import services.DealerService;
import services.RestaurantService;
import domain.Dealer;
import forms.RegistrationFormDealer;

@Controller
@RequestMapping("/dealer/restaurant")
public class DealerRestaurantController {

	@Autowired
	private DealerService				dealerService;
	@Autowired
	private RestaurantService			restaurantService;
	@Autowired
	private CustomizableSystemService	customizableService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Dealer> dealers = this.dealerService.getAllDealerByRestaurantUserAccount(LoginService.getPrincipal().getId());

		result = new ModelAndView("dealer/list");
		result.addObject("dealers", dealers);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createDealerForm() {
		ModelAndView result;
		RegistrationFormDealer registrationForm = new RegistrationFormDealer();

		registrationForm = registrationForm.createToDealer();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("dealer/edit");
		result.addObject("actor", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer dealerId) {
		ModelAndView result;
		final RegistrationFormDealer registrationForm = new RegistrationFormDealer();
		final Dealer dealer;

		try {

			dealer = this.dealerService.findOne(dealerId);
			Assert.isTrue(dealer.getRestaurant().equals(this.restaurantService.getRestaurantByUserAccount(LoginService.getPrincipal().getId())));

			registrationForm.setId(dealer.getId());
			registrationForm.setVersion(dealer.getVersion());
			registrationForm.setName(dealer.getName());
			registrationForm.setVatNumber(dealer.getVatNumber());
			registrationForm.setSurnames(dealer.getSurnames());
			registrationForm.setPhoto(dealer.getPhoto());
			registrationForm.setEmail(dealer.getEmail());
			registrationForm.setPhone(dealer.getPhone());

			registrationForm.setAddress(dealer.getAddress());
			registrationForm.setPassword(dealer.getUserAccount().getPassword());
			registrationForm.setPatternPhone(false);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername(dealer.getUserAccount().getUsername());
			userAccount.setPassword(dealer.getUserAccount().getPassword());
			registrationForm.setUserAccount(userAccount);

			result = new ModelAndView("dealer/edit");
			result.addObject("actor", registrationForm);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("actor") final RegistrationFormDealer r, final BindingResult binding) {
		ModelAndView result;
		final Dealer dealer;

		try {
			dealer = this.dealerService.reconstruct(r, binding);

			if (!binding.hasErrors()) {
				this.dealerService.save(dealer);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("dealer/edit");
				result.addObject("actor", r);
			}
		} catch (final Exception e) {
			result = new ModelAndView("dealer/edit");
			result.addObject("exception", e);
			result.addObject("actor", r);
		}

		return result;

	}

}
