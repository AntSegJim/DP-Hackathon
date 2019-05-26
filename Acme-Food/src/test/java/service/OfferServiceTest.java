
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import services.OfferService;
import services.RestaurantService;
import utilities.AbstractTest;
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
	private RestaurantService	restaurantService;
	@Autowired
	private OfferService		offerService;


	/*
	 * @Test
	 * public void CreateOfferService() {
	 * final Object testingData[][] = {
	 * {//Positive test
	 * "title1", 4.0, super.getEntityId("restaurant1"), null
	 * }, {//Negative test
	 * "", "nameSocialProfile1", "http://lol.com", super.getEntityId("restaurant1"), ConstraintViolationException.class
	 * }
	 * };
	 * 
	 * for (int i = 0; i < testingData.length; i++)
	 * this.CreateOfferTemplate((String) testingData[i][0], (Double) testingData[i][1], (Collection<FoodDishes>) testingData[i][2], (Class<?>) testingData[i][3]);
	 * }
	 * protected void CreateOfferTemplate(final String title, final Double totalPrice, final Collection<FoodDishes> foodDihes, final Class<?> expected) {
	 * Class<?> caught;
	 * 
	 * caught = null;
	 * try {
	 * super.authenticate("restaurant");
	 * 
	 * final SocialProfile sp = this.socialProfileService.create();
	 * final Restaurant r = this.restaurantService.findOne(restaurantId);
	 * 
	 * sp.setNickName(nickName);
	 * sp.setNameSocialNetwork(nameSocialNetwork);
	 * sp.setUrl(url);
	 * sp.setRestaurant(r);
	 * 
	 * this.socialProfileService.save(sp);
	 * this.socialProfileRepository.flush();
	 * 
	 * super.authenticate(null);
	 * } catch (final Throwable oops) {
	 * caught = oops.getClass();
	 * }
	 * this.checkExceptions(expected, caught);
	 * }
	 */

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
