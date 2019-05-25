
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	@Query("select r from Rating r where r.customer.id = ?1")
	public Collection<Rating> getAllMyRatings(int customerId);

	@Query("select avg(r.valoration) from Rating r where r.restaurant.id = ?1")
	public Integer averageScore(int restaurantId);

}
