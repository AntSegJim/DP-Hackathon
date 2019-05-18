
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

	@Query("select m from Restaurant m where m.userAccount.id = ?1")
	public Restaurant getRestaurantByUserAccount(int userAccountId);

}
