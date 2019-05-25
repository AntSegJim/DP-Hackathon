
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
	public ModelAndView list(@RequestParam final int cashOrderId) {
		final ModelAndView result;
		final Collection<Complain> complains;

		final UserAccount user = LoginService.getPrincipal();
		final Customer c = this.customerService.getCustomerUserAccount(user.getId());
		final CashOrder cashOrder = this.cashOrderService.findOne(cashOrderId);
		complains = this.complainService.getComplainsByCustomer(c.getId(), cashOrderId);

		result = new ModelAndView("complain/list");
		result.addObject("complains", complains);
		result.addObject("cashOrder", cashOrder);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int cashOrderId) {
		final ModelAndView result;
		final Complain complain;
		//		final UserAccount user = LoginService.getPrincipal();
		//		final Customer c = this.customerService.getCustomerUserAccount(user.getId());
		complain = this.complainService.create();
		final CashOrder cashOrder = this.cashOrderService.findOne(cashOrderId);
		Assert.notNull(complain);

		result = new ModelAndView("complain/edit");
		result.addObject("complain", complain);
		result.addObject("cashOrder", cashOrder);
		return result;
	}

}
