
package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import services.FoodDishesService;
import services.OfferService;
import utilities.AbstractTest;
import domain.FoodDishes;
import domain.Offer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfferServiceTest extends AbstractTest {

	@Autowired
	private OfferRepository		offerRepository;
	@Autowired
	private OfferService		offerService;
	@Autowired
	private FoodDishesService	foodDishesService;


	/*
	 * a) Requeriment:Manejar sus ofertas de comida, que incluye listarlas, crearlas,
	 * mostrarlas, modificarlas y eliminarlas. Para poder crear una oferta,
	 * esta debe estar formada por, al menos, dos platos. El precio total de la oferta
	 * debe ser menor que la suma de los precios de los platos que la contiene.
	 * 
	 * 
	 * b) Broken bussines rule: Un restaurante crea una oferta de comida con el atributo "title" vacio.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo create offer-> 7
	 * Sentencias metodo save offer-> 12
	 * Sentencias metodo findOne foodDishes-> 1 * 2 = 2
	 * Sentencias totales-> 21
	 * Sentence covegare positive test -> 21 (100%)
	 * Sentence covegare negative test -> 20 (95,23%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 4 atributos-> 25%
	 */
	@Test
	public void CreateOfferService() {
		final Object testingData[][] = {
			{//Positive test
				"title1", 4.0, super.getEntityId("foodDishes1"), super.getEntityId("foodDishes2"), null
			}, {//Negative test
				"", 4.0, super.getEntityId("foodDishes1"), super.getEntityId("foodDishes2"), ConstraintViolationException.class
			}, {//Negative test
				"a", -4.0, super.getEntityId("foodDishes1"), super.getEntityId("foodDishes2"), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateOfferTemplate((String) testingData[i][0], (Double) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void CreateOfferTemplate(final String title, final Double totalPrice, final int foodDishes1Id, final int foodDishes2Id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("restaurant");

			final Offer o = this.offerService.create();
			final FoodDishes foodDishes1 = this.foodDishesService.findOne(foodDishes1Id);
			final FoodDishes foodDishes2 = this.foodDishesService.findOne(foodDishes2Id);

			final List<FoodDishes> foodDishes = new ArrayList<>();
			Collections.addAll(foodDishes, foodDishes1, foodDishes2);
			o.setTitle(title);
			o.setTotalPrice(totalPrice);
			o.setFoodDisheses(foodDishes);

			this.offerService.save(o);
			this.offerRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment:Manejar sus ofertas de comida, que incluye listarlas, crearlas,
	 * mostrarlas, modificarlas y eliminarlas. Para poder crear una oferta,
	 * esta debe estar formada por, al menos, dos platos. El precio total de la oferta
	 * debe ser menor que la suma de los precios de los platos que la contiene.
	 * 
	 * 
	 * b) Broken bussines rule: Un restaurante intenta mostrar una oferta que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne offer-> 6
	 * Sentencias totales-> 6
	 * Sentence covegare positive test -> 6 (100%)
	 * Sentence covegare negative test -> 5 (83,33%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void ShowOfferService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("offer1"), null
			}, {//Positive test
				"restaurant", super.getEntityId("offer2"), IllegalArgumentException.class
			}, {//Positive test
				"restaurant1", super.getEntityId("offer1"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.ShowOfferTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void ShowOfferTemplate(final String authority, final int offerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);
			final Offer o = this.offerService.findOne(offerId);
			Assert.notNull(o);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment:Manejar sus ofertas de comida, que incluye listarlas, crearlas,
	 * mostrarlas, modificarlas y eliminarlas. Para poder crear una oferta,
	 * esta debe estar formada por, al menos, dos platos. El precio total de la oferta
	 * debe ser menor que la suma de los precios de los platos que la contiene.
	 * 
	 * 
	 * b) Broken bussines rule: Un restaurante crea una oferta de comida con el atributo "title" vacio.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne offer-> 6
	 * Sentencias metodo save offer-> 12
	 * Sentencias metodo findOne foodDishes-> 1 * 2 = 2
	 * Sentencias totales-> 20
	 * Sentence covegare positive test -> 20 (100%)
	 * Sentence covegare negative test -> 5 (25%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 6 atributos-> 16,66%
	 */
	@Test
	public void EditOfferService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("offer1"), "newTitle", 4.0, super.getEntityId("foodDishes1"), super.getEntityId("foodDishes2"), null
			}, {//Negative test
				"restaurant1", super.getEntityId("offer1"), "newTitle", 4.0, super.getEntityId("foodDishes1"), super.getEntityId("foodDishes2"), IllegalArgumentException.class
			}, {//Negative test
				"restaurant", super.getEntityId("offer1"), "<script>alert('hola');</script>", 4.0, super.getEntityId("foodDishes1"), super.getEntityId("foodDishes2"), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditOfferTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void EditOfferTemplate(final String authority, final int offerId, final String title, final Double totalPrice, final int foodDishes1Id, final int foodDishes2Id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Offer o = this.offerService.findOne(offerId);

			final FoodDishes foodDishes1 = this.foodDishesService.findOne(foodDishes1Id);
			final FoodDishes foodDishes2 = this.foodDishesService.findOne(foodDishes2Id);

			final List<FoodDishes> foodDishes = new ArrayList<>();
			Collections.addAll(foodDishes, foodDishes1, foodDishes2);
			o.setTitle(title);
			o.setTotalPrice(totalPrice);
			o.setFoodDisheses(foodDishes);

			this.offerService.save(o);
			this.offerRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment:Manejar sus ofertas de comida, que incluye listarlas, crearlas,
	 * mostrarlas, modificarlas y eliminarlas. Para poder crear una oferta,
	 * esta debe estar formada por, al menos, dos platos. El precio total de la oferta
	 * debe ser menor que la suma de los precios de los platos que la contiene.
	 * 
	 * 
	 * b) Broken bussines rule: Un restaurante intenta eliminar una oferta que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne offer-> 6
	 * Sentencias metodo delete offer-> 4
	 * Sentencias totales-> 10
	 * Sentence covegare positive test -> 10 (100%)
	 * Sentence covegare negative test -> 5 (50%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void DeleteOfferService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("offer1"), null
			}, {//Positive test
				"restaurant", super.getEntityId("offer2"), IllegalArgumentException.class
			}, {//Positive test
				"restaurant1", super.getEntityId("offer1"), NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteOfferTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void DeleteOfferTemplate(final String authority, final int offerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);
			final Offer o = this.offerService.findOne(offerId);
			this.offerService.delete(o);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
