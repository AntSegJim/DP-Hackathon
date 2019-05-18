
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CashOrderRepository;
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
	private CashOrderRepository	cashOrderRepositoty;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	public CashOrder create() {
		final CashOrder cashOrder = new CashOrder();

		cashOrder.setStatus(0);
		cashOrder.setDraftMode(1);
		cashOrder.setMoment(new Date());
		cashOrder.setTotalPrice(0.);
		cashOrder.setSenderMoment(null);
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

		res = this.cashOrderRepositoty.save(cashOrder);
		return res;
	}

	//RECONSTRUCT
	public CashOrder reconstruct(final CashOrder cashOrder, final BindingResult binding) {
		CashOrder res;

		//if....
		res = cashOrder;

		final UserAccount user = LoginService.getPrincipal();
		final Customer cust = (Customer) this.actorService.getActorByUserAccount(user.getId());

		cashOrder.setCustomer(cust);
		cashOrder.setTicker(CashOrderService.generarTicker());
		cashOrder.setStatus(0);
		cashOrder.setDraftMode(1);
		cashOrder.setMoment(new Date());
		cashOrder.setDealer(null);

		this.validator.validate(res, binding);

		return res;
	}

	//TICKER
	public static String generarTicker() {
		final int tamLetras = 4;
		final int tam = 6;
		String d = "";
		final String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";

		for (int i = 0; i < tamLetras; i++) {
			final Integer random = (int) (Math.floor(Math.random() * letras.length()) % letras.length());
			d = d + d.charAt(random);
		}

		String ticker = d + "-";
		final String a = "0123456789";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return ticker;

	}
}
