
package service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.ComplainRepository;
import services.CashOrderService;
import services.ComplainService;
import utilities.AbstractTest;
import domain.CashOrder;
import domain.Complain;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ComplainServiceTest extends AbstractTest {

	@Autowired
	private ComplainService		complainService;
	@Autowired
	private CashOrderService	cashOrderService;
	@Autowired
	private ComplainRepository	complainRepository;


	/*
	 * a) Requeriment: Manejar sus quejas que incluye listarlas, crearlas,
	 * editarlas y eliminarlas. Para que un cliente pueda crear una queja
	 * sobre un pedido, este debe estar en estado ACEPTADO.
	 * 
	 * 
	 * b) Broken bussines rule: Un customer crea una queja con el atributo "description" vacio.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo create complain-> 6
	 * Sentencias metodo save complain-> 5
	 * Sentencias metodo findOne cashOrder->1
	 * Sentencias totales-> 12
	 * Sentence covegare positive test -> 12 (100%)
	 * Sentence covegare negative test -> 11 (91,66%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void CreateComplainService() {
		final Object testingData[][] = {
			{//Positive test
				"description1", super.getEntityId("cashOrder1"), null
			}, {//Negative test
				"", super.getEntityId("cashOrder1"), ConstraintViolationException.class
			}, {//Negative test
				"<script>alert('hola');</script>", super.getEntityId("cashOrder1"), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateComplainTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void CreateComplainTemplate(final String description, final int cashOrderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("customer1");

			final Complain c = this.complainService.create();
			final CashOrder cashOrder = this.cashOrderService.findOne(cashOrderId);

			c.setDescription(description);
			c.setCashOrder(cashOrder);

			this.complainService.save(c);
			this.complainRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manejar sus quejas que incluye listarlas, crearlas,
	 * editarlas y eliminarlas. Para que un cliente pueda crear una queja
	 * sobre un pedido, este debe estar en estado ACEPTADO.
	 * 
	 * 
	 * b) Broken bussines rule: Un customer intenta mostrar una queja que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne complain-> 6
	 * Sentencias totales-> 6
	 * Sentence covegare positive test -> 6 (100%)
	 * Sentence covegare negative test -> 4 (66,66%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void ShowComplainService() {
		final Object testingData[][] = {
			{//Positive test
				"customer1", super.getEntityId("complain1"), null
			}, {//Negative test
				"customer1", super.getEntityId("complain2"), IllegalArgumentException.class
			}, {//Negative test
				"customer2", super.getEntityId("complain1"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.ShowComplainTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void ShowComplainTemplate(final String authority, final int complainId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);
			final Complain c = this.complainService.findOne(complainId);
			Assert.notNull(c);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manejar sus quejas que incluye listarlas, crearlas,
	 * editarlas y eliminarlas. Para que un cliente pueda crear una queja
	 * sobre un pedido, este debe estar en estado ACEPTADO.
	 * 
	 * 
	 * b) Broken bussines rule: Un customer intenta editar una queja que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne complain-> 6
	 * Sentencias metodo save complain-> 5
	 * Sentencias metodo findOne cashOrder->1
	 * Sentencias totales-> 12
	 * Sentence covegare positive test -> 12 (100%)
	 * Sentence covegare negative test -> 4 (33,33%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 4 atributos-> 25%
	 */
	@Test
	public void EditComplainService() {
		final Object testingData[][] = {
			{//Positive test
				"customer1", super.getEntityId("complain1"), "newDescription", super.getEntityId("cashOrder1"), null
			}, {//Negative test
				"customer2", super.getEntityId("complain1"), "newDescription", super.getEntityId("cashOrder1"), IllegalArgumentException.class
			}, {//Negative test
				"customer2", super.getEntityId("complain1"), "<script>alert('hola');</script>", super.getEntityId("cashOrder1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditComplainTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void EditComplainTemplate(final String authority, final int complainId, final String description, final int cashOrderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Complain c = this.complainService.findOne(complainId);
			final CashOrder cashOrder = this.cashOrderService.findOne(cashOrderId);
			c.setDescription(description);
			c.setCashOrder(cashOrder);

			this.complainService.save(c);
			this.complainRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manejar sus quejas que incluye listarlas, crearlas,
	 * editarlas y eliminarlas. Para que un cliente pueda crear una queja
	 * sobre un pedido, este debe estar en estado ACEPTADO.
	 * 
	 * 
	 * b) Broken bussines rule: Un customer intenta eliminar una queja que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne complain-> 6
	 * Sentencias metodo delete complain-> 4
	 * Sentencias totales-> 10
	 * Sentence covegare positive test -> 10 (100%)
	 * Sentence covegare negative test -> 4 (40%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void DeleteComplainService() {
		final Object testingData[][] = {
			{//Positive test
				"customer1", super.getEntityId("complain1"), null
			}, {//Negative test
				"customer1", super.getEntityId("complain2"), IllegalArgumentException.class
			}, {//Negative test
				"customer2", super.getEntityId("complain1"), NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteComplainTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void DeleteComplainTemplate(final String authority, final int complainId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);
			final Complain c = this.complainService.findOne(complainId);
			this.complainService.delete(c);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
