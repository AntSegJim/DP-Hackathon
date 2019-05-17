
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	private Collection<Rating>	ratings;
	private Finder				finder;


	@OneToOne
	@Valid
	@NotNull
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@OneToMany
	@Valid
	public Collection<Rating> getRatings() {
		return this.ratings;
	}

	public void setRatings(final Collection<Rating> ratings) {
		this.ratings = ratings;
	}

}
