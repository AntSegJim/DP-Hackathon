
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CashOrder;

@Repository
public interface CashOrderRepository extends JpaRepository<CashOrder, Integer> {

	@Query("select co from CashOrder co where co.customer.id = ?1")
	public Collection<CashOrder> getCashOrderByCustomer(Integer id);

	@Query("select co from CashOrder co where co.restaurant.id = ?1 and co.status != 1")
	public Collection<CashOrder> getCashOrderByRestaurant(Integer id);

	@Query(value = "select date_add(NOW(), INTERVAL 30 MINUTE)", nativeQuery = true)
	public Date getMoreHour();

}
