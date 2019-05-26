
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select avg(f.restaurants.size),min(f.restaurants.size),max(f.restaurants.size), sqrt(1.0*sum(f.restaurants.size * f.restaurants.size) / count(f) - avg(f.restaurants.size) * avg(f.restaurants.size)) from Finder f")
	public List<Object[]> getAvgMinMaxDesvResultsByFinder();

}
