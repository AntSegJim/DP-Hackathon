
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	public Customer getCustomerByUserAccountId(int userAccountId);

	//Dashboard
	@Query("select x.name from Customer x where (select count(v) from CashOrder v where v.customer.id=x.id) >= (select avg(1.0*(select count(c) from CashOrder c where c.customer.id=i.id)) from Customer i)")
	public Collection<String> getCustomerWithMoreCashThanAvg();

	@Query("select avg(1.0*(select count(c.customer) from CashOrder c where c.status != 1 and c.customer.id = r.id)), min(1.0*(select count(c.customer) from CashOrder c where c.status != 1 and c.customer.id = r.id)), max(1.0*(select count(c.customer) from CashOrder c where c.status != 1 and c.customer.id = r.id)), sqrt(1.0*sum(1.0*(select count(c.customer) from CashOrder c where c.status != 1 and c.customer.id = r.id) * (select count(c.customer) from CashOrder c where c.status != 1 and c.customer.id = r.id)) / count(r) - avg(1.0*(select count(c.customer) from CashOrder c where c.status != 1 and c.customer.id = r.id)) * avg(1.0*(select count(c.customer) from CashOrder c where c.status != 1 and c.customer.id = r.id))) from Customer r")
	public List<Object[]> getAvgMinMaxDesvNumbersOfOrdersByCustomer();

}
