
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

	@Query("select m from Restaurant m where m.userAccount.id = ?1")
	public Restaurant getRestaurantByUserAccount(int userAccountId);

	@Query("select r from Restaurant r where (locate(?1, r.comercialName) != 0  or locate(?1, r.speciality) != 0 ) and locate(?2,r.comercialName) != 0  and locate(?3,r.speciality) != 0 ")
	public Collection<Restaurant> filterRestaurants(String keyWord, String restaurantName, String speciality);

}
