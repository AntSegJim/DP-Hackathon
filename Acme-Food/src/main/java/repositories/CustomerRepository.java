
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Finder;

@Repository
public interface CustomerRepository extends JpaRepository<Finder, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	public Customer getCustomerByUserAccountId(int userAccountId);
}
