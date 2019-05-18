
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FoodDishesRepository;
import security.LoginService;
import security.UserAccount;
import domain.FoodDishes;
import domain.Restaurant;

@Service
@Transactional
public class FoodDishesService {

	@Autowired
	private FoodDishesRepository	foodDishesRepository;
	@Autowired
	private ActorService			actorService;


	public FoodDishes create() {

		final FoodDishes foodDishes = new FoodDishes();
		foodDishes.setName("");
		foodDishes.setDescription("");
		foodDishes.setPictures("");
		foodDishes.setPrice(0.);
		foodDishes.setType(0);
		foodDishes.setIngredients(new ArrayList<String>());
		foodDishes.setRestaurant(new Restaurant());
		return foodDishes;

	}

	public FoodDishes findOne(final Integer id) {
		return this.foodDishesRepository.findOne(id);
	}

	public Collection<FoodDishes> findAll() {
		return this.foodDishesRepository.findAll();
	}

	public Collection<FoodDishes> findFoodDishesByRestaurant(final Integer id) {
		return this.foodDishesRepository.getFoodDishesByRestaurant(id);
	}

	public FoodDishes save(final FoodDishes f) {
		FoodDishes res;

		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = (Restaurant) this.actorService.getActorByUserAccount(user.getId());
		Assert.notNull(r);
		f.setRestaurant(r);

		res = this.foodDishesRepository.save(f);

		return res;
	}

	public void delete(final FoodDishes fd) {
		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = (Restaurant) this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(fd.getRestaurant().equals(r));
		this.foodDishesRepository.delete(fd);
	}

}
