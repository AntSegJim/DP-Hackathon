
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Complain;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Integer> {

}
