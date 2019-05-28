
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.CreditCardService;
import services.CustomerService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Customer;
import forms.RegistrationFormCustomerAndCreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CreditCardService	creditCardService;


	/*
	 * a) Requeriment: An actor who is not authenticated must be able to
	 * register to the system as customer.
	 * 
	 * b) Broken bussines rule:
	 * Se intenta registratar un usuario como customer con un patrón de VatNumber incorrecto
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tanto del CustomerService como de CreditCardService,
	 * el total de lineas sumando estos metodos es de 147, de las cuales este test recorrer 78 , es decir un 53'06%.
	 * 
	 * d) Data coverage: 7.69% (1 atributo incorrecto/13atributos)
	 */

	@Test
	public void CreateCustomerService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, null
			}, {//Negative test: VatNumber con patrón incorrecto
				"Nuevo Nombre", "Apellido", "ES123456789X", "prueba2@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, NullPointerException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateCustomerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (Class<?>) testingData[i][13]);
	}
	protected void CreateCustomerTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final String brandName, final String holderName,
		final String number, final int expirationMonth, final int expirationYear, final int cW, final Class<?> expected) {
		Class<?> caught;
		Customer customer = null;
		CreditCard creditcard = null;
		caught = null;
		try {

			RegistrationFormCustomerAndCreditCard registrationForm = new RegistrationFormCustomerAndCreditCard();

			registrationForm = registrationForm.createToCustomerAndCreditCard();

			registrationForm.setBrandName(brandName);
			registrationForm.setHolderName(holderName);
			registrationForm.setNumber(number);
			registrationForm.setExpirationMonth(expirationMonth);
			registrationForm.setExpirationYear(expirationYear);
			registrationForm.setCW(cW);

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

			registrationForm.setCheck(true);

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			customer = this.customerService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			customer.setCreditCard(creditCardSave);
			this.customerService.save(customer);

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
	 * Un customer intenta editar la informacion de otro.
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tando del CustomerService como del CreditCardService, y al findOne de CustomerService,
	 * el total de lineas sumando estos metodos es de 153, de las cuales este test recorrer 99 , es decir un 64'71%.
	 * 
	 * d) Data coverage:
	 */

	@Test
	public void EditCustomerService() {
		final Object testingData[][] = {
			{
				//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("customer1"), null
			}, {
				//Negative test: Un hacker intenta modificar los datos de otra
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("customer2"), IllegalArgumentException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditHackerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (Class<?>) testingData[i][13]);
	}
	protected void EditHackerTemplate(final String name, final String surnames, final String vatNumber, final String email, final String password, final String confirmPassword, final String brandName, final String holderName, final String number,
		final int expirationMonth, final int expirationYear, final int cW, final int hackerId, final Class<?> expected) {
		Class<?> caught;
		Customer customer = null;
		CreditCard creditcard = null;
		caught = null;
		try {
			super.authenticate("customer1");
			RegistrationFormCustomerAndCreditCard registrationForm = new RegistrationFormCustomerAndCreditCard();

			registrationForm = registrationForm.createToCustomerAndCreditCard();

			customer = this.customerService.findOne(hackerId);
			registrationForm.setId(customer.getId());
			registrationForm.setVersion(customer.getVersion());
			registrationForm.setBrandName(brandName);
			registrationForm.setHolderName(holderName);
			registrationForm.setNumber(number);
			registrationForm.setExpirationMonth(expirationMonth);
			registrationForm.setExpirationYear(expirationYear);
			registrationForm.setCW(cW);

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(customer.getUserAccount().getUsername());
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");
			registrationForm.setRatings(customer.getRatings());
			registrationForm.setFinder(customer.getFinder());
			registrationForm.setComplains(customer.getComplains());

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			customer = this.customerService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			customer.setCreditCard(creditCardSave);
			this.customerService.save(customer);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
