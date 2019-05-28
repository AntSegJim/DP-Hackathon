
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.DealerService;
import utilities.AbstractTest;
import domain.Dealer;
import forms.RegistrationFormDealer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DealerServiceTest extends AbstractTest {

	@Autowired
	private DealerService	dealerService;


	/*
	 * a) Requeriment: An actor who is authenticated as an restaurant
	 * must be able to create user acconunts for new dealers.
	 * 
	 * b) Broken bussines rule:
	 * Se intenta crear un nuevo repartidor sin email
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save de DealerService,
	 * el total de lineas sumando estos metodos es de 106, de las cuales este test recorre 62 , es decir un 58'49%.
	 * 
	 * d) Data coverage: 14.29% (1 atributo incorrecto/7 atributos)
	 */

	@Test
	public void CreateDealerService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", null
			}, {//Negative test: email vacio
				"Nuevo Nombre", "Apellido", "ES12345678X", "", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", NullPointerException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateDealerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	protected void CreateDealerTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final Class<?> expected) {
		Class<?> caught;
		Dealer d = null;

		caught = null;
		try {
			super.authenticate("restaurant1");
			RegistrationFormDealer registrationForm = new RegistrationFormDealer();

			registrationForm = registrationForm.createToDealer();

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

			d = this.dealerService.reconstruct(registrationForm, binding);

			this.dealerService.save(d);

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
	 * Un restaurant inetenta editar la informacion de uno de sus repartidores.
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save de DealerService,
	 * el total de lineas sumando estos metodos es de 106, de las cuales este test recorre 31 , es decir un 29'24%.
	 * 
	 * d) Data coverage: 14.29% (1 atributo incorrecto/7 atributos)
	 */

	@Test
	public void EditDealerService() {
		final Object testingData[][] = {
			{
				//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername2", "NuevaPassWord", "NuevaPassWord", super.getEntityId("dealer1"), null
			}, {
				//Negative test: Nombre blanco
				"Antonio", "Apellido", "ES12345678X", "prueba2@email.com", "NuevoUsername13", "NuevaPassWord", "NuevaPassWord22", super.getEntityId("dealer2"), IllegalArgumentException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditDealerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(int) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void EditDealerTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final int administratoriId, final Class<?> expected) {
		Class<?> caught;
		Dealer dealer;
		caught = null;
		try {
			super.authenticate("restaurant1");
			RegistrationFormDealer registrationForm = new RegistrationFormDealer();

			registrationForm = registrationForm.createToDealer();

			dealer = this.dealerService.findOne(administratoriId);
			registrationForm.setId(dealer.getId());
			registrationForm.setVersion(dealer.getVersion());

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(dealer.getUserAccount().getUsername());
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");
			final BindingResult binding = null;

			dealer = this.dealerService.reconstruct(registrationForm, binding);

			this.dealerService.save(dealer);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
