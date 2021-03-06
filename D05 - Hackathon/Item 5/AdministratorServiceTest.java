
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.AdministratorService;
import utilities.AbstractTest;
import domain.Administrator;
import forms.RegistrationForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	/*
	 * a) Requeriment: An actor who is authenticated as an administrator
	 * must be able to create user acconunts for new administrators.
	 * 
	 * b) Broken bussines rule:
	 * Se intenta crear un nuevo administrador sin email
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save del AdministratorService
	 * el total de lineas sumando estos metodos es de 98, de las cuales este test recorrer 59 , es decir un 60'20%.
	 * 
	 * d) Data coverage: 14.28% (1 atributo incorrecto/7 atributos)
	 */

	@Test
	public void CreateAdmnistratorService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", null
			}, {//Negative test: email vacio
				"Nuevo Nombre", "Apellido", "ES12345678X", "", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", NullPointerException.class

			}, {//Negative test: Se intenta registrar un admin pero se equivoca poniendo la contraseņa y el confirmar contraseņa mal
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWor", IllegalArgumentException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateAdministradorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	protected void CreateAdministradorTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final Class<?> expected) {
		Class<?> caught;
		Administrator admin = null;

		caught = null;
		try {
			super.authenticate("admin");
			RegistrationForm registrationForm = new RegistrationForm();

			registrationForm = registrationForm.createToAdmin();

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(username);
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");

			final BindingResult binding = null;

			admin = this.administratorService.reconstruct(registrationForm, binding);

			this.administratorService.save(admin);

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
	 * Un administrador intenta editar la informacion de otro.
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor , el save y el findOne del AdministratorService
	 * el total de lineas sumando estos metodos es de 104, de las cuales este test recorrer 70 , es decir un 67'31%.
	 * 
	 * d) Data coverage:-
	 */

	@Test
	public void EditAdmnistratorService() {
		final Object testingData[][] = {
			{
				//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", super.getEntityId("administrator2"), null
			}, {
				//Negative test: UN administrador intenta modificar los datos de otro
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", super.getEntityId("administrator1"), IllegalArgumentException.class

			}, {
				//Negative test: Un final administrador modifica su final nombre dejandolo en blanco
				"", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", super.getEntityId("administrator2"), NullPointerException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditAdministradorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(int) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void EditAdministradorTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final int administratoriId,
		final Class<?> expected) {
		Class<?> caught;
		Administrator admin = null;
		;
		caught = null;
		try {
			super.authenticate("admin1");
			RegistrationForm registrationForm = new RegistrationForm();

			registrationForm = registrationForm.createToAdmin();

			admin = this.administratorService.findOne(administratoriId);
			registrationForm.setId(admin.getId());
			registrationForm.setVersion(admin.getVersion());

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(admin.getUserAccount().getUsername());
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");

			final BindingResult binding = null;

			admin = this.administratorService.reconstruct(registrationForm, binding);

			this.administratorService.save(admin);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
