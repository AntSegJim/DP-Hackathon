
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Dealer;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Integer> {

	@Query("select d from Dealer d where d.restaurant.id=?1")
	public Collection<Dealer> getActiveDealersByRestaurant(Integer id);

	@Query("select count(c) from CashOrder c where c.dealer.id=?1 and c.status=3 and c.choice=1 and c.draftMode=0")
	public Integer getNumberCashOrderByDealer(Integer id);

	@Query("select d from Dealer d where d.userAccount.id=?1")
	public Dealer getDealerByUserAccount(Integer id);

	@Query("select d from Dealer d where d.restaurant.userAccount.id = ?1")
	public Collection<Dealer> getAllDealerByRestaurantUserAccount(int restaurantUserAccountId);

}
