
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
@RequestMapping("/cashOrder/restaurant")
public class CashOrderRestaurantController extends AbstractController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CashOrderService	cashOrderService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<CashOrder> cashOrders;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		cashOrders = this.cashOrderService.findByRestaurant(a.getId());
		Assert.notNull(cashOrders);

		result = new ModelAndView("cashOrder/list");
		result.addObject("cashOrders", cashOrders);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer cashOrderId) {
		ModelAndView result;
		try {
			final CashOrder cashOrder;

			cashOrder = this.cashOrderService.findOne(cashOrderId);
			Assert.notNull(cashOrder);

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			Assert.isTrue(cashOrder.getRestaurant().equals(a));

			result = new ModelAndView("cashOrder/edit2");
			result.addObject("cashOrder", cashOrder);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");

		}
		return result;

	}
}
