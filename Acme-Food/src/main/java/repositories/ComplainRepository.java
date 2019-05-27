
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complain;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Integer> {

	@Query("select c from Complain c where c.customer.id=?1")
	public Collection<Complain> getComplainByCustomer(Integer customerId);

	//DashBoard
	@Query("select count(r)*1.0/(select count(x) from Restaurant x) from Restaurant r where (select count(c) from Complain c where c.cashOrder.restaurant.id =r.id) > 0")
	public Double ratioOfRestaurantsWithComplain();

}
