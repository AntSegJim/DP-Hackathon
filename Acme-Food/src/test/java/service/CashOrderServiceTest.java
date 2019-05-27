
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
			}, {//Negative test: draftMode out of range
				super.getEntityId("cashOrder1"), "customer1", 9, null
			}

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

	public void EditCashOrderRestaurantService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("cashOrder1"), "restaurant", 3, null
			}, {//Negative test: status out of range
				super.getEntityId("cashOrder1"), "restaurant1", 3, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditCashOrderRestaurantTemplate((int) testingData[i][0], (String) testingData[i][1], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void EditCashOrderRestaurantTemplate(final int idCashOrder, final String authority, final int status, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final CashOrder p = this.cashOrderService.findOne(idCashOrder);

			p.setStatus(status);
			this.cashOrderService.save(p);
			this.cashOrderRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	public void EditCashOrderDealerService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("cashOrder2"), "dealer", 2, null
			}, {//Negative test: order without dealer
				super.getEntityId("cashOrder1"), "dealer", 2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditCashOrderRestaurantTemplate((int) testingData[i][0], (String) testingData[i][1], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void EditCashOrderDealerService(final int idCashOrder, final String authority, final int status, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final CashOrder p = this.cashOrderService.findOne(idCashOrder);

			p.setStatus(status);
			this.cashOrderService.save(p);
			this.cashOrderRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	public void DeleteCashOrderService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("cashOrder1"), "customer1", null
			}, {//Negative test: is not the same customer to make the order
				super.getEntityId("cashOrder1"), "customer2", IllegalArgumentException.class
			}, {//Negative test: order in safe mode
				super.getEntityId("cashOrder2"), "customer2", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteCashOrderTemplate((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][3]);
	}

	protected void DeleteCashOrderTemplate(final int idCashOrder, final String authority, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final CashOrder p = this.cashOrderService.findOne(idCashOrder);

			this.cashOrderService.delete(p);
			this.cashOrderRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
