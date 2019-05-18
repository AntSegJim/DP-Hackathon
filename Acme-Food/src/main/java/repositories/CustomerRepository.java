
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Finder;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	public Customer getCustomerByUserAccountId(int userAccountId);

>>>>>>> f4316957c1b7502c6cc7574a0b959ccb57d31838
}
