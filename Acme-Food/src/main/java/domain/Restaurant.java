
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Restaurant extends Actor {

	private Collection<Rating>	ratings;


	@OneToMany
	@Valid
	public Collection<Rating> getRatings() {
		return this.ratings;
	}

	public void setRatings(final Collection<Rating> ratings) {
		this.ratings = ratings;
	}

}
