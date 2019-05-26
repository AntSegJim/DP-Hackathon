
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	public Customer getCustomerByUserAccountId(int userAccountId);

	@Query("select x.name from Customer x where (select count(v) from CashOrder v where v.customer.id=x.id) >= (select avg(1.0*(select count(c) from CashOrder c where c.customer.id=i.id)) from Customer i)")
	public Collection<String> getCustomerWithMoreCashThanAvg();

}
