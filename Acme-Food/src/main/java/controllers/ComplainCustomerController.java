
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CashOrderService;
import services.ComplainService;
import services.CustomerService;
import domain.CashOrder;
import domain.Complain;
import domain.Customer;

@Controller
@RequestMapping("/complain/customer")
public class ComplainCustomerController extends AbstractController {

	@Autowired
	private ComplainService		complainService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ActorService		actorS;
	@Autowired
	private CashOrderService	cashOrderService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Complain> complains;

		final UserAccount user = LoginService.getPrincipal();
		final Customer c = this.customerService.getCustomerUserAccount(user.getId());
		complains = this.complainService.getComplainsByCustomer(c.getId());

		result = new ModelAndView("complain/list");
		result.addObject("complains", complains);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Complain complain;
		final Collection<CashOrder> cashOrders;
		complain = this.complainService.create();
		cashOrders = this.cashOrderService.getCashOrdersAccepted();
		Assert.notNull(complain);
		Assert.notNull(cashOrders);

		result = new ModelAndView("complain/edit");
		result.addObject("complain", complain);
		result.addObject("cashOrders", cashOrders);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complainId) {
		ModelAndView result;
		final Complain complain;
		final Collection<CashOrder> cashOrders;
		try {
			complain = this.complainService.findOne(complainId);
			cashOrders = this.cashOrderService.getCashOrdersAccepted();
			Assert.notNull(complain);
			Assert.notNull(cashOrders);
			result = new ModelAndView("complain/edit");
			result.addObject("complain", complain);
			result.addObject("cashOrders", cashOrders);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Complain complain, final BindingResult binding) {
		ModelAndView result;
		Complain c;
		try {
			c = this.complainService.reconstruct(complain, binding);
			if (!binding.hasErrors()) {
				this.complainService.save(c);
				result = new ModelAndView("redirect:list.do");
			} else {
				final Collection<CashOrder> cashOrders;
				cashOrders = this.cashOrderService.getCashOrdersAccepted();
				result = new ModelAndView("complain/edit");
				result.addObject("complain", complain);
				result.addObject("cashOrders", cashOrders);
			}
		} catch (final ValidationException opps) {
			final Collection<CashOrder> cashOrders;
			cashOrders = this.cashOrderService.getCashOrdersAccepted();
			result = new ModelAndView("complain/edit");
			result.addObject("complain", complain);
			result.addObject("cashOrders", cashOrders);
		} catch (final Exception e) {
			final Collection<CashOrder> cashOrders;
			cashOrders = this.cashOrderService.getCashOrdersAccepted();
			result = new ModelAndView("complain/edit");
			result.addObject("exception", e);
			result.addObject("complain", complain);
			result.addObject("cashOrders", cashOrders);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Complain complain, final BindingResult binding) {
		ModelAndView result;
		try {
			final Complain c = this.complainService.reconstruct(complain, binding);
			if (!binding.hasErrors()) {
				this.complainService.delete(c);
				result = new ModelAndView("redirect:list.do");
			} else {
				final Collection<CashOrder> cashOrders;
				cashOrders = this.cashOrderService.getCashOrdersAccepted();
				result = new ModelAndView("complain/edit");
				result.addObject("complain", complain);
				result.addObject("cashOrders", cashOrders);
			}
		} catch (final Exception e) {
			final Collection<CashOrder> cashOrders;
			cashOrders = this.cashOrderService.getCashOrdersAccepted();
			result = new ModelAndView("complain/edit");
			result.addObject("exception", e);
			result.addObject("complain", complain);
			result.addObject("cashOrders", cashOrders);
		}
		return result;
	}
}
