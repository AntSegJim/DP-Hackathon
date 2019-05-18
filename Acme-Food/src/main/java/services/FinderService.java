
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import repositories.FinderRepository;
import repositories.RestaurantRepository;
import security.LoginService;
import domain.Finder;
import domain.Restaurant;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository			finderRepository;
	@Autowired
	private RestaurantRepository		restaurantRepository;
	@Autowired
	private CustomerRepository			customerRepository;		// Cambiar a Service cuando esté
	@Autowired
	private Validator					validator;
	@Autowired
	private CustomizableSystemService	customizableSystemService;


	public Finder create() {
		final Finder finder = new Finder();
		finder.setKeyWord("");
		finder.setMinScore(0);
		finder.setRestaurants(new HashSet<Restaurant>());
		finder.setMaxScore(10);
		finder.setMoment(new Date());
		return finder;
	}

	public Finder findOne() {
		return this.customerRepository.getCustomerByUserAccountId(LoginService.getPrincipal().getId()).getFinder();
	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder save(final Finder f) {
		Finder saved;
		if (f.getId() == 0)
			saved = this.finderRepository.save(f);
		else {
			final Finder savedFinder = this.findOne();
			savedFinder.setKeyWord(f.getKeyWord());
			savedFinder.setRestaurants(f.getRestaurants());
			savedFinder.setMinScore(f.getMinScore());
			savedFinder.setMaxScore(f.getMaxScore());
			savedFinder.setMoment(f.getMoment());
			Assert.isTrue(f.getMoment() != null);
			saved = this.finderRepository.save(savedFinder);
		}
		return saved;
	}

	//RECONSTRUCT
	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		final Finder res = this.findOne();

		final Finder copy = new Finder();
		copy.setId(res.getId());
		copy.setVersion(res.getVersion());
		copy.setMoment(res.getMoment()); // OJO CAMBIAR MAS ABAJO Y QUITARLO DE AQUI
		copy.setKeyWord(finder.getKeyWord());
		copy.setMinScore(finder.getMinScore());
		copy.setMaxScore(finder.getMaxScore());

		if (finder.getKeyWord() == null)
			copy.setKeyWord("");
		if (res.getKeyWord() == null)
			res.setKeyWord("");
		if (finder.getMinScore() == null)
			copy.setMinScore(0);
		if (res.getMinScore() == null)
			res.setMinScore(0);
		if (finder.getMaxScore() == null)
			copy.setMaxScore(10);
		if (res.getMaxScore() == null)
			res.setMaxScore(10);

		final Boolean noHaCambiadoFinder = res.getMinScore().equals(copy.getMinScore()) && res.getMaxScore().equals(copy.getMaxScore()) && res.getKeyWord().equals(copy.getKeyWord());

		final long a = (new Date().getTime() - res.getMoment().getTime()) / 3600000;
		final long b = this.customizableSystemService.getTimeCache();

		if (a > b || !noHaCambiadoFinder) {
			final Collection<Restaurant> restaurants = this.restaurantRepository.filterRestaurants(copy.getKeyWord(), copy.getMinScore(), copy.getMaxScore());
			copy.setRestaurants(restaurants);
			copy.setMoment(new Date());
		} else
			copy.setRestaurants(res.getRestaurants());

		this.validator.validate(copy, binding);
		return copy;

	}

}
