
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CashOrderRepository;
import repositories.RestaurantRepository;
import security.LoginService;
import security.UserAccount;
import domain.CashOrder;
import domain.Customer;
import domain.FoodDishes;
import domain.Restaurant;

@Service
@Transactional
public class CashOrderService {

	@Autowired
	private CashOrderRepository		cashOrderRepositoty;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RestaurantRepository	restaurantRepository;

	@Autowired
	private Validator				validator;


	public CashOrder create() {
		final CashOrder cashOrder = new CashOrder();

		cashOrder.setStatus(0);
		cashOrder.setDraftMode(1);
		cashOrder.setMoment(new Date());
		cashOrder.setTotalPrice(0.);
		cashOrder.setSenderMoment(this.cashOrderRepositoty.getMoreHour());
		cashOrder.setChoice(0);
		cashOrder.setTicker("");
		cashOrder.setCustomer(new Customer());
		cashOrder.setRestaurant(new Restaurant());
		cashOrder.setDealer(null);
		cashOrder.setFoodDisheses(new ArrayList<FoodDishes>());

		return cashOrder;
	}

	public CashOrder findOne(final Integer id) {
		return this.cashOrderRepositoty.findOne(id);
	}

	public Collection<CashOrder> findAll() {
		return this.cashOrderRepositoty.findAll();
	}

	public Collection<CashOrder> findByCustomer(final Integer id) {
		return this.cashOrderRepositoty.getCashOrderByCustomer(id);
	}

	public Collection<CashOrder> findByRestaurant(final Integer id) {
		return this.cashOrderRepositoty.getCashOrderByRestaurant(id);
	}

	public CashOrder save(final CashOrder cashOrder) {
		CashOrder res;
		final UserAccount user = LoginService.getPrincipal();
		if (cashOrder.getId() != 0 && user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER")) {
			final CashOrder older = this.cashOrderRepositoty.findOne(cashOrder.getId());
			Assert.isTrue(older.getDraftMode() == 1, "esta en draftMode");
		}

		cashOrder.setTotalPrice(this.getTotalPrice(cashOrder));

		res = this.cashOrderRepositoty.save(cashOrder);
		return res;
	}

	//RECONSTRUCT
	public CashOrder reconstruct(final CashOrder cashOrder, final BindingResult binding, final Integer id) {
		CashOrder res;

		if (cashOrder.getId() == 0) {
			res = cashOrder;

			final UserAccount user = LoginService.getPrincipal();
			final Customer cust = (Customer) this.actorService.getActorByUserAccount(user.getId());

			cashOrder.setCustomer(cust);
			cashOrder.setTicker(CashOrderService.generarTicker());
			cashOrder.setStatus(0);
			cashOrder.setMoment(new Date());
			cashOrder.setDealer(null);
			cashOrder.setTotalPrice(0.0);
			cashOrder.setRestaurant(this.restaurantRepository.findOne(id));

			if (!cashOrder.getSenderMoment().after(this.cashOrderRepositoty.getMoreHourR()))
				binding.rejectValue("senderMoment", "NoTime");

			this.validator.validate(res, binding);
			return res;
		} else {
			final UserAccount user = LoginService.getPrincipal();
			res = this.cashOrderRepositoty.findOne(cashOrder.getId());
			final CashOrder copy = new CashOrder();
			if (user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER")) {

				copy.setId(res.getId());
				copy.setVersion(res.getVersion());
				copy.setStatus(res.getStatus());
				copy.setMoment(res.getMoment());
				copy.setTicker(res.getTicker());
				copy.setCustomer(res.getCustomer());
				copy.setRestaurant(res.getRestaurant());
				copy.setDealer(res.getDealer());

				copy.setFoodDisheses(cashOrder.getFoodDisheses());
				copy.setDraftMode(cashOrder.getDraftMode());
				copy.setTotalPrice(this.getTotalPrice(cashOrder));
				copy.setSenderMoment(cashOrder.getSenderMoment());
				copy.setChoice(cashOrder.getChoice());

				if (!cashOrder.getSenderMoment().after(this.cashOrderRepositoty.getMoreHourR()))
					binding.rejectValue("senderMoment", "NoTime");

				this.validator.validate(copy, binding);

				return copy;
			} else
				return res;
		}
	}

	//TICKER
	public static String generarTicker() {
		final int tamLetras = 4;
		final int tam = 6;
		String d = "";
		final String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";

		for (int i = 0; i < tamLetras; i++) {
			final Integer random = (int) (Math.floor(Math.random() * letras.length()) % letras.length());
			d = d + letras.charAt(random);
		}

		String ticker = d + "-";
		final String a = "0123456789";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return ticker;

	}

	public Double getTotalPrice(final CashOrder cashOrder) {
		Double res = 0.0;
		try {
			if (cashOrder.getFoodDisheses().size() > 0)
				for (final FoodDishes food : cashOrder.getFoodDisheses())
					res = res + food.getPrice();
		} catch (final NullPointerException opps) {
			res = cashOrder.getTotalPrice();
		}
		return res;
	}
}
