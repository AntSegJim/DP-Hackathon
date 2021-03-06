
package service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.FinderRepository;
import services.FinderService;
import utilities.AbstractTest;
import domain.Finder;
import domain.Restaurant;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService		finderService;
	@Autowired
	private FinderRepository	finderRepository;


	/*
	 * a) Requeriment: Create a finder.
	 * 
	 * b) Broken bussines rule:
	 * create a finder with bad score
	 * 
	 * c) Sentence coverage: 13/23=56.52%
	 * 
	 * d) Data coverage: 20% (1 atributo incorrecto/5 atributos)
	 */

	@Test
	public void CreateFinderService() {
		final Object testingData[][] = {
			{//Positive test
				"keyword", 1, 6, new HashSet<Restaurant>(), new Date(), null
			}, {//Negative test: maxScore bigger than 10
				"i7", 1, 11, new HashSet<Restaurant>(), new Date(), ConstraintViolationException.class
			}, {//Negative test: minScore is negative
				"i7", -1, 10, new HashSet<Restaurant>(), new Date(), ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateFinderTemplate((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Collection<Restaurant>) testingData[i][3], (Date) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void CreateFinderTemplate(final String keyword, final Integer minScore, final Integer maxScore, final Collection<Restaurant> c, final Date moment, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("customer1");

			final Finder f = this.finderService.create();
			f.setKeyWord(keyword);
			f.setMinScore(minScore);
			f.setMaxScore(maxScore);
			f.setMoment(moment);
			f.setRestaurants(c);
			this.finderService.save(f);
			this.finderRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Edit a finder.
	 * 
	 * b) Broken bussines rule:
	 * edit a finder with date null
	 * 
	 * c) Sentence coverage: 17/23=73.91%
	 * 
	 * d) Data coverage: 20% (1 atributo incorrecto/5 atributos)
	 */

	@Test
	public void EditFinderService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("finder1"), "i7", 0, 1, new HashSet<Restaurant>(), new Date(), null
			}, {//Negative test: Moment null
				super.getEntityId("finder2"), "i7", 0, 1, new HashSet<Restaurant>(), null, IllegalArgumentException.class
			}, {//Negative test: Script in keyword
				super.getEntityId("finder2"), "<script>alert('hola');</script>", 0, -1, new HashSet<Restaurant>(), null, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditFinderTemplate((int) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Collection<Restaurant>) testingData[i][4], (Date) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void EditFinderTemplate(final int finderId, final String keyword, final Integer minScore, final Integer maxScore, final Collection<Restaurant> c, final Date moment, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("customer1");

			final Finder f = this.finderService.findOne();
			f.setKeyWord(keyword);
			f.setMinScore(minScore);
			f.setMaxScore(maxScore);
			f.setMoment(moment);
			f.setRestaurants(c);
			this.finderService.save(f);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
