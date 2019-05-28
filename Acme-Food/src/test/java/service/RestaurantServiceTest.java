
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.RestaurantService;
import utilities.AbstractTest;
import domain.Restaurant;
import forms.RegistrationFormRestaurant;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RestaurantServiceTest extends AbstractTest {

	@Autowired
	private RestaurantService	restaurantService;


	/*
	 * a) Requeriment: An actor who is not authenticated
	 * must be able to create user acconunt.
	 * 
	 * b) Broken bussines rule:
	 * Se intenta crear un nuevo restaurant sin email
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save del RestaurantService,
	 * el total de lineas sumando estos metodos es de 105, de las cuales este test recorrer 61 , es decir un 58'09%.
	 * 
	 * d) Data coverage: 10% (1 atributo incorrecto/10 atributos)
	 */

	@Test
	public void CreateRestaurantService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoComercialName", "NuevaEspecialidad", 30, null
			}, {//Negative test: email vacio
				"Nuevo Nombre", "Apellido", "ES12345678X", "", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoComercialName", "NuevaEspecialidad", 30, NullPointerException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateRestaurantTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Integer) testingData[i][9], (Class<?>) testingData[i][10]);
	}
	protected void CreateRestaurantTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final String comercialName,
		final String speciality, final Integer orderTime, final Class<?> expected) {
		Class<?> caught;
		Restaurant restaurant = null;

		caught = null;
		try {

			RegistrationFormRestaurant registrationForm = new RegistrationFormRestaurant();

			registrationForm = registrationForm.createToRestaurant();

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.setComercialName(comercialName);
			registrationForm.setSpeciality(speciality);
			registrationForm.setOrderTime(orderTime);
			registrationForm.setCheck(true);
			registrationForm.getUserAccount().setUsername(username);
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");

			final BindingResult binding = null;

			restaurant = this.restaurantService.reconstruct(registrationForm, binding);

			this.restaurantService.save(restaurant);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated
	 * must be able to edit his or her personal data.
	 * 
	 * b) Broken bussines rule:
	 * Un restaurant intenta editar la informacion de otro.
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor , el save y el findOne del RestaurantService ,
	 * el total de lineas sumando estos metodos es de 111, de las cuales este test recorrer 69 , es decir un 62'18%.
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void EditRestaurantService() {
		final Object testingData[][] = {
			{
				//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", super.getEntityId("restaurant1"), null
			}, {
				//Negative test: UN restaurant intenta modificar los datos de otro
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", super.getEntityId("restaurant2"), IllegalArgumentException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditAdministradorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(int) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void EditAdministradorTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final int administratoriId,
		final Class<?> expected) {
		Class<?> caught;
		Restaurant restaurant = null;

		caught = null;
		try {
			super.authenticate("restaurant");
			RegistrationFormRestaurant registrationForm = new RegistrationFormRestaurant();

			registrationForm = registrationForm.createToRestaurant();

			restaurant = this.restaurantService.findOne(administratoriId);
			registrationForm.setId(restaurant.getId());
			registrationForm.setVersion(restaurant.getVersion());

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(restaurant.getUserAccount().getUsername());
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");
			registrationForm.setComercialName(restaurant.getComercialName());
			registrationForm.setSpeciality(restaurant.getSpeciality());
			registrationForm.setMediumScore(restaurant.getMediumScore());
			registrationForm.setOrderTime(restaurant.getOrderTime());
			final BindingResult binding = null;

			restaurant = this.restaurantService.reconstruct(registrationForm, binding);

			this.restaurantService.save(restaurant);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
