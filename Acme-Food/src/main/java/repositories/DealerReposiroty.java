
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Dealer;

@Repository
public interface DealerReposiroty extends JpaRepository<Dealer, Integer> {

	@Query("select d from Dealer d where d.restaurant.id=?1 and d.isDealing=0")
	public Collection<Dealer> getActiveDealersByRestaurant(Integer id);

}
