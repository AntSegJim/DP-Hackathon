
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ComplainService;
import utilities.AbstractTest;
import domain.Complain;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ComplainServiceTest extends AbstractTest {

	@Autowired
	private ComplainService	complainService;


	@Test
	public void ShowComplainService() {
		final Object testingData[][] = {
			{//Positive test
				"customer1", super.getEntityId("complain1"), null
			}, {//Positive test
				"customer1", super.getEntityId("complain2"), IllegalArgumentException.class
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

	@Test
	public void DeleteComplainService() {
		final Object testingData[][] = {
			{//Positive test
				"customer1", super.getEntityId("complain1"), null
			}, {//Positive test
				"customer1", super.getEntityId("complain2"), IllegalArgumentException.class
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
