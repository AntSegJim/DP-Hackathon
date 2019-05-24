
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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
import domain.Actor;
import domain.CashOrder;
import domain.Complain;
import domain.Customer;
import domain.FoodDishes;
import domain.Offer;
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

	@Autowired
	private DealerService			dealerService;


	public CashOrder create() {
		final CashOrder cashOrder = new CashOrder();

		cashOrder.setStatus(0);
		cashOrder.setDraftMode(1);
		cashOrder.setMoment(new Date());
		cashOrder.setTotalPrice(0.);
		cashOrder.setSenderMoment(new Date());
		cashOrder.setChoice(0);
		cashOrder.setTicker("");
		cashOrder.setCustomer(new Customer());
		cashOrder.setRestaurant(new Restaurant());
		cashOrder.setDealer(null);
		cashOrder.setFoodDisheses(new ArrayList<FoodDishes>());
		cashOrder.setOffers(new HashSet<Offer>());
		cashOrder.setMinutes(0.0);
		cashOrder.setComplains(new HashSet<Complain>());

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

	public Collection<CashOrder> findByDealer(final Integer id) {
		return this.cashOrderRepositoty.getCashOrderByDealer(id);
	}

	public CashOrder save(final CashOrder cashOrder) {
		CashOrder res;
		final UserAccount user = LoginService.getPrincipal();
		if (cashOrder.getId() != 0 && user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER")) {
			Assert.isTrue(cashOrder.getCustomer().equals(this.actorService.getActorByUserAccount(user.getId())));
			Assert.isTrue(cashOrder.getStatus() == 0);
			final CashOrder older = this.cashOrderRepositoty.findOne(cashOrder.getId());
			Assert.isTrue(older.getDraftMode() == 1, "esta en draftMode");
		}

		else if (cashOrder.getId() != 0 && user.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT")) {
			Assert.isTrue(cashOrder.getRestaurant().equals(this.actorService.getActorByUserAccount(user.getId())));
			Assert.isTrue(cashOrder.getDraftMode() == 0);
			final CashOrder older = this.cashOrderRepositoty.findOne(cashOrder.getId());
			Assert.isTrue(older.getStatus() == 0);
			if (cashOrder.getChoice() == 0)
				Assert.isTrue(cashOrder.getDealer() == null);

			if (cashOrder.getChoice() == 1)
				if (cashOrder.getStatus() == 3) {
					Assert.isTrue(cashOrder.getDealer() != null);
					Assert.isTrue(cashOrder.getDealer().getRestaurant().equals(this.actorService.getActorByUserAccount(user.getId())));
				} else
					Assert.isTrue(cashOrder.getDealer() == null);

		} else if (cashOrder.getId() != 0 && user.getAuthorities().iterator().next().getAuthority().equals("DEALER")) {
			final CashOrder older = this.cashOrderRepositoty.findOne(cashOrder.getId());

			//Ser el mismo dealer que esta en el pedido
			Assert.isTrue(cashOrder.getDealer().equals(this.actorService.getActorByUserAccount(user.getId())));
			//Pertener al restaurante
			Assert.isTrue(cashOrder.getDealer().getRestaurant().equals(cashOrder.getRestaurant()));
			//Que el pedido este aceptado
			Assert.isTrue(older.getStatus() == 3);
			//Que este seleccionada la opcion de llevar a su domicilio
			Assert.isTrue(cashOrder.getChoice() == 1);
			//Que este fuera de draftMode
			Assert.isTrue(cashOrder.getDraftMode() == 0);
		}

		cashOrder.setTotalPrice(this.getTotalPrice(cashOrder));

		res = this.cashOrderRepositoty.save(cashOrder);
		return res;
	}

	public void delete(final CashOrder cashOrder) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		Assert.isTrue(cashOrder.getCustomer().equals(a));
		Assert.isTrue(cashOrder.getDraftMode() == 1);
		Assert.isTrue(cashOrder.getStatus() == 0);

		this.cashOrderRepositoty.delete(cashOrder);
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
			cashOrder.setMinutes(0.0);

			cashOrder.setRestaurant(this.restaurantRepository.findOne(id));

			if (cashOrder.getFoodDisheses() == null && cashOrder.getOffers() == null)
				binding.rejectValue("foodDisheses", "NoFood");

			if (cashOrder.getSenderMoment() == null)
				binding.rejectValue("senderMoment", "NoDate");

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
				copy.setMinutes(res.getMinutes());

				copy.setFoodDisheses(cashOrder.getFoodDisheses());
				copy.setOffers(cashOrder.getOffers());
				copy.setDraftMode(cashOrder.getDraftMode());
				copy.setTotalPrice(this.getTotalPrice(cashOrder));
				copy.setSenderMoment(cashOrder.getSenderMoment());
				copy.setChoice(cashOrder.getChoice());
				copy.setComplains(cashOrder.getComplains());

				if (cashOrder.getFoodDisheses() == null && cashOrder.getOffers() == null)
					binding.rejectValue("foodDisheses", "NoFood");

				if (cashOrder.getSenderMoment() == null)
					binding.rejectValue("senderMoment", "NoDate");

				this.validator.validate(copy, binding);

				return copy;
			} else if (user.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT")) {
				copy.setId(res.getId());
				copy.setVersion(res.getVersion());
				copy.setStatus(cashOrder.getStatus());
				copy.setMoment(res.getMoment());
				copy.setTicker(res.getTicker());
				copy.setCustomer(res.getCustomer());
				copy.setRestaurant(res.getRestaurant());
				copy.setDealer(cashOrder.getDealer());
				copy.setFoodDisheses(res.getFoodDisheses());
				copy.setComplains(res.getComplains());
				copy.setOffers(res.getOffers());
				copy.setDraftMode(res.getDraftMode());
				copy.setTotalPrice(res.getTotalPrice());
				copy.setSenderMoment(res.getSenderMoment());
				copy.setChoice(res.getChoice());
				copy.setOffers(res.getOffers());
				copy.setMinutes(res.getMinutes());

				if (copy.getChoice() == 1) {
					if (cashOrder.getStatus() == 3 && cashOrder.getDealer() == null)
						binding.rejectValue("dealer", "NoDealer");

					if (cashOrder.getStatus() == 1 && cashOrder.getDealer() != null)
						binding.rejectValue("dealer", "NoSelectedDealer");

					if (this.dealerService.getNumberCashOrderByDealer(cashOrder.getDealer().getId()) >= 3)
						binding.rejectValue("dealer", "	ManyCashOrder");

				}

				this.validator.validate(copy, binding);

				return copy;
			} else {
				copy.setId(res.getId());
				copy.setVersion(res.getVersion());
				copy.setStatus(cashOrder.getStatus());
				copy.setMoment(res.getMoment());
				copy.setTicker(res.getTicker());
				copy.setCustomer(res.getCustomer());
				copy.setRestaurant(res.getRestaurant());
				copy.setDealer(res.getDealer());
				copy.setFoodDisheses(res.getFoodDisheses());
				copy.setComplains(res.getComplains());
				copy.setOffers(res.getOffers());
				copy.setDraftMode(res.getDraftMode());
				copy.setTotalPrice(res.getTotalPrice());
				copy.setSenderMoment(res.getSenderMoment());
				copy.setChoice(res.getChoice());
				copy.setOffers(res.getOffers());
				copy.setMinutes(res.getMinutes());

				this.validator.validate(copy, binding);

				return copy;
			}
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
			if (cashOrder.getOffers().size() > 0)
				for (final Offer offer : cashOrder.getOffers())
					res = res + offer.getTotalPrice();
		} catch (final NullPointerException opps) {
			if (cashOrder.getFoodDisheses() == null && cashOrder.getOffers() != null)
				for (final Offer offer : cashOrder.getOffers())
					res = res + offer.getTotalPrice();
			else if (cashOrder.getOffers() == null && cashOrder.getFoodDisheses() != null)
				for (final FoodDishes food : cashOrder.getFoodDisheses())
					res = res + food.getPrice();
			else if (cashOrder.getFoodDisheses() == null && cashOrder.getOffers() == null)
				res = 0.0;

		}
		return res;
	}

	public Date fechaSumada(final Integer integer) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); //tuFechaBase es un Date;
		calendar.add(Calendar.MINUTE, integer); //minutosASumar es int.
		//lo que más quieras sumar
		final Date fechaSalida = calendar.getTime(); //Y ya tienes la fecha sumada.
		return fechaSalida;
	}

	public void actualizarMinutosPedidos(final Integer idRestaurant) {
		final Collection<CashOrder> cashOrders = this.findByRestaurant(idRestaurant);

		for (final CashOrder pedido : cashOrders) {
			final Double min = this.getMinutes(pedido.getSenderMoment(), new Date());
			if (min <= 0) {
				pedido.setMinutes(0.);
				pedido.setStatus(1);
				pedido.setSenderMoment(null);
				this.cashOrderRepositoty.save(pedido);
			} else {
				pedido.setMinutes(min);
				this.cashOrderRepositoty.save(pedido);
			}

		}
	}
	public Double getMinutes(final Date date1, final Date date2) {
		Double res = 0.0;
		res = (double) ((date1.getTime() - date2.getTime()) / 60000);
		return res;

	}
}
