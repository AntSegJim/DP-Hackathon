
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

import repositories.RestaurantRepository;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CashOrderService;
import services.FoodDishesService;
import services.OfferService;
import domain.Actor;
import domain.CashOrder;
import domain.FoodDishes;
import domain.Offer;
import domain.Restaurant;

@Controller
@RequestMapping("/cashOrder/customer")
public class CashOrderCustomerController extends AbstractController {

	@Autowired
	private CashOrderService		cashOrderService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RestaurantRepository	restaurantReposiroty;

	@Autowired
	private FoodDishesService		foodDishesService;

	@Autowired
	private OfferService			offerService;


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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer restaurantId) {
		ModelAndView result;

		final CashOrder cashOrder;
		Collection<FoodDishes> foodDishes;
		Collection<Offer> offers;
		Restaurant restaurant;

		cashOrder = this.cashOrderService.create();
		Assert.notNull(cashOrder);

		restaurant = this.restaurantReposiroty.findOne(restaurantId);
		foodDishes = this.foodDishesService.findFoodDishesByRestaurant(restaurant.getId());
		offers = this.offerService.getOffersByRestaurant(restaurantId);

		cashOrder.setRestaurant(restaurant);

		result = new ModelAndView("cashOrder/edit");
		result.addObject("cashOrder", cashOrder);
		result.addObject("foodDishes", foodDishes);
		result.addObject("offers", offers);
		result.addObject("restaurant", restaurant);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer cashOrderId) {
		ModelAndView result;
		try {
			final CashOrder cashOrder;
			Collection<FoodDishes> foodDishes;
			Collection<Offer> offers;
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			cashOrder = this.cashOrderService.findOne(cashOrderId);
			Assert.notNull(cashOrder);
			Assert.isTrue(cashOrder.getDraftMode() == 1);
			Assert.isTrue(cashOrder.getStatus() == 0);
			Assert.isTrue(cashOrder.getCustomer().equals(a));
			offers = this.offerService.getOffersByRestaurant(cashOrder.getRestaurant().getId());
			foodDishes = this.foodDishesService.findFoodDishesByRestaurant(cashOrder.getRestaurant().getId());

			result = new ModelAndView("cashOrder/edit2");
			result.addObject("cashOrder", cashOrder);
			result.addObject("foodDishes", foodDishes);
			result.addObject("offers", offers);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");

		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(final CashOrder cashOrder, final BindingResult binding, @RequestParam final Integer restaurantId) {
		ModelAndView result;

		try {
			final CashOrder pedido = this.cashOrderService.reconstruct(cashOrder, binding, restaurantId);

			final Integer dealers = this.restaurantReposiroty.getFreeDealerByRestaurant(pedido.getRestaurant().getId());
			if (dealers == 0 && pedido.getChoice() == 1)
				binding.rejectValue("choice", "NoFreeDealers");

			if (pedido.getSenderMoment() != null)
				if (!pedido.getSenderMoment().after(this.cashOrderService.fechaSumada(pedido.getRestaurant().getOrderTime())))
					binding.rejectValue("senderMoment", "NoTime");

			if (!binding.hasErrors()) {
				this.cashOrderService.save(pedido);
				result = new ModelAndView("redirect:list.do");
			} else {
				final Restaurant r = this.restaurantReposiroty.findOne(restaurantId);
				Collection<Offer> offers;
				Collection<FoodDishes> foodDishes;
				offers = this.offerService.getOffersByRestaurant(r.getId());
				foodDishes = this.foodDishesService.findFoodDishesByRestaurant(r.getId());
				result = new ModelAndView("cashOrder/edit");
				result.addObject("cashOrder", cashOrder);
				result.addObject("foodDishes", foodDishes);
				result.addObject("offers", offers);
				result.addObject("restaurant", r);
			}
		} catch (final Exception e) {
			final Restaurant r = this.restaurantReposiroty.findOne(restaurantId);

			Collection<FoodDishes> foodDishes;
			Collection<Offer> offers;
			offers = this.offerService.getOffersByRestaurant(restaurantId);
			foodDishes = this.foodDishesService.findFoodDishesByRestaurant(restaurantId);
			result = new ModelAndView("cashOrder/edit");
			result.addObject("cashOrder", cashOrder);
			result.addObject("foodDishes", foodDishes);
			result.addObject("offers", offers);
			result.addObject("exception", e);
			result.addObject("restaurant", r);

		}

		return result;
	}
	@RequestMapping(value = "/edit2", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final CashOrder cashOrder, final BindingResult binding) {
		ModelAndView result;

		try {
			final CashOrder pedido = this.cashOrderService.reconstruct(cashOrder, binding, null);

			final Integer dealers = this.restaurantReposiroty.getFreeDealerByRestaurant(pedido.getRestaurant().getId());
			if (dealers == 0 && pedido.getChoice() == 1)
				binding.rejectValue("choice", "NoFreeDealers");

			if (pedido.getSenderMoment() != null)
				if (!pedido.getSenderMoment().after(this.cashOrderService.fechaSumada(pedido.getRestaurant().getOrderTime())))
					binding.rejectValue("senderMoment", "NoTime");

			if (!binding.hasErrors()) {
				this.cashOrderService.save(pedido);
				result = new ModelAndView("redirect:list.do");
			} else {
				Collection<Offer> offers;
				Collection<FoodDishes> foodDishes;
				offers = this.offerService.getOffersByRestaurant(pedido.getRestaurant().getId());
				foodDishes = this.foodDishesService.findFoodDishesByRestaurant(pedido.getRestaurant().getId());
				result = new ModelAndView("cashOrder/edit2");
				result.addObject("cashOrder", cashOrder);
				result.addObject("foodDishes", foodDishes);
				result.addObject("offers", offers);
			}
		} catch (final Exception e) {
			final CashOrder pedido = this.cashOrderService.reconstruct(cashOrder, binding, null);

			Collection<Offer> offers;
			Collection<FoodDishes> foodDishes;
			offers = this.offerService.getOffersByRestaurant(pedido.getRestaurant().getId());
			foodDishes = this.foodDishesService.findFoodDishesByRestaurant(pedido.getRestaurant().getId());
			result = new ModelAndView("cashOrder/edit2");
			result.addObject("cashOrder", cashOrder);
			result.addObject("foodDishes", foodDishes);
			result.addObject("offers", offers);
			result.addObject("exception", e);
		}

		return result;
	}

	@RequestMapping(value = "/edit2", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final CashOrder cashOrder, final BindingResult binding) {
		ModelAndView result;

		try {
			final CashOrder pedido = this.cashOrderService.findOne(cashOrder.getId());

			if (!binding.hasErrors()) {
				this.cashOrderService.delete(pedido);
				result = new ModelAndView("redirect:list.do");
			} else {
				Collection<Offer> offers;
				Collection<FoodDishes> foodDishes;
				offers = this.offerService.getOffersByRestaurant(pedido.getRestaurant().getId());
				foodDishes = this.foodDishesService.findFoodDishesByRestaurant(pedido.getRestaurant().getId());
				result = new ModelAndView("cashOrder/edit2");
				result.addObject("cashOrder", cashOrder);
				result.addObject("foodDishes", foodDishes);
				result.addObject("offers", offers);
			}
		} catch (final Exception e) {
			final CashOrder pedido = this.cashOrderService.findOne(cashOrder.getId());

			Collection<Offer> offers;
			Collection<FoodDishes> foodDishes;
			offers = this.offerService.getOffersByRestaurant(pedido.getRestaurant().getId());
			foodDishes = this.foodDishesService.findFoodDishesByRestaurant(pedido.getRestaurant().getId());
			result = new ModelAndView("cashOrder/edit2");
			result.addObject("cashOrder", pedido);
			result.addObject("foodDishes", foodDishes);
			result.addObject("offers", offers);
			result.addObject("exception", e);
		}

		return result;
	}

}
