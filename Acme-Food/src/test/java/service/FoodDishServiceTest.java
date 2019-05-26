
package service;

import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.FoodDishesRepository;
import services.FoodDishesService;
import services.RestaurantService;
import utilities.AbstractTest;
import domain.FoodDishes;
import domain.Restaurant;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FoodDishServiceTest extends AbstractTest {

	@Autowired
	private RestaurantService		restaurantService;

	@Autowired
	private FoodDishesService		foodDishesService;
	@Autowired
	private FoodDishesRepository	foodDishesRepository;


	@Test
	public void CreateFoodDishService() {
		final Object testingData[][] = {
			{//Positive test
				"Nueva comida1", "Decription1", "https://www.imagen.com.mx/assets/img/imagen_share.png", 12.0, 0, super.getEntityId("restaurant1"), null
			}, {//Negative test: name vacio
				"", "Decription2", "https://www.imagen.com.mx/assets/img/imagen_share.png", 10.0, 1, super.getEntityId("restaurant1"), ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateFoodDishTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void CreateFoodDishTemplate(final String name, final String description, final String urlPicture, final Double price, final int type, final int idRestaurant, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("restaurant");

			final Restaurant c = this.restaurantService.findOne(idRestaurant);

			final FoodDishes p = this.foodDishesService.create();
			p.setRestaurant(c);
			p.setIngredients(new HashSet<String>());

			p.setName(name);
			p.setDescription(description);
			p.setPictures(urlPicture);
			p.setPrice(price);
			p.setType(type);

			this.foodDishesService.save(p);
			this.foodDishesRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void EditFoodDishService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("foodDishes1"), 100.0, null
			}, {//Negative test: precio negativo
				"restaurant", super.getEntityId("foodDishes1"), -10.0, ConstraintViolationException.class
			}, {//Negative test: no es su restaurant
				"restaurant1", super.getEntityId("foodDishes1"), 50.3, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditFoodDishTemplate((String) testingData[i][0], (int) testingData[i][1], (Double) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void EditFoodDishTemplate(final String authority, final int idFoodDish, final Double newPrice, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final FoodDishes p = this.foodDishesService.findOne(idFoodDish);
			p.setPrice(newPrice);
			this.foodDishesService.save(p);
			this.foodDishesRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void DeleteFoodDishService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("foodDishes1"), null
			}
		//			, {//Negative test: Case 2
		//				"restaurant2", super.getEntityId("foodDishes1"), IllegalArgumentException.class
		//			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteFoodDishServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeleteFoodDishServiceTemplate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final FoodDishes p = this.foodDishesService.findOne(id);
			this.foodDishesService.delete(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
