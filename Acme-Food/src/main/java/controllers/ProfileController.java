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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	//	@Autowired
	//	private ActorService			actorService;
	//
	//	@Autowired
	//	private AdministratorService	adminService;
	//
	//	@Autowired
	//	private CompanyService			companyService;
	//
	//	@Autowired
	//	private AuditorService			auditorService;
	//
	//	@Autowired
	//	private HackerService			hackerService;
	//
	//	@Autowired
	//	private ProviderService			providerService;
	//
	//	@Autowired
	//	private CreditCardService		creditCardService;
	//
	//	@Autowired
	//	private PositionService			positionService;
	//
	//
	//	// Action-2 ---------------------------------------------------------------		
	//
	//	//VER SUS DATOS PERSONALES
	//	@RequestMapping(value = "/personal-datas", method = RequestMethod.GET)
	//	public ModelAndView action2() {
	//		ModelAndView result;
	//		Actor a;
	//		CreditCard creditCard;
	//
	//		final UserAccount user = LoginService.getPrincipal();
	//		a = this.actorService.getActorByUserAccount(user.getId());
	//		creditCard = a.getCreditCard();
	//
	//		result = new ModelAndView("profile/action-1");
	//		result.addObject("actor", a);
	//		result.addObject("creditCard", creditCard);
	//
	//		return result;
	//	}
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
	//	@RequestMapping(value = "/edit-administrator", method = RequestMethod.GET)
	//	public ModelAndView editAdmin() {
	//		ModelAndView result;
	//		final RegistrationForm registrationForm = new RegistrationForm();
	//		Administrator admin;
	//		CreditCard creditCard;
	//		try {
	//
	//			admin = this.adminService.findOne(this.adminService.getAdministratorByUserAccount(LoginService.getPrincipal().getId()).getId());
	//			creditCard = admin.getCreditCard();
	//			Assert.notNull(admin);
	//			registrationForm.setId(admin.getId());
	//			registrationForm.setVersion(admin.getVersion());
	//			registrationForm.setName(admin.getName());
	//			registrationForm.setVatNumber(admin.getVatNumber());
	//			registrationForm.setSurnames(admin.getSurnames());
	//			registrationForm.setPhoto(admin.getPhoto());
	//			registrationForm.setEmail(admin.getEmail());
	//			registrationForm.setPhone(admin.getPhone());
	//			registrationForm.setCreditCard(admin.getCreditCard());
	//			registrationForm.setAddress(admin.getAddress());
	//			registrationForm.setPassword(admin.getUserAccount().getPassword());
	//			registrationForm.setPatternPhone(false);
	//			final UserAccount userAccount = new UserAccount();
	//			userAccount.setUsername(admin.getUserAccount().getUsername());
	//			userAccount.setPassword(admin.getUserAccount().getPassword());
	//			registrationForm.setUserAccount(userAccount);
	//			registrationForm.setBrandName(creditCard.getBrandName());
	//			registrationForm.setHolderName(creditCard.getHolderName());
	//			registrationForm.setNumber(creditCard.getNumber());
	//			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
	//			registrationForm.setExpirationYear(creditCard.getExpirationYear());
	//			registrationForm.setCW(creditCard.getCW());
	//
	//			result = new ModelAndView("profile/editAdmin");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("action", "profile/edit-administrator.do");
	//
	//		} catch (final Exception e) {
	//			result = new ModelAndView("redirect:../../");
	//		}
	//
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit-administrator", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editAdmin(@ModelAttribute("actor") final RegistrationForm registrationForm, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		try {
	//			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
	//			registrationForm.setCreditCard(creditCard);
	//			final Administrator admin = this.adminService.reconstruct(registrationForm, binding);
	//			if (!binding.hasErrors()) {
	//				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
	//				admin.setCreditCard(creditCardSave);
	//				this.adminService.save(admin);
	//
	//				result = new ModelAndView("redirect:personal-datas.do");
	//			} else {
	//				result = new ModelAndView("profile/editAdmin");
	//				result.addObject("actor", registrationForm);
	//
	//			}
	//		} catch (final Exception e) {
	//
	//			result = new ModelAndView("profile/editAdmin");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("exception", e);
	//
	//		}
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit-rookie", method = RequestMethod.GET)
	//	public ModelAndView editHacker() {
	//		ModelAndView result;
	//		final RegistrationFormHacker registrationForm = new RegistrationFormHacker();
	//		Rookie rookie;
	//		CreditCard creditCard;
	//		try {
	//
	//			rookie = this.hackerService.findOne(this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId());
	//			creditCard = rookie.getCreditCard();
	//			Assert.notNull(rookie);
	//			registrationForm.setId(rookie.getId());
	//			registrationForm.setVersion(rookie.getVersion());
	//			registrationForm.setName(rookie.getName());
	//			registrationForm.setVatNumber(rookie.getVatNumber());
	//			registrationForm.setSurnames(rookie.getSurnames());
	//			registrationForm.setPhoto(rookie.getPhoto());
	//			registrationForm.setEmail(rookie.getEmail());
	//			registrationForm.setPhone(rookie.getPhone());
	//			registrationForm.setCreditCard(rookie.getCreditCard());
	//			registrationForm.setAddress(rookie.getAddress());
	//			registrationForm.setPassword(rookie.getUserAccount().getPassword());
	//			registrationForm.setCheck(true);
	//			registrationForm.setPatternPhone(false);
	//			final UserAccount userAccount = new UserAccount();
	//			userAccount.setUsername(rookie.getUserAccount().getUsername());
	//			userAccount.setPassword(rookie.getUserAccount().getPassword());
	//			registrationForm.setUserAccount(userAccount);
	//			registrationForm.setBrandName(creditCard.getBrandName());
	//			registrationForm.setHolderName(creditCard.getHolderName());
	//			registrationForm.setNumber(creditCard.getNumber());
	//			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
	//			registrationForm.setExpirationYear(creditCard.getExpirationYear());
	//			registrationForm.setCW(creditCard.getCW());
	//
	//			result = new ModelAndView("profile/editRookie");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("action", "profile/edit-rookie.do");
	//
	//		} catch (final Exception e) {
	//			result = new ModelAndView("redirect:../../");
	//		}
	//
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit-rookie", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editHacker(@ModelAttribute("actor") final RegistrationFormHacker registrationForm, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		try {
	//			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
	//			registrationForm.setCreditCard(creditCard);
	//			final Rookie rookie = this.hackerService.reconstruct(registrationForm, binding);
	//			if (!binding.hasErrors()) {
	//				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
	//				rookie.setCreditCard(creditCardSave);
	//				this.hackerService.save(rookie);
	//
	//				result = new ModelAndView("redirect:personal-datas.do");
	//			} else {
	//				result = new ModelAndView("profile/editRookie");
	//				result.addObject("actor", registrationForm);
	//
	//			}
	//		} catch (final Exception e) {
	//
	//			result = new ModelAndView("profile/editRookie");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("exception", e);
	//
	//		}
	//		return result;
	//
	//	}
	//	@RequestMapping(value = "/edit-provider", method = RequestMethod.GET)
	//	public ModelAndView editProvider() {
	//		ModelAndView result;
	//		final RegistrationFormProviderAndCreditCard registrationForm = new RegistrationFormProviderAndCreditCard();
	//		Provider provider;
	//		CreditCard creditCard;
	//		try {
	//
	//			provider = this.providerService.findOne(this.providerService.providerUserAccount(LoginService.getPrincipal().getId()).getId());
	//			creditCard = provider.getCreditCard();
	//			Assert.notNull(provider);
	//			registrationForm.setId(provider.getId());
	//			registrationForm.setVersion(provider.getVersion());
	//			registrationForm.setMake(provider.getMake());
	//			registrationForm.setName(provider.getName());
	//			registrationForm.setVatNumber(provider.getVatNumber());
	//			registrationForm.setSurnames(provider.getSurnames());
	//			registrationForm.setPhoto(provider.getPhoto());
	//			registrationForm.setEmail(provider.getEmail());
	//			registrationForm.setPhone(provider.getPhone());
	//			registrationForm.setCreditCard(provider.getCreditCard());
	//			registrationForm.setAddress(provider.getAddress());
	//			registrationForm.setPassword(provider.getUserAccount().getPassword());
	//			registrationForm.setCheck(true);
	//			registrationForm.setPatternPhone(false);
	//			final UserAccount userAccount = new UserAccount();
	//			userAccount.setUsername(provider.getUserAccount().getUsername());
	//			userAccount.setPassword(provider.getUserAccount().getPassword());
	//			registrationForm.setUserAccount(userAccount);
	//			registrationForm.setBrandName(creditCard.getBrandName());
	//			registrationForm.setHolderName(creditCard.getHolderName());
	//			registrationForm.setNumber(creditCard.getNumber());
	//			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
	//			registrationForm.setExpirationYear(creditCard.getExpirationYear());
	//			registrationForm.setCW(creditCard.getCW());
	//
	//			result = new ModelAndView("profile/editProvider");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("action", "profile/edit-provider.do");
	//
	//		} catch (final Exception e) {
	//			result = new ModelAndView("redirect:../../");
	//		}
	//
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit-provider", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editProvider(@ModelAttribute("actor") final RegistrationFormProviderAndCreditCard registrationForm, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		try {
	//			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
	//			registrationForm.setCreditCard(creditCard);
	//			final Provider provider = this.providerService.reconstruct(registrationForm, binding);
	//			if (!binding.hasErrors()) {
	//				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
	//				provider.setCreditCard(creditCardSave);
	//				this.providerService.save(provider);
	//
	//				result = new ModelAndView("redirect:personal-datas.do");
	//			} else {
	//				result = new ModelAndView("profile/editProvider");
	//				result.addObject("actor", registrationForm);
	//
	//			}
	//		} catch (final Exception e) {
	//
	//			result = new ModelAndView("profile/editProvider");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("exception", e);
	//
	//		}
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit-auditor", method = RequestMethod.GET)
	//	public ModelAndView editAuditor() {
	//		ModelAndView result;
	//		final RegistrationFormAuditor registrationForm = new RegistrationFormAuditor();
	//		Auditor auditor;
	//		CreditCard creditCard;
	//		Collection<Position> allPositions;
	//		Collection<Position> positionAssing;
	//		Collection<Position> positionsMe;
	//
	//		try {
	//			allPositions = this.positionService.findAll();
	//			positionAssing = this.positionService.getAllPositionAssing();
	//			final boolean eliminar = allPositions.removeAll(positionAssing);
	//
	//			auditor = this.auditorService.findOne(this.auditorService.auditorUserAccount(LoginService.getPrincipal().getId()).getId());
	//			positionsMe = auditor.getPositions();
	//			final boolean añadir = allPositions.addAll(positionsMe);
	//
	//			creditCard = auditor.getCreditCard();
	//			Assert.notNull(auditor);
	//			registrationForm.setId(auditor.getId());
	//			registrationForm.setVersion(auditor.getVersion());
	//			registrationForm.setPositions(auditor.getPositions());
	//			registrationForm.setName(auditor.getName());
	//			registrationForm.setVatNumber(auditor.getVatNumber());
	//			registrationForm.setSurnames(auditor.getSurnames());
	//			registrationForm.setPhoto(auditor.getPhoto());
	//			registrationForm.setEmail(auditor.getEmail());
	//			registrationForm.setPhone(auditor.getPhone());
	//			registrationForm.setCreditCard(auditor.getCreditCard());
	//			registrationForm.setAddress(auditor.getAddress());
	//			registrationForm.setPassword(auditor.getUserAccount().getPassword());
	//			registrationForm.setPatternPhone(false);
	//			final UserAccount userAccount = new UserAccount();
	//			userAccount.setUsername(auditor.getUserAccount().getUsername());
	//			userAccount.setPassword(auditor.getUserAccount().getPassword());
	//			registrationForm.setUserAccount(userAccount);
	//			registrationForm.setBrandName(creditCard.getBrandName());
	//			registrationForm.setHolderName(creditCard.getHolderName());
	//			registrationForm.setNumber(creditCard.getNumber());
	//			registrationForm.setExpirationMonth(creditCard.getExpirationMonth());
	//			registrationForm.setExpirationYear(creditCard.getExpirationYear());
	//			registrationForm.setCW(creditCard.getCW());
	//
	//			result = new ModelAndView("profile/editAuditor");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("action", "profile/edit-auditor.do");
	//			result.addObject("positions", allPositions);
	//
	//		} catch (final Exception e) {
	//			result = new ModelAndView("redirect:../../");
	//		}
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit-auditor", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editAuditor(@ModelAttribute("actor") final RegistrationFormAuditor registrationForm, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		Collection<Position> allPositions;
	//		Collection<Position> positionAssing;
	//		Collection<Position> positionsMe;
	//
	//		allPositions = this.positionService.findAll();
	//		positionAssing = this.positionService.getAllPositionAssing();
	//		final boolean eliminar = allPositions.removeAll(positionAssing);
	//
	//		final Auditor auditor2 = this.auditorService.findOne(this.auditorService.auditorUserAccount(LoginService.getPrincipal().getId()).getId());
	//		positionsMe = auditor2.getPositions();
	//		final boolean añadir = allPositions.addAll(positionsMe);
	//
	//		try {
	//
	//			final CreditCard creditCard = this.creditCardService.reconstruct(registrationForm, binding);
	//			registrationForm.setCreditCard(creditCard);
	//			final Auditor auditor = this.auditorService.reconstruct(registrationForm, binding);
	//
	//			if (!binding.hasErrors()) {
	//				final CreditCard creditCardSave = this.creditCardService.save(creditCard);
	//				auditor.setCreditCard(creditCardSave);
	//				this.auditorService.save(auditor);
	//
	//				result = new ModelAndView("redirect:personal-datas.do");
	//			} else {
	//				result = new ModelAndView("profile/editAuditor");
	//				result.addObject("actor", registrationForm);
	//				result.addObject("positions", allPositions);
	//
	//			}
	//		} catch (final Exception e) {
	//
	//			result = new ModelAndView("profile/editAuditor");
	//			result.addObject("actor", registrationForm);
	//			result.addObject("exception", e);
	//			result.addObject("positions", allPositions);
	//
	//		}
	//		return result;
	//
	//	}

}
