
package controllers;

import java.util.Collection;

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
import domain.Actor;
import domain.CashOrder;

@Controller
@RequestMapping("/cashOrder/dealer")
public class CashOrderDealerController {

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

		cashOrders = this.cashOrderService.findByDealer(a.getId());
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
			Assert.isTrue(cashOrder.getStatus() == 3);

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			Assert.isTrue(cashOrder.getDealer().equals(a));

			result = new ModelAndView("cashOrder/edit2");
			result.addObject("cashOrder", cashOrder);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");

		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final CashOrder cashOrder, final BindingResult binding) {
		ModelAndView result;

		try {
			final CashOrder pedido = this.cashOrderService.reconstruct(cashOrder, binding, null);
			if (!binding.hasErrors()) {
				this.cashOrderService.save(pedido);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("cashOrder/edit2");
				result.addObject("cashOrder", pedido);
			}
		} catch (final Exception e) {
			final CashOrder pedido = this.cashOrderService.reconstruct(cashOrder, binding, null);

			result = new ModelAndView("cashOrder/edit2");
			result.addObject("cashOrder", pedido);
			result.addObject("exception", e);
		}

		return result;
	}
}
