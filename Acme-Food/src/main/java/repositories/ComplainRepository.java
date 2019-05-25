
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

}
