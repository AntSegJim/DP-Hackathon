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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CreditCardService;
import services.CustomerService;
import services.CustomizableSystemService;
import services.FinderService;
import services.RestaurantService;
import domain.Administrator;
import forms.RegistrationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private RestaurantService			restaurantService;

	@Autowired
	private FinderService				finderService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private CreditCardService			creditCardService;
	//
	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private AdministratorService		administratorService;


	//	// Constructors -----------------------------------------------------------
	//
	//	public AdministratorController() {
	//		super();
	//	}
	//
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		final ModelAndView result;

		final Collection<String> getRestaurantWithMoreScore = this.restaurantService.getRestaurantWithMoreScore();

		final Collection<String> getRestaurantWithLessScore = this.restaurantService.getRestaurantWithLessScore();

		final Collection<String> getCustomerWithMoreCashThanAvg = this.customerService.getCustomerWithMoreCashThanAvg();

		final List<Object[]> getAvgMinMaxDesvResultsByFinder = this.finderService.getAvgMinMaxDesvResultsByFinder();
		final Double getAvgResultsByFinder = (Double) getAvgMinMaxDesvResultsByFinder.get(0)[0];
		final Integer getMinResultsByFinder = (Integer) getAvgMinMaxDesvResultsByFinder.get(0)[1];
		final Integer getMaxResultsByFinder = (Integer) getAvgMinMaxDesvResultsByFinder.get(0)[2];
		final Double getDesvResultsByFinder = (Double) getAvgMinMaxDesvResultsByFinder.get(0)[3];

		final Collection<String> getTop5RestaurantsWithMoreOrders = this.restaurantService.getTop5RestaurantsWithMoreOrders();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("getRestaurantWithMoreScore", getRestaurantWithMoreScore);

		result.addObject("getRestaurantWithLessScore", getRestaurantWithLessScore);

		result.addObject("getCustomerWithMoreCashThanAvg", getCustomerWithMoreCashThanAvg);

		result.addObject("getAvgResultsByFinder", getAvgResultsByFinder);
		result.addObject("getMinResultsByFinder", getMinResultsByFinder);
		result.addObject("getMaxResultsByFinder", getMaxResultsByFinder);
		result.addObject("getDesvResultsByFinder", getDesvResultsByFinder);

		result.addObject("getTop5RestaurantsWithMoreOrders", getTop5RestaurantsWithMoreOrders);

		return result;
	}

	//	//	-------------------------------------------------------
	//	//	+ The best and the worst position in terms of salary 
	//	//
	//	//	select p.title from Position p where p.salary=(select max(p.salary) from Position p)
	//	//	select p.title from Position p where p.salary=(select min(p.salary) from Position p)
	//
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationForm registrationForm = new RegistrationForm();

		registrationForm = registrationForm.createToAdmin();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("administrator/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Administrator admin = null;

		try {

			admin = this.administratorService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {

				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("administrator/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {

			result = new ModelAndView("administrator/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}

}
