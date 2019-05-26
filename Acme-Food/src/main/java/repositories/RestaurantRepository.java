
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

	@Query("select count(d) from Dealer d where d.restaurant.id=?1 and (select count(c) from CashOrder c where c.dealer.id=d.id and c.status=3 and c.choice=1 and c.draftMode=0) < 3")
	public Integer getFreeDealerByRestaurant(Integer id);

	@Query("select distinct c.restaurant from CashOrder c where c.customer.id = ?1 and c.draftMode = 0 and c.status = 2")
	public Collection<Restaurant> getAllRestaurantWhereIHaveDoneAOrder(int customerId);

	@Query("select r.restaurant from Rating r where r.customer.id = ?1")
	public Collection<Restaurant> getAllMyRatings(int customerId);

	//DASHBOARD
	@Query("select r.comercialName from Restaurant r where (select max(res.mediumScore) from Restaurant res) = r.mediumScore")
	public Collection<String> getRestaurantWithMoreScore();

	@Query("select r.comercialName from Restaurant r where (select min(res.mediumScore) from Restaurant res) = r.mediumScore")
	public Collection<String> getRestaurantWithLessScore();

	@Query(value = "select restaurant from `acme-food`.cash_order i group by i.restaurant order by count(i.restaurant) desc LIMIT 5", nativeQuery = true)
	public Collection<Integer> getTop5RestaurantsWithMoreOrders();

}
