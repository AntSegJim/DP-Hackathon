
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

	@Query("select r from Restaurant r where (select count(f) from FoodDishes f where f.restaurant.id=r.id) > 0 and r.isBanned=0")
	public Collection<Restaurant> getRestaurantWithFood();

	@Query("select m from Restaurant m where m.userAccount.id = ?1")
	public Restaurant getRestaurantByUserAccount(int userAccountId);

	@Query("select r from Restaurant r where (locate(?1, r.comercialName) != 0  or locate(?1, r.speciality) != 0 ) and r.mediumScore between ?2 and ?3")
	public Collection<Restaurant> filterRestaurants(String keyWord, Integer minScore, Integer maxScore);

	@Query("select r from Restaurant r where r.isBanned=0")
	public Collection<Restaurant> getActivesRestaurants();

	@Query("select count(d) from Dealer d where d.restaurant.id=?1 and d.isDealing=0")
	public Integer getFreeDealerByRestaurant(Integer id);
}
