
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FoodDishesRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.FoodDishes;
import domain.Restaurant;

@Service
@Transactional
public class FoodDishesService {

	@Autowired
	private FoodDishesRepository	foodDishesRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private Validator				validator;


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
		Assert.isTrue(f.getRestaurant().equals(r));

		res = this.foodDishesRepository.save(f);

		return res;
	}

	public void delete(final FoodDishes fd) {
		final UserAccount user = LoginService.getPrincipal();
		final Restaurant r = (Restaurant) this.actorService.getActorByUserAccount(user.getId());
		Assert.isTrue(fd.getRestaurant().equals(r));
		this.foodDishesRepository.delete(fd);
	}

	//RECONSTRUCT
	public FoodDishes reconstruct(final FoodDishes food, final BindingResult binding) {
		final FoodDishes res;

		if (food.getId() == 0) {
			res = food;

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			food.setRestaurant((Restaurant) a);

			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.foodDishesRepository.findOne(food.getId());
			final FoodDishes copy = new FoodDishes();
			copy.setRestaurant(res.getRestaurant());
			copy.setId(res.getId());
			copy.setVersion(res.getVersion());

			copy.setName(food.getName());
			copy.setDescription(food.getDescription());
			copy.setPictures(food.getPictures());
			copy.setPrice(food.getPrice());
			copy.setType(food.getType());
			copy.setIngredients(food.getIngredients());

			this.validator.validate(copy, binding);
			if (binding.hasErrors())
				throw new ValidationException();
			return copy;
		}
	}
}
