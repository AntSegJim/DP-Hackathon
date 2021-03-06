
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
import repositories.RatingRepository;
import repositories.RestaurantRepository;
import security.LoginService;
import services.CashOrderService;
import services.CustomerService;
import services.RatingService;
import utilities.AbstractTest;
import domain.CashOrder;
import domain.FoodDishes;
import domain.Rating;
import domain.Restaurant;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RatingServiceTest extends AbstractTest {

	@Autowired
	private RatingService			ratingService;
	@Autowired
	private RatingRepository		ratingRepository;
	@Autowired
	private RestaurantRepository	restaurantRepository;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private CashOrderService		cashOrderService;
	@Autowired
	private CashOrderRepository		cashOrderRepository;


	/*
	 * a) Requeriment: Create a rating.
	 * 
	 * b) Broken bussines rule:
	 * create a rating with bad score
	 * 
	 * c) Sentence coverage: 17/24 =70.83% (Only create & save Rating)
	 * 
	 * d) Data coverage: 33.33% (1 atributo incorrecto/3 atributos)
	 */

	@Test
	public void CreateRatingService() {
		final Object testingData[][] = {
			{//Positive test
				10, "Comentario", super.getEntityId("restaurant3"), "tyrg-654387", null
			}, {//Negative test: Valoration bigger than 10
				11, "Comentario", super.getEntityId("restaurant3"), "tyrg-654354", IllegalArgumentException.class
			}, {//Negative test: Negative valoration
				-1, "Comentario", super.getEntityId("restaurant3"), "tyrg-654334", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateRatingTemplate((int) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void CreateRatingTemplate(final Integer valoration, final String comentario, final Integer restaurantId, final String ticker, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("customer1");

			final CashOrder p = this.cashOrderService.create();
			final Restaurant restaurant = this.restaurantRepository.findOne(restaurantId);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, 70);
			final Date fechaSalida = calendar.getTime();
			p.setStatus(2);
			p.setDraftMode(0);
			p.setMoment(new Date());
			p.setTotalPrice(10.);
			p.setSenderMoment(fechaSalida);
			p.setChoice(1);
			p.setTicker(ticker);
			p.setCustomer(this.customerService.getCustomerUserAccount(LoginService.getPrincipal().getId()));
			p.setRestaurant(this.restaurantRepository.findOne(super.getEntityId("restaurant3")));

			final List<FoodDishes> food = new ArrayList<FoodDishes>();

			p.setFoodDisheses(food);
			p.setMinutes(30.);

			this.cashOrderService.save(p);
			this.cashOrderRepository.flush();

			final Rating r = this.ratingService.create();
			r.setComment(comentario);
			r.setValoration(valoration);
			r.setRestaurant(restaurant);
			r.setCustomer(this.customerService.getCustomerUserAccount(LoginService.getPrincipal().getId()));
			this.ratingService.save(r);
			this.ratingRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Edit a rating.
	 * 
	 * b) Broken bussines rule:
	 * edit a rating with emptu comment
	 * 
	 * c) Sentence coverage: 14/24 =58.33% (Only findOne & save Rating)
	 * 
	 * d) Data coverage: 33.33% (1 atributo incorrecto/3 atributos)
	 */
	@Test
	public void EditRatingService() {
		final Object testingData[][] = {
			{//Positive test
				4, "Comentario", super.getEntityId("rating1"), null
			}, {//Negative test: Comment empty
				2, "", super.getEntityId("rating2"), ConstraintViolationException.class
			}, {//Negative test: Negative valoration
				-2, "", super.getEntityId("rating2"), ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditRatingTemplate((int) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void EditRatingTemplate(final Integer valoration, final String comentario, final Integer ratingId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("customer1");

			final Rating r = this.ratingService.findOne(ratingId);
			r.setComment(comentario);
			r.setValoration(valoration);
			this.ratingService.save(r);
			this.ratingRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
