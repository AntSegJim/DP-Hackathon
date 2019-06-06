
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

	private Collection<Rating>		ratings;
	private Finder					finder;
	private CreditCard				creditCard;
	private Collection<Complain>	complains;


	@OneToMany
	@Valid
	public Collection<Complain> getComplains() {
		return this.complains;
	}

	public void setComplains(final Collection<Complain> complains) {
		this.complains = complains;
	}

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

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
