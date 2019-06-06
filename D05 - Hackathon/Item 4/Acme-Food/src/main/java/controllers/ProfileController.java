/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.CreditCardService;
import services.CustomerService;
import services.RestaurantService;
import domain.Actor;
import domain.Administrator;
import domain.CreditCard;
import domain.Customer;
import domain.Restaurant;
import forms.RegistrationForm;
import forms.RegistrationFormCustomerAndCreditCard;
import forms.RegistrationFormRestaurant;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;
	//
	@Autowired
	private CustomerService			customerService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private RestaurantService		restaurantService;


	//
	//	@Autowired
	//	private PositionService			positionService;
	//
	//
	//	// Action-2 ---------------------------------------------------------------		
	//
	//VER SUS DATOS PERSONALES
	@RequestMapping(value = "/personal-datas", method = RequestMethod.GET)
	public ModelAndView action2() {
		ModelAndView result;
		Actor a;
		CreditCard creditCard = null;

		final UserAccount user = LoginService.getPrincipal();
		a = this.actorService.getActorByUserAccount(user.getId());
		if (user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER"))
			creditCard = this.customerService.getCustomerUserAccount(user.getId()).getCreditCard();

		result = new ModelAndView("profile/action-1");
		result.addObject("actor", a);
		result.addObject("creditCard", creditCard);

		return result;
	}
	//
	//	@RequestMapping(value = "/edit-company", method = RequestMethod.GET)
	//	public ModelAndView editCompany() {
	//		ModelAndView result;
	//		final RegistrationFormCompanyAndCreditCard registrationForm = new RegistrationFormCompanyAndCreditCard();
	//		Company company;
	//		CreditCard creditCard;
	//		try {
	//
	//			company = this.companyService.findOne(this.companyService.companyUserAccount(LoginService.getPrincipal().getId()).getId());
	//			creditCard = company.getCreditCard();
	//			Assert.notNull(company);
	//			registrationForm.setId(company.getId());
	//			registrationForm.setVersion(company.getVersion());
	//			registrationForm.setName(company.getName());
	//			registrationForm.setVatNumber(company.getVatNumber());
	//			registrationForm.setSurnames(company.getSurnames());
	//			registrationForm.setPhoto(company.getPhoto());
	//			registrationForm.setEmail(company.getEmail());
	//			registrationForm.setPhone(company.getPhone());
	//			registrationForm.setCreditCard(company.getCreditCard());
	//			registrationForm.setAddress(company.getAddress());
	//			registrationForm.setPassword("");
	//			registrationForm.setCheck(true);
	//			registrationForm.setPatternPhone(false);
	//			registrationForm.setNameCompany(company.getNameCompany());
	//			final UserAccount userAccount = new UserAccount();
	//			userAccount.setUsername(company.getUserAccount().getUsername());
	//			userAccount.setPassword("");
	//			registrationForm.setUserAccount(userAccount);
	//			registrationForm.setBrandName(creditCard.getBrandName());
	//			registrationForm.setHolderName(creditCard.getHolderName());
	//			registrationForm.setNumber(creditCard.getNumber());
	//			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
	//			registrationForm.setExpirationYear(creditCard.getExpirationYear());
	//			registrationForm.setCW(creditCard.getCW());
	//
	//			result = new ModelAndView("profile/editCompany");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("action", "profile/edit-company.do");
	//
	//		} catch (final Exception e) {
	//			result = new ModelAndView("redirect:../../");
	//		}
	//
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit-company", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editCompany(@ModelAttribute("actor") final RegistrationFormCompanyAndCreditCard registrationForm, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		try {
	//			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
	//			registrationForm.setCreditCard(creditCard);
	//			final Company company = this.companyService.reconstruct(registrationForm, binding);
	//			if (!binding.hasErrors()) {
	//				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
	//				company.setCreditCard(creditCardSave);
	//				this.companyService.save(company);
	//
	//				result = new ModelAndView("redirect:personal-datas.do");
	//			} else {
	//				result = new ModelAndView("profile/editCompany");
	//				result.addObject("actor", registrationForm);
	//
	//			}
	//		} catch (final Exception e) {
	//
	//			result = new ModelAndView("profile/editCompany");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("exception", e);
	//
	//		}
	//		return result;
	//
	//	}
	//
	@RequestMapping(value = "/edit-administrator", method = RequestMethod.GET)
	public ModelAndView editAdmin() {
		ModelAndView result;
		final RegistrationForm registrationForm = new RegistrationForm();
		Administrator admin;

		try {

			admin = this.adminService.findOne(this.adminService.getAdministratorByUserAccount(LoginService.getPrincipal().getId()).getId());

			Assert.notNull(admin);
			registrationForm.setId(admin.getId());
			registrationForm.setVersion(admin.getVersion());
			registrationForm.setName(admin.getName());
			registrationForm.setVatNumber(admin.getVatNumber());
			registrationForm.setSurnames(admin.getSurnames());
			registrationForm.setPhoto(admin.getPhoto());
			registrationForm.setEmail(admin.getEmail());
			registrationForm.setPhone(admin.getPhone());

			registrationForm.setAddress(admin.getAddress());
			registrationForm.setPassword(admin.getUserAccount().getPassword());
			registrationForm.setPatternPhone(false);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername(admin.getUserAccount().getUsername());
			userAccount.setPassword(admin.getUserAccount().getPassword());
			registrationForm.setUserAccount(userAccount);

			result = new ModelAndView("profile/editAdmin");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-administrator.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView editAdmin(@ModelAttribute("actor") final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {

			final Administrator admin = this.adminService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {

				this.adminService.save(admin);

				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editAdmin");
				result.addObject("actor", registrationForm);

			}
		} catch (final Exception e) {

			result = new ModelAndView("profile/editAdmin");
			result.addObject("actor", registrationForm);
			result.addObject("exception", e);

		}
		return result;

	}
	//
	@RequestMapping(value = "/edit-customer", method = RequestMethod.GET)
	public ModelAndView editHacker() {
		ModelAndView result;
		final RegistrationFormCustomerAndCreditCard registrationForm = new RegistrationFormCustomerAndCreditCard();
		Customer customer;
		CreditCard creditCard;
		try {

			customer = this.customerService.findOne(this.customerService.getCustomerUserAccount(LoginService.getPrincipal().getId()).getId());
			creditCard = customer.getCreditCard();
			Assert.notNull(customer);
			registrationForm.setId(customer.getId());
			registrationForm.setVersion(customer.getVersion());
			registrationForm.setName(customer.getName());
			registrationForm.setVatNumber(customer.getVatNumber());
			registrationForm.setSurnames(customer.getSurnames());
			registrationForm.setPhoto(customer.getPhoto());
			registrationForm.setEmail(customer.getEmail());
			registrationForm.setPhone(customer.getPhone());
			registrationForm.setCreditCard(customer.getCreditCard());
			registrationForm.setAddress(customer.getAddress());
			registrationForm.setPassword(customer.getUserAccount().getPassword());
			registrationForm.setCheck(true);
			registrationForm.setPatternPhone(false);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername(customer.getUserAccount().getUsername());
			userAccount.setPassword(customer.getUserAccount().getPassword());
			registrationForm.setUserAccount(userAccount);
			registrationForm.setBrandName(creditCard.getBrandName());
			registrationForm.setHolderName(creditCard.getHolderName());
			registrationForm.setNumber(creditCard.getNumber());
			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
			registrationForm.setExpirationYear(creditCard.getExpirationYear());
			registrationForm.setCW(creditCard.getCW());

			result = new ModelAndView("profile/editCustomer");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-customer.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}
	//
	@RequestMapping(value = "/edit-customer", method = RequestMethod.POST, params = "save")
	public ModelAndView editHacker(@ModelAttribute("actor") final RegistrationFormCustomerAndCreditCard registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditCard);
			final Customer customer = this.customerService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {
				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
				customer.setCreditCard(creditCardSave);
				this.customerService.save(customer);

				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editCustomer");
				result.addObject("actor", registrationForm);

			}
		} catch (final Exception e) {

			result = new ModelAndView("profile/editCustomer");
			result.addObject("actor", registrationForm);
			result.addObject("exception", e);

		}
		return result;

	}
	@RequestMapping(value = "/edit-restaurant", method = RequestMethod.GET)
	public ModelAndView editProvider() {
		ModelAndView result;
		final RegistrationFormRestaurant registrationForm = new RegistrationFormRestaurant();
		Restaurant restaurant;

		try {

			restaurant = this.restaurantService.findOne(this.restaurantService.getRestaurantByUserAccount(LoginService.getPrincipal().getId()).getId());

			Assert.notNull(restaurant);
			registrationForm.setId(restaurant.getId());
			registrationForm.setVersion(restaurant.getVersion());
			registrationForm.setName(restaurant.getName());
			registrationForm.setVatNumber(restaurant.getVatNumber());
			registrationForm.setSurnames(restaurant.getSurnames());
			registrationForm.setPhoto(restaurant.getPhoto());
			registrationForm.setEmail(restaurant.getEmail());
			registrationForm.setPhone(restaurant.getPhone());
			registrationForm.setAddress(restaurant.getAddress());
			registrationForm.setComercialName(restaurant.getComercialName());
			registrationForm.setSpeciality(restaurant.getSpeciality());
			registrationForm.setIsBanned(restaurant.getIsBanned());
			registrationForm.setMediumScore(restaurant.getMediumScore());
			registrationForm.setRatings(restaurant.getRatings());
			registrationForm.setOrderTime(restaurant.getOrderTime());
			registrationForm.setPassword(restaurant.getUserAccount().getPassword());
			registrationForm.setCheck(true);
			registrationForm.setPatternPhone(false);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername(restaurant.getUserAccount().getUsername());
			userAccount.setPassword(restaurant.getUserAccount().getPassword());
			registrationForm.setUserAccount(userAccount);

			result = new ModelAndView("profile/editRestaurant");
			result.addObject("actor", registrationForm);
			result.addObject("action", "profile/edit-restaurant.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/edit-restaurant", method = RequestMethod.POST, params = "save")
	public ModelAndView editProvider(@ModelAttribute("actor") final RegistrationFormRestaurant registrationForm, final BindingResult binding) {
		ModelAndView result;

		try {

			final Restaurant restaurant = this.restaurantService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors()) {
				this.restaurantService.save(restaurant);

				result = new ModelAndView("redirect:personal-datas.do");
			} else {
				result = new ModelAndView("profile/editRestaurant");
				result.addObject("actor", registrationForm);

			}
		} catch (final Exception e) {

			result = new ModelAndView("profile/editRestaurant");
			result.addObject("actor", registrationForm);
			result.addObject("exception", e);

		}
		return result;

	}

}
