
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.FoodDishes;

public interface FoodDishesRepository extends JpaRepository<FoodDishes, Integer> {

	@Query("select f from FoodDishes f where f.restaurant.id = ?1")
	public Collection<FoodDishes> getFoodDishesByRestaurant(Integer id);

}
