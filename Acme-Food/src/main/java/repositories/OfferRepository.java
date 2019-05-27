
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query("select f from Offer f where f.restaurant.id=?1")
	public Collection<Offer> getOffersByRestaurant(Integer restaurantId);

	@Query("select sum(d.price) from Offer f join f.foodDisheses d where f.id=?1")
	public Double getPrecioPlatosOferta(Integer offerId);

	//DashBoard
	@Query("select x.comercialName from Restaurant x where (select sum(u.totalPrice) from Offer u where u.restaurant.id=x.id) < ( select avg(1.0*(select sum(r.totalPrice) from Offer r where r.restaurant.id=a.id)) from Restaurant a)")
	public Collection<String> getRestaurantWithOffersLessThanAvg();
}
