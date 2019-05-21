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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CreditCardService;
import services.CustomizableSystemService;
import domain.Administrator;
import forms.RegistrationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//	@Autowired
	//	private PositionService				positionService;
	//
	//	@Autowired
	//	private ApplicationService			applicationService;
	//
	//	@Autowired
	//	private CompanyService				companyService;
	//
	//	@Autowired
	//	private HackerService				hackerService;
	//
	//	@Autowired
	//	private CurriculaService			curriculaService;
	//
	//	@Autowired
	//	private FinderService				finderService;
	//
	@Autowired
	private CreditCardService			creditCardService;
	//
	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private AdministratorService		administratorService;


	//
	//	@Autowired
	//	private ProviderService				providerService;
	//
	//	@Autowired
	//	private ItemService					itemService;
	//
	//	@Autowired
	//	private AuditService				auditService;
	//
	//
	//	// Constructors -----------------------------------------------------------
	//
	//	public AdministratorController() {
	//		super();
	//	}
	//
	//	@RequestMapping("/dashboard")
	//	public ModelAndView dashboard() {
	//		final ModelAndView result;
	//
	//		final List<Object[]> getAvgMinMaxDesvPositionByCompany = this.positionService.getAvgMinMaxDesvPositionByCompany();
	//		final Double getAvgPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[0];
	//		final Double getMinPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[1];
	//		final Double getMaxPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[2];
	//		final Double getDesvPositionByCompany = (Double) getAvgMinMaxDesvPositionByCompany.get(0)[3];
	//
	//		final List<Object[]> getAvgMinMaxDesvAppByHackers = this.applicationService.getAvgMinMaxDesvAppByHackers();
	//		final Double getAvgAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[0];
	//		final Double getMinAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[1];
	//		final Double getMaxAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[2];
	//		final Double getDesvAppByHackers = (Double) getAvgMinMaxDesvAppByHackers.get(0)[3];
	//
	//		final List<String> getCompaniesWithMorePositions = this.companyService.getCompaniesWithMorePositions();
	//
	//		final List<String> getHackersWithMoreApplications = this.hackerService.getHackersWithMoreApplications();
	//
	//		final List<Object[]> getAvgMaxMinDesvSalaryOfPositions = this.positionService.getAvgMaxMinDesvSalaryOfPositions();
	//		final Double getAvgSalaryOfPositions = (Double) getAvgMaxMinDesvSalaryOfPositions.get(0)[0];
	//		final Double getMinSalaryOfPositions = (Double) getAvgMaxMinDesvSalaryOfPositions.get(0)[1];
	//		final Double getMaxSalaryOfPositions = (Double) getAvgMaxMinDesvSalaryOfPositions.get(0)[2];
	//		final Double getDesvSalaryOfPositions = (Double) getAvgMaxMinDesvSalaryOfPositions.get(0)[3];
	//
	//		final List<String> getPositionWithBestSalary = this.positionService.getPositionWithBestSalary();
	//		final List<String> getPositionWithWorstSalary = this.positionService.getPositionWithWorstSalary();
	//
	//		final List<Object[]> getAvgMinMaxDesvScoreOfAudit = this.auditService.getAvgMinMaxDesvScoreOfAudit();
	//		final Double getAvgScoreOfAudit = (Double) getAvgMinMaxDesvScoreOfAudit.get(0)[0];
	//		final Integer getMinScoreOfAudit = (Integer) getAvgMinMaxDesvScoreOfAudit.get(0)[1];
	//		final Integer getMaxScoreOfAudit = (Integer) getAvgMinMaxDesvScoreOfAudit.get(0)[2];
	//		final Double getDesvScoreOfAudit = (Double) getAvgMinMaxDesvScoreOfAudit.get(0)[3];
	//
	//		final List<Object[]> getAvgMinMaxDesvScoreOfAuditByCompany = this.auditService.getAvgMinMaxDesvScoreOfAuditByCompany();
	//		final Double getAvgScoreOfAuditByCompany = (Double) getAvgMinMaxDesvScoreOfAuditByCompany.get(0)[0];
	//		final Double getMinScoreOfAuditByCompany = (Double) getAvgMinMaxDesvScoreOfAuditByCompany.get(0)[1];
	//		final Double getMaxScoreOfAuditByCompany = (Double) getAvgMinMaxDesvScoreOfAuditByCompany.get(0)[2];
	//		final Double getDesvScoreOfAuditByCompany = (Double) getAvgMinMaxDesvScoreOfAuditByCompany.get(0)[3];
	//
	//		final Collection<String> getCompaniesWithHighestScore = this.companyService.getCompaniesWithHighestScore();
	//
	//		final Collection<Double> getAvgSalaryOfCompaniesWithHighScore = this.positionService.getAverageSalaryOfCompaniesWithHighScore();
	//
	//		final List<Object[]> getAvgMaxMinDesvItemProvider = this.itemService.getAvgMinMaxDesvNumberItemByProvider();
	//		final Double getAvgItemProvider = (Double) getAvgMaxMinDesvItemProvider.get(0)[0];
	//		final Double getMinItemProvider = (Double) getAvgMaxMinDesvItemProvider.get(0)[1];
	//		final Double getMaxItemProvider = (Double) getAvgMaxMinDesvItemProvider.get(0)[2];
	//		final Double getDesvItemProvider = (Double) getAvgMaxMinDesvItemProvider.get(0)[3];
	//
	//		final List<String> top5Providers = this.providerService.getTop5Providers();
	//
	//		result = new ModelAndView("administrator/dashboard");
	//
	//		result.addObject("getAvgPositionByCompany", getAvgPositionByCompany);
	//		result.addObject("getMinPositionByCompany", getMinPositionByCompany);
	//		result.addObject("getMaxPositionByCompany", getMaxPositionByCompany);
	//		result.addObject("getDesvPositionByCompany", getDesvPositionByCompany);
	//
	//		result.addObject("getAvgAppByHackers", getAvgAppByHackers);
	//		result.addObject("getMinAppByHackers", getMinAppByHackers);
	//		result.addObject("getMaxAppByHackers", getMaxAppByHackers);
	//		result.addObject("getDesvAppByHackers", getDesvAppByHackers);
	//
	//		result.addObject("getCompaniesWithMorePositions", getCompaniesWithMorePositions);
	//
	//		result.addObject("getHackersWithMoreApplications", getHackersWithMoreApplications);
	//
	//		result.addObject("getAvgSalaryOfPositions", getAvgSalaryOfPositions);
	//		result.addObject("getMinSalaryOfPositions", getMinSalaryOfPositions);
	//		result.addObject("getMaxSalaryOfPositions", getMaxSalaryOfPositions);
	//		result.addObject("getDesvSalaryOfPositions", getDesvSalaryOfPositions);
	//
	//		result.addObject("getPositionWithBestSalary", getPositionWithBestSalary);
	//		result.addObject("getPositionWithWorstSalary", getPositionWithWorstSalary);
	//
	//		result.addObject("curricula", this.curriculaService.getMinMaxAvgDesvCurriculaPerHacker());
	//		result.addObject("resultsFinder", this.finderService.getMinMaxAvgDesvResultsFinder());
	//		result.addObject("emptyVSnotEmpty", this.finderService.ratioEmptyNotEmtpyFinder());
	//
	//		result.addObject("getAvgScoreOfAudit", getAvgScoreOfAudit);
	//		result.addObject("getMinScoreOfAudit", getMinScoreOfAudit);
	//		result.addObject("getMaxScoreOfAudit", getMaxScoreOfAudit);
	//		result.addObject("getDesvScoreOfAudit", getDesvScoreOfAudit);
	//
	//		result.addObject("getAvgScoreOfAuditByCompany", getAvgScoreOfAuditByCompany);
	//		result.addObject("getMinScoreOfAuditByCompany", getMinScoreOfAuditByCompany);
	//		result.addObject("getMaxScoreOfAuditByCompany", getMaxScoreOfAuditByCompany);
	//		result.addObject("getDesvScoreOfAuditByCompany", getDesvScoreOfAuditByCompany);
	//
	//		result.addObject("getCompaniesWithHighestScore", getCompaniesWithHighestScore);
	//
	//		result.addObject("getAvgSalaryOfCompaniesWithHighScore", getAvgSalaryOfCompaniesWithHighScore);
	//
	//		result.addObject("getAvgItemProvider", getAvgItemProvider);
	//		result.addObject("getMinItemProvider", getMinItemProvider);
	//		result.addObject("getMaxItemProvider", getMaxItemProvider);
	//		result.addObject("getDesvItemProvider", getDesvItemProvider);
	//
	//		result.addObject("top5Providers", top5Providers);
	//
	//		return result;
	//	}
	//
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
