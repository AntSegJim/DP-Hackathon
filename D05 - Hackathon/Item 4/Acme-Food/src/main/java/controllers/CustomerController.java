
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.CustomerService;
import services.CustomizableSystemService;
import domain.CreditCard;
import domain.Customer;
import forms.RegistrationFormCustomerAndCreditCard;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerService				customerService;
	@Autowired
	private CreditCardService			creditCardService;
	@Autowired
	private CustomizableSystemService	customizableService;


	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView customer() {
	//		final ModelAndView result;
	//		final Collection<Customer> customers;
	//
	//		customers= this.customerService.findAll();
	//		Assert.notNull(customers);
	//
	//		result = new ModelAndView("customer/list");
	//		result.addObject("customers", customers);
	//		return result;
	//
	//	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormCustomerAndCreditCard registrationForm = new RegistrationFormCustomerAndCreditCard();

		registrationForm = registrationForm.createToCustomerAndCreditCard();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("customer/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormCustomerAndCreditCard registrationForm, final BindingResult binding) {
		ModelAndView result;
		Customer customer = null;
		CreditCard creditcard = null;
		CreditCard creditCardSave = null;

		try {
			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			customer = this.customerService.reconstruct(registrationForm, binding);

			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				creditCardSave = this.creditCardService.save(creditcard);
				customer.setCreditCard(creditCardSave);
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("customer/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {
			final Collection<String> creditCardsNumbers = this.creditCardService.getAllNumbers();
			if (creditcard != null)
				if (creditCardsNumbers.contains(creditcard.getNumber()) && creditCardSave.equals((this.creditCardService.getCreditCardByNumber(creditcard.getNumber()))))
					this.creditCardService.delete(creditCardSave);
			result = new ModelAndView("customer/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}
}
