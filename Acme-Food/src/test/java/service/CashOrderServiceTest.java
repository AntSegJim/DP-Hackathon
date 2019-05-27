
package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.CashOrderRepository;
import repositories.CustomerRepository;
import repositories.DealerRepository;
import repositories.RestaurantRepository;
import services.CashOrderService;
import services.FoodDishesService;
import utilities.AbstractTest;
import domain.CashOrder;
import domain.FoodDishes;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CashOrderServiceTest extends AbstractTest {

	@Autowired
	private RestaurantRepository	restaurantRepository;
	@Autowired
	private CashOrderService		cashOrderService;
	@Autowired
	private CashOrderRepository		cashOrderRepository;
	@Autowired
	private CustomerRepository		customerRepository;
	@Autowired
	private DealerRepository		dealerRepository;
	@Autowired
	private FoodDishesService		foodDishService;


	@Test
	public void CreateCashOrderService() {
		final Object testingData[][] = {
			{//Positive test
				0, 1, new Date(), 12.0, this.getFutureDate(), 1, "tyrg-654387", super.getEntityId("customer1"), super.getEntityId("restaurant1"), super.getEntityId("foodDishes1"), 30.0, null
			}, {//Negative test: status out of range
				6, 1, new Date(), 12.0, this.getFutureDate(), 1, "tyrg-654387", super.getEntityId("customer1"), super.getEntityId("restaurant1"), super.getEntityId("foodDishes1"), 30.0, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateCashOrderTemplate((int) testingData[i][0], (int) testingData[i][1], (Date) testingData[i][2], (Double) testingData[i][3], (Date) testingData[i][4], (int) testingData[i][5], (String) testingData[i][6], (int) testingData[i][7],
				(int) testingData[i][8], (int) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}
	public Date getFutureDate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 70);
		final Date fechaSalida = calendar.getTime();
		return fechaSalida;
	}

	protected void CreateCashOrderTemplate(final int status, final int draftMode, final Date moment, final Double price, final Date senderMoment, final int choice, final String ticker, final int idCustomer, final int idRestaurant, final int idFoodDish,
		final Double minute, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("customer1");

			final CashOrder p = this.cashOrderService.create();

			p.setStatus(status);
			p.setDraftMode(draftMode);
			p.setMoment(moment);
			p.setTotalPrice(price);
			p.setSenderMoment(senderMoment);
			p.setChoice(choice);
			p.setTicker(ticker);
			p.setCustomer(this.customerRepository.findOne(idCustomer));
			p.setRestaurant(this.restaurantRepository.findOne(idRestaurant));

			final List<FoodDishes> food = new ArrayList<FoodDishes>();
			food.add(this.foodDishService.findOne(idFoodDish));

			p.setFoodDisheses(food);
			p.setMinutes(minute);

			this.cashOrderService.save(p);
			this.cashOrderRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	public void EditCashOrderCustomerService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("cashOrder1"), "customer1", 1, null
			}
		//			, {//Negative test: status out of range
		//				6, 1, new Date(), 12.0, this.getFutureDate(), 1, "tyrg-654387", super.getEntityId("customer1"), super.getEntityId("restaurant1"), super.getEntityId("foodDishes1"), 30.0, ConstraintViolationException.class
		//			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditCashOrderCustomerTemplate((int) testingData[i][0], (String) testingData[i][1], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void EditCashOrderCustomerTemplate(final int idCashOrder, final String authority, final int draftMode, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final CashOrder p = this.cashOrderService.findOne(idCashOrder);

			p.setDraftMode(draftMode);
			this.cashOrderService.save(p);
			this.cashOrderRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}