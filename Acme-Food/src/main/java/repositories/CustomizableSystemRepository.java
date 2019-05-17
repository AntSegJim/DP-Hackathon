
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CustomizableSystem;

@Repository
public interface CustomizableSystemRepository extends JpaRepository<CustomizableSystem, Integer> {

	@Query("select c.messageWelcomePage from CustomizableSystem c")
	public String getWelcomeMessage();

	@Query("select c.spanishMessageWelcomePage from CustomizableSystem c")
	public String getSpanishWelcomeMessage();

	@Query("select c.telephoneCode from CustomizableSystem c")
	public String getTelephoneCode();

	@Query("select c.banner from CustomizableSystem c")
	public String getUrlBanner();

	@Query("select c.nameSystem from CustomizableSystem c")
	public String getNameApp();

	@Query("select c.timeCache from CustomizableSystem c")
	public int getTimeCache();

	@Query("select c.maxResults from CustomizableSystem c")
	public int getMaxResults();

}
