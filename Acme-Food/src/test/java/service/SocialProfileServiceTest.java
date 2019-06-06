
package service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import services.RestaurantService;
import services.SocialProfileService;
import utilities.AbstractTest;
import domain.Restaurant;
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private RestaurantService		restaurantService;
	@Autowired
	private SocialProfileRepository	socialProfileRepository;


	/*
	 * a) Requeriment: Manejar sus perfiles sociales, que incluye listarlos,
	 * mostrarlos, crearlos, modificarlos y eliminarlos.
	 * 
	 * 
	 * b) Broken bussines rule: Un Restaurante crea un perdil social con el atributo "nickName" vacio.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo create socialProfile-> 7
	 * Sentencias metodo save socialProfile-> 5
	 * Sentencias totales-> 12
	 * Sentence covegare positive test -> 12 (100%)
	 * Sentence covegare negative test -> 10 (83,33%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 4 atributos-> 25%
	 */
	@Test
	public void CreateSocialProfileService() {
		final Object testingData[][] = {
			{//Positive test
				"nickName1", "nameSocialNetwork1", "http://lol.com", super.getEntityId("restaurant1"), null
			}, {//Negative test
				"", "nameSocialProfile1", "http://lol.com", super.getEntityId("restaurant1"), ConstraintViolationException.class
			}, {//Negative test
				"<script>alert('hola');</script>", "nameSocialProfile1", "http://lol.com", super.getEntityId("restaurant1"), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateSocialProfileTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void CreateSocialProfileTemplate(final String nickName, final String nameSocialNetwork, final String url, final int restaurantId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("restaurant");

			final SocialProfile sp = this.socialProfileService.create();
			final Restaurant r = this.restaurantService.findOne(restaurantId);

			sp.setNickName(nickName);
			sp.setNameSocialNetwork(nameSocialNetwork);
			sp.setUrl(url);
			sp.setRestaurant(r);

			this.socialProfileService.save(sp);
			this.socialProfileRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manejar sus perfiles sociales, que incluye listarlos,
	 * mostrarlos, crearlos, modificarlos y eliminarlos.
	 * 
	 * 
	 * b) Broken bussines rule: Un restaurante intenta mostrar un perfil social que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne socialProfile-> 6
	 * Sentencias totales-> 6
	 * Sentence covegare positive test -> 6 (100%)
	 * Sentence covegare negative test -> 5 (83,33%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void ShowSocialProfileService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("socialProfile1"), null
			}, {//Negative test
				"restaurant", super.getEntityId("socialProfile3"), IllegalArgumentException.class
			}, {//Negative test
				"restaurant1", super.getEntityId("socialProfile1"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.ShowSocialProfileTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void ShowSocialProfileTemplate(final String authority, final int socialProfileId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);
			final SocialProfile sp = this.socialProfileService.findOne(socialProfileId);
			Assert.notNull(sp);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manejar sus perfiles sociales, que incluye listarlos,
	 * mostrarlos, crearlos, modificarlos y eliminarlos.
	 * 
	 * 
	 * b) Broken bussines rule: Un restaurante intenta editar un perfil social que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne socialProfile-> 6
	 * Sentencias metodo save socialProfile-> 5
	 * Sentencias totales-> 11
	 * Sentence covegare positive test -> 11 (100%)
	 * Sentence covegare negative test -> 5 (45,45%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 5 atributos-> 20%
	 */
	@Test
	public void EditSocialProfileService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("socialProfile1"), "nickName1", "nameSocialNetwork1", "http://lol.com", null
			}, {//Negative test
				"restaurant1", super.getEntityId("socialProfile1"), "nickName1", "nameSocialNetwork1", "http://lol.com", IllegalArgumentException.class
			}, {//Negative test
				"restaurant", super.getEntityId("socialProfile1"), "<script>alert('hola');</script>", "nameSocialNetwork1", "http://lol.com", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditSocialProfileTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void EditSocialProfileTemplate(final String authority, final int socialProfileId, final String nickName, final String nameSocialNetwork, final String url, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final SocialProfile sp = this.socialProfileService.findOne(socialProfileId);

			sp.setNickName(nickName);
			sp.setNameSocialNetwork(nameSocialNetwork);
			sp.setUrl(url);

			this.socialProfileService.save(sp);
			this.socialProfileRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manejar sus perfiles sociales, que incluye listarlos,
	 * mostrarlos, crearlos, modificarlos y eliminarlos.
	 * 
	 * 
	 * b) Broken bussines rule: Un restaurante intenta eliminar un perfil social que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne socialProfile-> 6
	 * Sentencias metodo delete socialProfile-> 4
	 * Sentencias totales-> 10
	 * Sentence covegare positive test -> 10 (100%)
	 * Sentence covegare negative test -> 5 (50%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void DeleteSocialProfileService() {
		final Object testingData[][] = {
			{//Positive test
				"restaurant", super.getEntityId("socialProfile1"), null
			}, {//Negative test
				"restaurant", super.getEntityId("socialProfile3"), IllegalArgumentException.class
			}, {//Negative test
				"restaurant1", super.getEntityId("socialProfile2"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteSocialProfileTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void DeleteSocialProfileTemplate(final String authority, final int socialProfileId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);
			final SocialProfile sp = this.socialProfileService.findOne(socialProfileId);
			this.socialProfileService.delete(sp);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
