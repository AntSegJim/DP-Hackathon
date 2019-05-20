
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.DealerReposiroty;
import domain.Dealer;

@Service
@Transactional
public class DealerService {

	@Autowired
	private DealerReposiroty	dealerRepository;


	public Collection<Dealer> getActiveDealersByRestaurant(final Integer id) {
		return this.dealerRepository.getActiveDealersByRestaurant(id);
	}
}
