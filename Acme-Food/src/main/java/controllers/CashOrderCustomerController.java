
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
import domain.Actor;
import domain.CashOrder;

@Controller
@RequestMapping("/cashOrder/customer")
public class CashOrderCustomerController extends AbstractController {

	@Autowired
	private CashOrderService	cashOrderService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<CashOrder> cashOrders;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		cashOrders = this.cashOrderService.findByCustomer(a.getId());
		Assert.notNull(cashOrders);

		result = new ModelAndView("cashOrder/list");
		result.addObject("cashOrders", cashOrders);

		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer idCashOrder) {
		ModelAndView result;
		try {
			final CashOrder cashOrder;

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			cashOrder = this.cashOrderService.findOne(idCashOrder);
			Assert.notNull(cashOrder);
			Assert.isTrue(cashOrder.getCustomer().equals(a));

			result = new ModelAndView("cashOrder/show");
			result.addObject("cashOrder", cashOrder);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;

	}

}
